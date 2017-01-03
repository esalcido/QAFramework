package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import aggregates.Aggregate;
import project.Report;
import project.User;

public class Database {

	
	private String dbName;
	private String userName;
	private String password;
	private Connection con;
	//localhost/qa_platform
	//root
	//qazwsx
	
	public Database(String dbn,String un,String pw){
		dbName = dbn;
		userName=un;
		password = pw;
	}
	
	
	public void connect(){
	//Database stuff
			try{
				Class.forName("com.mysql.jdbc.Driver");  
				con=DriverManager.getConnection(  
				"jdbc:mysql://"+dbName+"",userName,password);  
			
				//System.out.println("success");
			}catch(Exception e){
				System.out.println("error connecting to db"+e);
			}
	}
	
	public void disconnect(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  ArrayList<User> getUsersDB() {
		Statement stmt;
		ArrayList<User> users= new ArrayList<User>();
		try {
			
			stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("select * from user" );
		
		while(rs.next()){
			//System.out.println(rs.getString(2)+ " " +rs.getString(3));
			User usr = new User(rs.getString(2),rs.getString(3),0);
			users.add(usr);
		}
		

		stmt.close();
		rs.close();
		
		return users;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return users;
		}
		
		
	}
	
	public void insertUsers(String [] params){
		Statement st;
		try{
			st = con.createStatement();
			
			String sqlString = "INSERT INTO user(uid,password) VALUES('"+params[0]+"','"+params[1]+"')";
			
			st.execute(sqlString);
			st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//grab reports based on user
	public ArrayList<Report> getReport(User usr){
		
		Statement st;
		Report rpt;
		ArrayList<Report> reports = new ArrayList<Report>();
		try{
			st = con.createStatement();
		
			String sqlString = "SELECT DISTINCT report.path, report.filename  FROM report INNER JOIN user ON report.uid='"+usr.getUid()+"'";
			
			ResultSet rs = st.executeQuery(sqlString);
			while(rs.next()){
				
				String [] path = rs.getString(1).split("/");
				rpt = new Report( rs.getString(1), rs.getString(2) );
				reports.add(rpt);
				//System.out.println("from db report: "+rs.getString(1)+" "+ rs.getString(2));
			}
			
			st.execute(sqlString);
			st.close();
			
			return reports;
			
		}catch(Exception e){
			e.printStackTrace();
			return reports;
		}
		
	}
	
	//grab reports based on user
		public ArrayList<Aggregate> getAggregates(String userType){
			
			Statement st;
			Aggregate agg;
			ArrayList<Aggregate> aggregates = new ArrayList<Aggregate>();
			try{
				st = con.createStatement();
			
				String sqlString = "SELECT * FROM aggregate_names WHERE userType='"+userType+"'";
				
				ResultSet rs = st.executeQuery(sqlString);
				while(rs.next()){
					
					
					agg = new Aggregate( rs.getString(2), rs.getString(3) );
					aggregates.add(agg);
					//System.out.println("from db report: "+rs.getString(1)+" "+ rs.getString(2));
				}
				
				st.execute(sqlString);
				st.close();
				
				return aggregates;
				
			}catch(Exception e){
				e.printStackTrace();
				return aggregates;
			}
			
		}
	
}
