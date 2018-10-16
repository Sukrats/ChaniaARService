package tuc.christos.citywalk.model;


import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Visit {

	private long scene_id;
	private Date created;
	private String username;
	private String admin_area_name;
	private String scene_name;
	private String scene_visits;
	private String scene_saves;

	
	public Visit(){}
	
	public Visit(long id) {
		super();
		this.scene_id = id;
	}
	
	public long getScene_id() {
		return scene_id;
	}

	public void setScene_id(long scene_id) {
		this.scene_id = scene_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String getAdmin_area_name() {
		return admin_area_name;
	}

	public void setAdmin_area_name(String admin_area_name) {
		this.admin_area_name = admin_area_name;
	}

	public String getScene_name() {
		return scene_name;
	}

	public void setScene_name(String scene_name) {
		this.scene_name = scene_name;
	}

	public String getScene_visits() {
		return scene_visits;
	}

	public void setScene_visits(String scene_visits) {
		this.scene_visits = scene_visits;
	}

	public String getScene_saves() {
		return scene_saves;
	}

	public void setScene_saves(String scene_saves) {
		this.scene_saves = scene_saves;
	}
	
	
	
}
