package com.kaishengit.crm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Disk implements Serializable {
    private Integer id;

    private String name;

    private String size;

    private Integer pid;

    private String type;

    private Date updateTime;

    private Integer accountId;

    private String downloadName;

    private String password;

    private String saveName;

    private String md5;

    private String newTime;

    private static final long serialVersionUID = 1L;

    public String getNewTime() {
        return newTime;
    }

    public void setNewTime(String newTime) {
        this.newTime = newTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}