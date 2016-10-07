package com.talentrecd.dao.impl;

import org.springframework.stereotype.Repository;

import com.talentrecd.dao.IHrDao;
import com.talentrecd.model.Hr;

@Repository("hrDao")
@SuppressWarnings("unchecked")
public class HrDaoImpl extends BaseDaoImpl<Hr> implements IHrDao{

}
