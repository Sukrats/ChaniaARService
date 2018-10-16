package tuc.christos.citywalk.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import tuc.christos.citywalk.database.DBUtility;
import tuc.christos.citywalk.exceptions.DataNotFoundException;
import tuc.christos.citywalk.model.Level;
import tuc.christos.citywalk.model.Location;

public class LevelsService {
	
	public static ArrayList<Level> getLevels(){
		ArrayList<Level> levels = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		Level temp = null;
		try {
			conn = DBUtility.createConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM levels as l "
					+ " INNER JOIN countries as c ON l.country_id = c.country_id";

			System.out.println(query);
			if(stmt.execute(query))
			{
				ResultSet rs = stmt.getResultSet();
				while(rs.next()){
					temp = new Level(rs.getLong("admin_area_id"),
								rs.getString("admin_area_name"),
								rs.getDouble("latitude"),
								rs.getDouble("longitude"),
								rs.getDouble("bound"));
					temp.setCountry_id(rs.getLong("country_id"));
					temp.setCountry_name(rs.getString("country_name"));
					levels.add(temp);
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
		
		
		if(levels.isEmpty())
			throw new DataNotFoundException("No levels found!");

		return levels;
	}
		
		
	public static Level getLevelFromLocation(Location loc){
		Level temp = null;
		Connection conn = null;
		Statement stmt = null;

		Location dest = new Location();
		double bound = 0;
		
		try {
			conn = DBUtility.createConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM levels as l "
					+ " INNER JOIN countries as c ON l.country_id = c.country_id";

			System.out.println(query);
			if(stmt.execute(query))
			{
				ResultSet rs = stmt.getResultSet();
				while(rs.next()){
					dest.setLatitude(rs.getDouble("latitude"));
					dest.setLongitude(rs.getDouble("longitude"));;
					
					bound = rs.getDouble("bound");
					
					if(loc.getDistanceFrom(dest) <= bound){
						temp = new Level(rs.getLong("admin_area_id"),
								rs.getString("admin_area_name"),
								rs.getDouble("latitude"),
								rs.getDouble("longitude"),
								rs.getDouble("bound"));
						temp.setCountry_id(rs.getLong("country_id"));
						temp.setCountry_name(rs.getString("country_name"));
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
		   try{
		      if(stmt!=null)
		    	  stmt.close();
		   }catch(SQLException se2){
		   
		   }
		   try{
			   if(conn!=null)
				   conn.close();
		   }catch(SQLException se){
		      se.printStackTrace();
		   }
		}
		
		
		if(temp == null)
			throw new DataNotFoundException("No level found for your Location!");
		
		return temp;
	}
	
	
	
	
	
	public static Level getLevel(long id){
		Level temp = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtility.createConnection();
			stmt = conn.createStatement();
			
			String query = "SELECT * FROM levels as l "
					+ " INNER JOIN countries as c ON l.country_id = c.country_id"
					+ " WHERE admin_area_id = '"+id+"';";
			
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next())			
			{
				temp= new Level(rs.getLong("admin_area_id"),
						rs.getString("admin_area_name"),
						rs.getDouble("latitude"),
						rs.getDouble("longitude"),
						rs.getDouble("bound"));
				temp.setCountry_id(rs.getLong("country_id"));
				temp.setCountry_name(rs.getString("country_name"));
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
			
			
		if(temp == null)
			throw new DataNotFoundException("Level not Found!");
		return temp;
	}
}
