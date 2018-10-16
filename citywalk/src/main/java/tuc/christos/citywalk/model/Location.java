package tuc.christos.citywalk.model;

public class Location {
	private double latitude;
	private double longitude;
	
	public Location(){
		
	}
	public Location(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
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
	public double getDistanceFrom(Location loc){
		// Earths Mean Radius
		double R=6371e3; 
		//Convert to radians
		double lat1 = this.latitude*(Math.PI/180);
		double lon1 = this.longitude*(Math.PI/180);
		double latdes = loc.getLatitude()*(Math.PI/180);
		double londes = loc.getLongitude()*(Math.PI/180);
		
		//haversine formula
		double dF = latdes-lat1;
		double dL = londes-lon1;
		double a = Math.sin(dF/2) * Math.sin(dF/2) +
		        Math.cos(lat1) * Math.cos(latdes) *
		        Math.sin(dL/2) * Math.sin(dL/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return R*c;
	}
}
