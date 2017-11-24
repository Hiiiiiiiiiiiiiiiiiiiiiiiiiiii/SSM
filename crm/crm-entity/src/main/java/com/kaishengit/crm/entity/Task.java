package com.kaishengit.crm.entity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 
 */
public class Task implements Serializable {
    private Integer id;

    private String title;

    private Date finishTime;

    private Date remindTime;

    private Byte done;

    private Integer accountId;

    private Integer custId;

    private Integer saleId;

    private Date createTime;

    private Customer customer;

    private SaleChance saleChance;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public Byte getDone() {
        return done;
    }

    public void setDone(Byte done) {
        this.done = done;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SaleChance getSaleChance() {
        return saleChance;
    }

    public void setSaleChance(SaleChance saleChance) {
        this.saleChance = saleChance;
    }

    public boolean isOverTime() {
        //String -> DateTime
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newFinshTime = simpleDateFormat.format(getFinishTime());
        DateTime finishTimes = formatter.parseDateTime(newFinshTime);

        return finishTimes.isBeforeNow();
    }

}