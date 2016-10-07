package com.talentrecd.service;

import com.talentrecd.model.Recruit;

public interface HrService {
	//发布招聘信息
	public void add(Recruit recruit,int depId,int jobId);
}
