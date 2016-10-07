package com.talentrecd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentrecd.dao.IDepartmentDao;
import com.talentrecd.dao.IHrDao;
import com.talentrecd.dao.IJobDao;
import com.talentrecd.dao.IRecruitDao;
import com.talentrecd.model.Department;
import com.talentrecd.model.Job;
import com.talentrecd.model.Recruit;
import com.talentrecd.service.HrService;

@Service("hrServiceImpl")
public class HrServiceImpl implements HrService{

	@Autowired
	private IHrDao hrDao;
	@Autowired
	private IRecruitDao recruitDao;
	@Autowired
	private IDepartmentDao departmentDao;
	@Autowired
	private IJobDao jobDao;
	
	
	@Override
	public void add(Recruit recruit,int depId,int jobId) {
		try {
			Job job=jobDao.load(jobId);
			Department dep=departmentDao.load(depId);
			recruit.setDepartment(dep);
			recruit.setJob(job);
			recruitDao.add(recruit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
