package com.talentrecd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_recommend")
public class Recommend {
	private int id;
	private Recruit recruit;
	private Resume resume;
	/**
	 * -1为待处理；0为未通过筛选；1为等待初试；2为暂无合适职位；3为通过初试等待终面；
	 * 4为未通过初试；5为通过终面发放offer；6为未通过终面；7为已入职；8为未入职
	 */
	private int status;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="recruit_id")
	public Recruit getRecruit() {
		return recruit;
	}
	public void setRecruit(Recruit recruit) {
		this.recruit = recruit;
	}
	@ManyToOne
	@JoinColumn(name="resume_id")
	public Resume getResume() {
		return resume;
	}
	public void setResume(Resume resume) {
		this.resume = resume;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
