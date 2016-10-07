package com.talentrecd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_admin")
public class Admin {
	
	private int id;
	private String adminame;
	private String password;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAdminame() {
		return adminame;
	}
	public void setAdminame(String adminame) {
		this.adminame = adminame;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Admin [id=" + id + ", adminame=" + adminame + ", password="
				+ password + "]";
	}
	
	
}
