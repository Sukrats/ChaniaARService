package tuc.christos.citywalk.service;

import java.sql.*;
import java.util.ArrayList;

import tuc.christos.citywalk.database.DBUtility;
import tuc.christos.citywalk.exceptions.DataNotFoundException;
import tuc.christos.citywalk.model.Location;
import tuc.christos.citywalk.model.Scene;
import tuc.christos.citywalk.model.tempScenes;

public class SceneService {

	public static ArrayList<Scene> getAllScenes(){
		ArrayList<Scene> ScenesList = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtility.createConnection();
			stmt = conn.createStatement();
			
			String query = "SELECT * FROM scene";
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Scene temp = new Scene();
				temp.setId(rs.getLong(1));
				temp.setName(rs.getString(2));
				temp.setDescription(rs.getString(3));
				temp.setLatitude(rs.getDouble(4));
				temp.setLongitude(rs.getDouble(5));
				temp.setNumOfVisits(rs.getInt(6));
				temp.setNumOfPlaces(rs.getInt("numofsaves"));
				temp.setPeriodId(rs.getLong(7));
				
				temp.setThumb((rs.getString("thumb_loc") == null ? "" : rs.getString("thumb_loc")));
				temp.setImgLoc((rs.getString("images_loc") == null ? "" : rs.getString("images_loc")));
				ScenesList.add(temp);
			}
			
			conn.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		        	stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		        	conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		
		if(ScenesList.isEmpty())
			throw new DataNotFoundException("No scenes found!");
		
