package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.SaleChanceRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SaleChanceRecordService {
    /**
     * 根据saleId查找所对应的销售机会对象
     * @param saleChanceId
     * @return
     */
    List<SaleChanceRecord> findBySale_id(Integer saleChanceId);

    void updateProgress(Integer saleId, String progress);

    void saveContext(String context, Integer saleChanceId);


    List<Map<String,Object>> findAllChanceAddToList(Date date);
}


