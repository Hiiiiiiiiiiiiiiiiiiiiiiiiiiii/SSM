package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> findByAccountId(Integer accountId);

    void save(Task task);

    void changeState(Integer taskId);

    void deleteByKey(Integer taskId);
}
