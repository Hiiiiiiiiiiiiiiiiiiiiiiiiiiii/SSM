package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.example.DeptExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.DeptMapper;
import com.kaishengit.crm.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    DeptMapper deptMapper;

    /**
     * 查询所有的部门
      * @return
     */
    @Override
    public List<Dept> findAllDept() {
        return deptMapper.selectByExample(new DeptExample());
    }

    @Override
    public List<Dept> findByAccountId(int accountId) {
        return deptMapper.findByAccountId(accountId);
    }

    @Override
    public Integer saveNewDept(String deptName,Integer pid) {
        //1.查询该部门名称是否存在
        DeptExample deptExample = new DeptExample();
        deptExample.createCriteria().andDeptNameEqualTo(deptName);
        List<Dept> deptList = deptMapper.selectByExample(deptExample);
        if(deptList.size()!=0){
            throw new ServiceException("该部门名称已存在!!!");
            //2.新增部门
        }
        Dept dept = new Dept();
        dept.setDeptName(deptName);
        dept.setDeptPid(pid);
        deptMapper.insertSelective(dept);
        return dept.getDeptId();
    }
}
