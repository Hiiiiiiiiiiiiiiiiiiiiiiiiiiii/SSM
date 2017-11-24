package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.example.SaleChanceExample;
import com.kaishengit.crm.example.SaleChanceRecordExample;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.SaleChanceMapper;
import com.kaishengit.crm.mapper.SaleChanceRecordMapper;
import com.kaishengit.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class SaleChanceServiceImpl implements SaleChanceService{

    @Autowired
    SaleChanceMapper saleChanceMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    SaleChanceRecordMapper saleChanceRecordMapper;
    @Value("#{'${sales.progress}'.split(',')}")
    private List<String> progressList;
    @Override
    public List<SaleChance> findByAccountId(Integer accountId) {
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andAccountIdEqualTo(accountId);
        List<SaleChance> saleChances = saleChanceMapper.selectByExample(saleChanceExample);
        return saleChances;
    }

    /**
     * 新增机会
     * @param saleChance
     */
    @Override
    public void save(SaleChance saleChance) {
        //新增机会时，更新客户的lastTime
        Integer custId = saleChance.getCustId();
        Customer customer = customerMapper.selectByPrimaryKey(custId);
        customer.setLastChatTime(saleChance.getLastTime());
        //更新客户
        customerMapper.updateByPrimaryKey(customer);
        saleChanceMapper.insert(saleChance);
    }

    /**
     * 通过机会的ID查询机会 以及 关联的客户对象
     * @param saleChanceId
     * @return
     */
    @Override
    public SaleChance findBySaleChanceServiceByKeyAndCustomer(Integer saleChanceId) {
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(saleChanceId);
        //获取客户Id
        Integer custId = saleChance.getCustId();
        //通过客户ID查询到该客户
        Customer customer = customerMapper.selectByPrimaryKey(custId);
        saleChance.setCustomer(customer);
        return saleChance;
    }

    /**
     * 获取进度属性List
     * @return
     */
    @Override
    public List<String> findAllProgress() {
        return progressList;
    }

    /**
     * 通过机会的key删除机会
     * @param saleChanceId
     */
    @Override
    public void deleteByKey(Integer saleChanceId) {
        //获得该机会所对应的客户Id
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(saleChanceId);
        Integer custId = saleChance.getCustId();
        //根据key删除机会
        saleChanceMapper.deleteByPrimaryKey(saleChanceId);
        //获取该客户信息
        Customer customer = customerMapper.selectByPrimaryKey(custId);
        // 查询该客户下面的所有机会并按时间排序
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCustIdEqualTo(custId);
        saleChanceExample.setOrderByClause("last_time desc");
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);
        if(saleChanceList!=null&&!saleChanceList.isEmpty()){
            //获取需要的最近交互时间 并把对象的时间进行更改
            Date newLastTime = saleChanceList.get(0).getLastTime();
            customer.setLastChatTime(newLastTime);
            //更新该客户信息
            customerMapper.updateByPrimaryKey(customer);
        }else{
            customer.setLastChatTime(null);
            customerMapper.updateByPrimaryKey(customer);
        }
        //删除该机会id下的所有跟进信息
        SaleChanceRecordExample saleChanceRecordExample = new SaleChanceRecordExample();
        saleChanceRecordExample.createCriteria().andSaleIdEqualTo(saleChanceId);
        saleChanceRecordMapper.deleteByExample(saleChanceRecordExample);
    }

    /**
     * 根据客户id来获取所有的机会
     * @param customerId
     * @return
     */
    @Override
    public List<SaleChance> findByCustId(int customerId) {
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCustIdEqualTo(customerId);
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);
        return saleChanceList;
    }
}
