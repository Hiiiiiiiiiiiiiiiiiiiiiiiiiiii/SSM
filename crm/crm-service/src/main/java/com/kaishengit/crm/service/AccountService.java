package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.exception.AuthenticationException;

import java.util.List;
import java.util.Map;

public interface AccountService {
//    登录
    Account login(String mobile, String password)throws AuthenticationException;
//    查找所有的员工
    List<Account> findAllAccount();
//
    List<Account> pageForAccount(Map<String, Object> queryParam);
    /**
     * 根据部门ID获取账号的数量
     * @param deptId
     * @return
     */
    Long accountCountByDeptId(Integer deptId);

    void saveNewEmployee(String userName, String mobile, String password, Integer[] deptId);

    void deleteAccount(int accountId);

    Account findByAccountId(Integer accountId);
}
