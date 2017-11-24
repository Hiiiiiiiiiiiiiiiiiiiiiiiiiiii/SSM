package com.kaishengit.crm.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class AccountDeptKey implements Serializable {
    private Integer userId;

    private Integer deptId;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}