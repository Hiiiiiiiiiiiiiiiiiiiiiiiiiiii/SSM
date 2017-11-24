package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Disk;

import java.util.List;

public interface DiskService {
    List<Disk> findAllFile();

    List<Disk> findFileByPid(Integer pid);

    void createDir(Integer pid, String dirName,Integer AccountId);
}
