package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.example.CustomerExample;

import com.kaishengit.crm.example.SaleChanceExample;
import com.kaishengit.crm.example.SaleChanceRecordExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.SaleChanceMapper;
import com.kaishengit.crm.mapper.SaleChanceRecordMapper;
import com.kaishengit.crm.service.CustomerService;

import com.kaishengit.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerImpl implements CustomerService {
    //SpringEL
    @Value("#{'${customer.trade}'.split(',')}")
    private List<String> customerTrade;
    @Value("#{'${customer.source}'.split(',')}")
    private List<String> customerSource;

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    SaleChanceMapper saleChanceMapper;
    @Autowired
    SaleChanceRecordMapper saleChanceRecordMapper;

    public List<Customer> findByPhone(String customerPhone){
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andCustomPhoneEqualTo(customerPhone);
        List<Customer> customers = customerMapper.selectByExample(customerExample);
        return customers;
    }

    /**
     * 根据登录对象的ID查找他所对应所有的客户
     * @param accountId
     * @return
     */
    @Override
    public List<Customer> findAllCustomerByAccountId(int accountId) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(accountId);
        List<Customer> customers = customerMapper.selectByExample(customerExample);
        return customers;
    }

    /**
     * 根据客户Id查找该对象
     * @param customerId
     * @return
     */
    @Override
    public Customer findByCustomerId(int customerId) {
        Customer customer = customerMapper.selectByPrimaryKey(customerId);
        return customer;
    }

    /**
     * 查找所有的客户来源
     * @return
     */
    @Override
    public List<String> findAllSource() {
        return customerSource;
    }

    /**
     * 查找所有的行业
     * @return
     */
    @Override
    public List<String> findAllTrade() {
        return customerTrade;
    }

    /**
     * 新增客户
     * @param customer
     */
    @Override
    @Transactional
    public void saveNewCustomer(Customer customer) {
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customerMapper.insert(customer);
    }

    /**
     * 更新用户
     * @param customer
     */
    @Override
    @Transactional
    public void update(Customer customer){

        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIdEqualTo(customer.getId());
        List<Customer> customers = customerMapper.selectByExample(customerExample);
        Customer oldCustomer = customers.get(0);
        customer.setCreateTime(oldCustomer.getCreateTime());
        customer.setUpdateTime(new Date());
        if(customer.getAccountId()==null){
            customer.setAccountId(oldCustomer.getAccountId());
        }
        customerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 根据主键删除客户 以及 它的所有 机会 以及跟进信息
     * @param customerId
     */
    @Override
    public void deleteCustomerByCustomerId(Integer customerId) {
        //获取该客户所关联的机会
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCustIdEqualTo(customerId);
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);
        if(saleChanceList!=null&&!saleChanceList.isEmpty()){
            for(SaleChance saleChance : saleChanceList){
                //根据机会的id删除所有的跟进信息
                SaleChanceRecordExample saleChanceRecordExample = new SaleChanceRecordExample();
                saleChanceRecordExample.createCriteria().andSaleIdEqualTo(saleChance.getId());
                saleChanceRecordMapper.deleteByExample(saleChanceRecordExample);
            }
            //根据客户ID删除与之关联的机会
            SaleChanceExample saleChanceExample1 = new SaleChanceExample();
            saleChanceExample1.createCriteria().andCustIdEqualTo(customerId);
            saleChanceMapper.deleteByExample(saleChanceExample);
        }

        //根据主键删除客户
        customerMapper.deleteByPrimaryKey(customerId);
    }

    @Override
    public List<Customer> findAll() {
        return customerMapper.selectByExample(new CustomerExample());
    }

    @Override
    public List<Customer> findAllPublicCustomer() {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(0);
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        return customerList;
    }

    @Override
    public List<Map<String, Object>> findCustomerCountByTime() {
        int month = 12;
        String time = "";
        List<Map<String, Object>> list = new ArrayList();
        for(int i = 1;i<=month;i++){
            if(i<10){
                time = "2017-0"+i+"-%";
            }else{
                time = "2017-"+i+"-%";
            }
            System.out.println(time+"******************************");
            Map<String ,Object> map = customerMapper.findCustomerCountByTime(time);
            if(map!=null&&!map.isEmpty()){
                map.put("month",i+"月");
                list.add(map);
            }
        }
        return list;
    }
}