		return ScenesList; 
	}
	
	public static ArrayList<Scene> getScenesForPeriod(long periodId){
		ArrayList<Scene> scenesList = new ArrayList<Scene>();
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = DBUtility.createConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM scene WHERE period_id = '"+periodId+"';";
			
			if(stmt.execute(query))
			{
				ResultSet rs = stmt.getResultSet();
				while(rs.next()){
					Scene temp = new Scene();
					temp.setId(rs.getLong("scene_id"));
					temp.setName(rs.getString("name"));
					temp.setDescription(rs.getString("description"));
					temp.setLatitude(rs.getDouble("latitude"));
					temp.setLongitude(rs.getDouble("longitude"));
					temp.setNumOfVisits(rs.getInt("numofvisits"));
					temp.setNumOfPlaces(rs.getInt("numofsaves"));
					temp.setPeriodId(rs.getLong("period_id"));
					
					temp.setThumb((rs.getString("thumb_loc") == null ? "" : rs.getString("thumb_loc")));
					temp.setImgLoc((rs.getString("images_loc") == null ? "" : rs.getString("images_loc")));
					scenesList.add(temp);
				}
			}
			
			conn.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		   //finally block used to close resources
		   try{
		      if(stmt!=null)
		    	  stmt.close();
		   }catch(SQLException se2){
		   
		   }// nothing we can do
		   try{
			   if(conn!=null)
				   conn.close();
		   }catch(SQLException se){
		      se.printStackTrace();
		   }//end finally try
		}//end try
		
		
		if(scenesList.isEmpty())
			throw new DataNotFoundException("Scenes for period with id: "+periodId+" not found!");
		return scenesList;
	}
	
	
	public static ArrayList<Scene> getScenesForLocation(String country,String admin_area){
		ArrayList<Scene> scenesList = new ArrayList<Scene>();
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = DBUtility.createConnection();
			stmt = conn.createStatement();
			String query = " SELECT * FROM ( countries AS c "
						  +" INNER JOIN levels AS a ON c.country_id = a.country_id ) "
						  +" INNER JOIN scene AS s ON a.admin_area_id = s.admin_area_id "
						  +" WHERE c.country_name = '"+country+"' AND a.admin_area_name = '"+admin_area+"'";
			
			System.out.println(query);
			if(stmt.execute(query))
			{
				ResultSet rs = stmt.getResultSet();
				while(rs.next()){
					Scene temp = new Scene();
					temp.setId(rs.getLong("scene_id"));
					temp.setName(rs.getString("name"));
					temp.setDescription(rs.getString("description"));
					temp.setLatitude(rs.getDouble("s.latitude"));
					temp.setLongitude(rs.getDouble("s.longitude"));
					temp.setNumOfVisits(rs.getInt("numofvisits"));
					temp.setNumOfPlaces(rs.getInt("numofsaves"));
					temp.setPeriodId(rs.getLong("period_id"));
					temp.setRegion(rs.getString("admin_area_name"));
					
					temp.setThumb((rs.getString("thumb_loc") == null ? "" : rs.getString("thumb_loc")));
					temp.setImgLoc((rs.getString("images_loc") == null ? "" : rs.getString("images_loc")));
					scenesList.add(temp);
				}
			}
			
			conn.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		   //finally block used to close resources
		   try{
		      if(stmt!=null)
		    	  stmt.close();
		   }catch(SQLException se2){
		   
		   }// nothing we can do
		   try{
			   if(conn!=null)
				   conn.close();
		   }catch(SQLException se){
		      se.printStackTrace();
		   }//end finally try
		}//end try
		
		
		if(scenesList.isEmpty())
			throw new DataNotFoundException("Scenes for City: "+admin_area+" not found!");
		return scenesList;
	}

	
	public static ArrayList<Scene> getScenesForAbsoluteLocation(Location loc){
	ArrayList<Scene> scenesList = new ArrayList<Scene>();
	Connection conn = null;
	Statement stmt = null;

	Location dest = new Location();
	double bound = 0;
	long areaCode;
	try {
		conn = DBUtility.createConnection();
		stmt = conn.createStatement();
		String query = " SELECT * FROM levels ";

		System.out.println(query);
		if(stmt.execute(query))
		{
			ResultSet rs = stmt.getResultSet();
			while(rs.next()){
				dest.setLatitude(rs.getDouble("latitude"));
				dest.setLongitude(rs.getDouble("longitude"));;
				bound = rs.getDouble("bound");
				areaCode = rs.getLong("admin_area_id");
				
				if(loc.getDistanceFrom(dest) <= bound){
					query = "SELECT * FROM scene WHERE admin_area_id = "+areaCode;
					System.out.println(query);
					if(stmt.execute(query))
					{
						ResultSet rs1 = stmt.getResultSet();
						while(rs1.next()){
							Scene temp = new Scene();
							temp.setId(rs1.getLong("scene_id"));
							temp.setName(rs1.getString("name"));
							temp.setDescription(rs1.getString("description"));
							temp.setLatitude(rs1.getDouble("latitude"));
							temp.setLongitude(rs1.getDouble("longitude"));
							temp.setNumOfVisits(rs1.getInt("numofvisits"));
							temp.setNumOfPlaces(rs.getInt("numofsaves"));
							temp.setPeriodId(rs1.getLong("period_id"));
							temp.setRegion(rs1.getString("admin_area_name"));
							
							temp.setThumb((rs1.getString("thumb_loc") == null ? "" : rs1.getString("thumb_loc")));
							temp.setImgLoc((rs1.getString("images_loc") == null ? "" : rs1.getString("images_loc")));
							scenesList.add(temp);
						}
					}
					break;
				}
			}
		}
		
		conn.close();
		stmt.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
	   //finally block used to close resources
	   try{
	      if(stmt!=null)
	    	  stmt.close();
	   }catch(SQLException se2){
	   
	   }// nothing we can do
	   try{
		   if(conn!=null)
			   conn.close();
	   }catch(SQLException se){
	      se.printStackTrace();
	   }//end finally try
	}//end try
	
	
	if(scenesList.isEmpty())
		throw new DataNotFoundException("No Content available for your area!");
	return scenesList;
}
	
	public static Scene getScene(long id){
		Scene temp = new Scene();
		boolean empty = false;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtility.createConnection();
			stmt = conn.createStatement();
			
			String query = "SELECT * FROM scene WHERE scene_id = '"+id+"';";
			
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next())			
			{
				empty = true;
				temp.setId(rs.getLong(1));
				temp.setName(rs.getString(2));
				temp.setDescription(rs.getString(3));
				temp.setLatitude(rs.getDouble(4));
				temp.setLongitude(rs.getDouble(5));
				temp.setNumOfVisits(rs.getInt(6));
				temp.setNumOfPlaces(rs.getInt("numofsaves"));
				temp.setPeriodId(rs.getLong(7));
				
				temp.setThumb((rs.getString("thumb_loc") == null ? "" : rs.getString("thumb_loc")));
				temp.setImgLoc((rs.getString("images_loc") == null ? "" : rs.getString("images_loc")));
			}
			conn.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			   //finally block used to close resources
			   try{
			      if(stmt!=null)
			    	  stmt.close();
			   }catch(SQLException se2){
			   
			   }// nothing we can do
			   try{
				   if(conn!=null)
					   conn.close();
			   }catch(SQLException se){
			      se.printStackTrace();
			   }//end finally try
			}//end try
			
			
		if(!empty)
			throw new DataNotFoundException("Scene with id "+ id +" not found!");
		return temp;
	}
	
	public static Scene addScene(Scene scene){
		
		return new Scene();
		
	}
	
	public static boolean addScenes(tempScenes scenes){
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtility.createConnection();
			
			stmt = conn.createStatement();
			String query = "";
			for(Scene temp: scenes.getScenes()){
				String tempQ= "INSERT INTO scene ( name , description , latitude , longitude , numofvisits , period_id ) "
						+ "VALUES ( "+ "'" + temp.getName() + "',"+
						"'"+temp.getDescription() +"',"+
						"'"+temp.getLatitude() +"',"+
						"'"+temp.getLongitude() +"',"+
						"'"+ 0 +"',"+
						"'"+temp.getPeriodId() +"');";
				query +=tempQ;
			}
			System.out.println(query);
			if(!query.equals("")){
				int count = stmt.executeUpdate(query);
				System.out.print(count);
				if(count > 0){
					return true;
				}
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			   //finally block used to close resources
			   try{
			      if(stmt!=null)
			    	  stmt.close();
			   }catch(SQLException se2){
			   
			   }// nothing we can do
			   try{
				   if(conn!=null)
					   conn.close();
			   }catch(SQLException se){
			      se.printStackTrace();
			   }//end finally try
			}//end try
			
			
		return false;
	}
	
	public static Scene updateScene(Scene scene){
		return new Scene();
	}
	
	public static Scene deleteScene(long id){
		return 	new Scene();
	}
	
	public static Timestamp checkSync(){
		Connection conn = null;
		Statement stmt = null;
		Timestamp time = null;
		try {
			conn = DBUtility.createConnection();
			stmt = conn.createStatement();
			String query = "SELECT update_time "
					+ "FROM   modifications "
					+ "WHERE  table_name = 'scene' ";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()){
				time = (rs.getTimestamp("update_time"));
			}
			if(time!= null){
				System.out.println(time.toString());
			}else{
				System.out.println("No time stamp");
			}
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}
	
}
