package com.kaishengit.crm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Customer implements Serializable {
    private Integer id;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 职位
     */
    private String jobTitle;

    /**
     * 客户电话
     */
    private String customPhone;

    /**
     * 客户住址
     */
    private String address;

    /**
     * 来源
     */
    private String trade;

    /**
     * 级别
     */
    private String level;

    /**
     * 来源
     */
    private String source;

    /**
     * 记号
     */
    private String mark;

    /**
     * 用户id
     */
    private Integer accountId;

    private Date lastChatTime;

    private Date createTime;

    private Date updateTime;

    private String sex;

    private String reminder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCustomPhone() {
        return customPhone;
    }

    public void setCustomPhone(String customPhone) {
        this.customPhone = customPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getLastChatTime() {
        return lastChatTime;
    }

    public void setLastChatTime(Date lastChatTime) {
        this.lastChatTime = lastChatTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", customPhone='" + customPhone + '\'' +
                ", address='" + address + '\'' +
                ", trade='" + trade + '\'' +
                ", level='" + level + '\'' +
                ", source='" + source + '\'' +
                ", mark='" + mark + '\'' +
                ", accountId=" + accountId +
                ", lastChatTime=" + lastChatTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sex='" + sex + '\'' +
                ", reminder='" + reminder + '\'' +
                '}';
    }
}