package com.kaishengit.crm.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Dept implements Serializable {
    private Integer deptId;

    private String deptName;

    private Integer deptPid;

    private static final long serialVersionUID = 1L;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptPid() {
        return deptPid;
    }

    public void setDeptPid(Integer deptPid) {
        this.deptPid = deptPid;
    }
}