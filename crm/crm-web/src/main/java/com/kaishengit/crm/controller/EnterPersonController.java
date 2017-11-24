package com.kaishengit.crm.controller;


import com.kaishengit.crm.entity.Account;

import javax.servlet.http.HttpSession;

public abstract class EnterPersonController {

    public int getCustomerAccount(HttpSession httpSession){
        Account account = (Account) httpSession.getAttribute("account");
        return account.getUserId();
    }

}
