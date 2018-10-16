package tuc.christos.citywalk.model;


import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Place {

	private Date created;
	private long scene_id;
	private String comment;
	private String username;
	private String admin_area_name;
	private String scene_name;
	private String scene_visits;
	private String scene_saves;
	
	public Place(){}
	
	public Place(String Username, long scene_id) {
		super();
		this.scene_id = scene_id;
		this.username = Username;
	}
	
	public Place(long scene_id, String username, String comment) {
		super();
		this.scene_id = scene_id;
		this.username = username;
		this.comment = comment;
	}
	
	public Place(long scene_id, Date created, String username, String comment) {
		super();
		this.scene_id = scene_id;
		this.created = created;
		this.username = username;
		this.comment = comment;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getScene_id() {
		return scene_id;
	}

	public void setScene_id(long scene_id) {
		this.scene_id = scene_id;
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
