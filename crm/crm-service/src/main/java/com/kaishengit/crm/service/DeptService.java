package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findAllDept();

    List<Dept> findByAccountId(int accountId);

    Integer saveNewDept(String deptName,Integer pid);
}
