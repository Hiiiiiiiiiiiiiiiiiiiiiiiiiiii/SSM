package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.example.DiskExample;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DiskServiceImpl implements DiskService{
    private static final String DIR_TYPE = "dir";
    @Autowired
    DiskMapper diskMapper;
    @Override
    public List<Disk> findAllFile() {
        return diskMapper.selectByExample(new DiskExample());
    }

    @Override
    public List<Disk> findFileByPid(Integer pid) {
        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andPidEqualTo(pid);
        diskExample.setOrderByClause("type");
        List<Disk> diskList = diskMapper.selectByExample(diskExample);
        for(Disk disk : diskList){
            Date date = disk.getUpdateTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd日");
            disk.setNewTime(simpleDateFormat.format(date));
        }
        return diskList;
    }

    @Override
    public void createDir(Integer pid, String dirName,Integer accountId) {
        //创建文件对象
        Disk disk = new Disk();
        disk.setAccountId(accountId);
        disk.setPid(pid);
        disk.setName(dirName);
        disk.setUpdateTime(new Date());
        disk.setType(DIR_TYPE);
        //创建文件夹
        diskMapper.insert(disk);
    }
}
