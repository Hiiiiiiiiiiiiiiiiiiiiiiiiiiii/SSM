package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;

import java.util.List;

public interface SaleChanceService {
    List<SaleChance> findByAccountId(Integer accountId);

    void save(SaleChance saleChance);

    SaleChance findBySaleChanceServiceByKeyAndCustomer(Integer saleChanceId);

    List<String> findAllProgress();

    void deleteByKey(Integer saleChanceId);

    List<SaleChance> findByCustId(int customerId);
}
