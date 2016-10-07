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
	 * -1Ϊ������0Ϊδͨ��ɸѡ��1Ϊ�ȴ����ԣ�2Ϊ���޺���ְλ��3Ϊͨ�����Եȴ����棻
	 * 4Ϊδͨ�����ԣ�5Ϊͨ�����淢��offer��6Ϊδͨ�����棻7Ϊ����ְ��8Ϊδ��ְ
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
