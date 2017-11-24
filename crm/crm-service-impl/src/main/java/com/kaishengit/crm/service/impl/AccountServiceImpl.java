package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.AccountDeptKey;
import com.kaishengit.crm.example.AccountDeptExample;
import com.kaishengit.crm.example.AccountExample;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.AccountDeptMapper;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountDeptMapper accountDeptMapper;

    public static final Integer COMPANY_ID = 1;
    @Override
    public List<Account> pageForAccount(Map<String, Object> queryParam) {
        Integer start = (Integer) queryParam.get("start");
        Integer length = (Integer) queryParam.get("length");
        Integer deptId = (Integer) queryParam.get("deptId");
        String accountName = (String) queryParam.get("accountName");

        if(deptId == null || COMPANY_ID.equals(deptId)) {
            deptId = null;
        }

        List<Account> accountList = accountMapper.findByDeptId(accountName,deptId,start,length);

        return accountList;
    }


    @Override
    public List<Account> findAllAccount() {
        return accountMapper.selectByExample(new AccountExample());
    }

    @Override
    public Account login(String mobile, String password) throws AuthenticationException{
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andUserMobileEqualTo(mobile);
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        Account account = null;
        if(accountList!=null&&!accountList.isEmpty()){
            account = accountList.get(0);
        }
        if(account!=null&&account.getUserPassword().equals(password)){
            return account;
        }else{
            throw new AuthenticationException("账号或者密码不正确");
        }

    }
    @Override
    public Long accountCountByDeptId(Integer deptId) {
        if(deptId == null || COMPANY_ID.equals(deptId)) {
            deptId = null;
        }
        return accountMapper.countByDeptId(deptId);
    }

    @Override
    @Transactional
    public void saveNewEmployee(String userName, String mobile, String password, Integer[] deptId) {
        //1.检验手机号是否被使用
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andUserMobileEqualTo(mobile);
        List<Account> accountList = accountMapper.selectByExample(accountExample);

        if(accountList!=null&&!accountList.isEmpty()){
            throw new ServiceException("该手机号已被注册!!!");
        }
        //2.保存账号
        Account account = new Account();
        account.setUserName(userName);
        account.setUserMobile(mobile);
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        account.setUserPassword(password);
        accountMapper.insertSelective(account);

        AccountExample accountExample1 =  new AccountExample();
        accountExample1.createCriteria().andUserNameEqualTo(userName);
        List<Account> accountList1 = accountMapper.selectByExample(accountExample1);

        /**
         *
         */
        //3.添加关联关系
        for(Integer dept : deptId){
            AccountDeptKey accountDeptKey = new AccountDeptKey();
            accountDeptKey.setDeptId(dept);
            accountDeptKey.setUserId(accountList1.get(0).getUserId());
            accountDeptMapper.insert(accountDeptKey);
        }
    }

    @Override
    public void deleteAccount(int accountId) {
        //1.删除该员工的所有信息
        accountMapper.deleteByPrimaryKey(accountId);
        //2.删除该员工的所有关联关系
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andUserIdEqualTo(accountId);
        accountDeptMapper.deleteByExample(accountDeptExample);
    }

    @Override
    public Account findByAccountId(Integer accountId) {
        Account account = accountMapper.selectByPrimaryKey(accountId);
        return account;
    }
}
