package com.talentrecd.dao.impl;

import org.springframework.stereotype.Repository;

import com.talentrecd.dao.IJobDao;
import com.talentrecd.model.Job;

@Repository("jobDao")
@SuppressWarnings("unchecked")
public class JobDaoImpl extends BaseDaoImpl<Job> implements IJobDao{

}
