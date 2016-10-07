package com.talentrecd.dao.impl;

import org.springframework.stereotype.Repository;

import com.talentrecd.dao.IDepartmentDao;
import com.talentrecd.dao.IJobDao;
import com.talentrecd.model.Department;
import com.talentrecd.model.Job;

@Repository("departmentDao")
@SuppressWarnings("unchecked")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements IDepartmentDao{

}
