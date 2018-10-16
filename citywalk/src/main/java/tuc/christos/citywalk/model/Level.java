package tuc.christos.citywalk.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Level {
	private long admin_area_id;
	private String admin_area_name;
	private long country_id;
	private String country_name;
	private double latitude;
	private double longitude;
	private double bound;
	
	public Level(){
		
	}
	
	public Level(long admin_area_id, String admin_area_name, double latitude, double longitude, double bound) {
		super();
		this.admin_area_id = admin_area_id;
		this.admin_area_name = admin_area_name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.bound = bound;
	}

	public long getAdmin_area_id() {
		return admin_area_id;
	}

	public void setAdmin_area_id(long admin_area_id) {
		this.admin_area_id = admin_area_id;
	}

	public String getAdmin_area_name() {
		return admin_area_name;
	}

	public void setAdmin_area_name(String admin_area_name) {
		this.admin_area_name = admin_area_name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getBound() {
		return bound;
	}

	public void setBound(double bound) {
		this.bound = bound;
	}

	public long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(long country_id) {
		this.country_id = country_id;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	
	
	

}
