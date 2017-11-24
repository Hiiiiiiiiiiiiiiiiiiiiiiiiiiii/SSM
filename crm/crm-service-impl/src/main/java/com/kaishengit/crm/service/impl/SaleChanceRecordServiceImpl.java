package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.example.SaleChanceRecordExample;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.SaleChanceMapper;
import com.kaishengit.crm.mapper.SaleChanceRecordMapper;
import com.kaishengit.crm.service.SaleChanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SaleChanceRecordServiceImpl implements SaleChanceRecordService{
    @Autowired
    SaleChanceRecordMapper saleChanceRecordMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    SaleChanceMapper saleChanceMapper;
    /**
     * 根据saleId查找所对应的销售机会对象
     * @param saleChanceId
     * @return
     */
    @Override
    public List<SaleChanceRecord> findBySale_id(Integer saleChanceId) {
        SaleChanceRecordExample saleChanceRecordExample = new SaleChanceRecordExample();
        saleChanceRecordExample.createCriteria().andSaleIdEqualTo(saleChanceId);
        List<SaleChanceRecord> saleChanceRecordList = saleChanceRecordMapper.selectByExample(saleChanceRecordExample);
        return saleChanceRecordList;
    }

    @Override
    public void updateProgress(Integer saleId, String progress) {
        //1.新增进度信息
        SaleChanceRecord saleChanceRecord = new SaleChanceRecord();
        saleChanceRecord.setSaleId(saleId);
        String context = "将当前进度修改为 ["+progress+"]";
        saleChanceRecord.setContent(context);
        saleChanceRecord.setCreateTime(new Date());
        saleChanceRecordMapper.insertSelective(saleChanceRecord);
        //2.更新客户的progress以及跟进时间
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(saleId);
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getAccountId());
        customer.setLastChatTime(saleChanceRecord.getCreateTime());
        customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public void saveContext(String context, Integer saleChanceId) {
        //1.新增进度信息
        SaleChanceRecord saleChanceRecord = new SaleChanceRecord();
        saleChanceRecord.setSaleId(saleChanceId);
        saleChanceRecord.setContent(context);
        saleChanceRecord.setCreateTime(new Date());
        saleChanceRecordMapper.insertSelective(saleChanceRecord);
        //2.更新客户的跟进时间
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(saleChanceId);
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getAccountId());
        customer.setLastChatTime(saleChanceRecord.getCreateTime());
        customerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 根据时间查询所有的机会进程
     * @param date
     * @return
     */
    @Override
    public List<Map<String, Object>> findAllChanceAddToList(Date date) {


        List<String> contentList = new ArrayList();
        contentList.add("将当前进度修改为 [初访]");
        contentList.add("将当前进度修改为 [意向]");
        contentList.add("将当前进度修改为 [报价]");
        contentList.add("将当前进度修改为 [成交]");
        contentList.add("将当前进度修改为 [暂时搁置]");
        List<Map<String, Object>> mapList = new ArrayList<>();
        for(String content : contentList){

            //首先判断进度是否存在表中
            SaleChanceRecordExample saleChanceRecordExample = new SaleChanceRecordExample();
            saleChanceRecordExample.createCriteria().andContentEqualTo(content);
            List<SaleChanceRecord> saleChanceRecordList = saleChanceRecordMapper.selectByExample(saleChanceRecordExample);
            if(saleChanceRecordList!=null&&!saleChanceRecordList.isEmpty()){
                Map<String ,Object> objectMap = saleChanceRecordMapper.findChanceCountByTime(date,content);
                String oldContent = (String)objectMap.get("content");
                String newContent = oldContent.substring(oldContent.indexOf('[')+1,oldContent.lastIndexOf(']'));
                objectMap.put("content",newContent);
                mapList.add(objectMap);
            }
        }
        return mapList;
    }
}
