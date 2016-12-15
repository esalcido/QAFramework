package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import files.FileHandler;
import project.Environments;
import project.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;


public class Projects {

	public static WebDriver driver = new FirefoxDriver();
			
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//Database stuff
//		try{
//			Class.forName("com.mysql.jdbc.Driver");  
//			Connection con=DriverManager.getConnection(  
//			"jdbc:mysql://localhost/qa_platform","root","qazwsx");  
//			
//			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("select * from User" );
//			
//			while(rs.next()){
//				System.out.println(rs.getString(2)+ " " +rs.getString(3));
//			}
//			
//			//System.out.println("success");
//		}catch(Exception e){
//			System.out.println("error connecting to db"+e);
//		}

		HashMap<String,String> hmap = new HashMap<String,String>();
		boolean pinEx=false,pinNav=false,pwIncorrect=false;
		
		String environment = Environments.DEV86.url();
		
		//======================= Handle the files =================================
		
		//handles file with usernames and passwords
		FileHandler fh = new FileHandler("resources/users1.csv","resources/output/useroutput.csv");
		fh.createFile();
		
		try {
			 //hmap = readFile(fileName);
			hmap = fh.readFile();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get users from file
		ArrayList<User> users = fh.readFileUsersArr();
		int marker =0;
		for(int i=0;i<users.size();i++){
			User usr = users.get(i);
			//set env and driver for them
			usr.setEnvironment(environment);
			usr.setDriver(driver);
			
			
  			fh.writeToFile("User: "+usr.getUid()+",");
  			if(usr.signIn()){
  				//write password result to file
  				fh.writeToFile("True,");
  				
  				//check what project users belong to.  Explorer or navigator
  				//checkProjects(pinEx,pinNav,fh);
  			
  				usr.waitSec(5);
  				//run a report
  				usr.runReport(marker);
  				
	  			usr.signOut();
	  			
	  			marker += usr.getNumOfUsers();
  			}
  			else{
  				fh.writeToFile("False,");
  			}
  			fh.writeToFile("\r\n");
			
		}
	  			
		//dynamically get the objects
//		for(int i=1;i<3;i++){
//			String projectName = driver.findElement(By.xpath(".//*[@id='projects_ProjectsStyle']/table/tbody/tr/td["+i+"]/div/table/tbody/tr/td[2]/div/a")).getText();
//			
//			System.out.println(projectName);
//		}
	 
		//create the user
		//User user1 = new User("qausa","4Quality@WLV",environment,driver);	
		//get reports from file		
//		user1.signIn();
//		user1.runReport(2);
//		user1.signOut();
	
		
	  	System.out.println("End of script.");
		
	  	fh.close();
	  	
		
	}
	
	public static void checkProjects(boolean pinEx, boolean pinNav,FileHandler fw) throws IOException{
		try{
		
			//check for Pin Explorer
			 pinEx = driver.findElement(By.xpath("//a[contains(text(),'PIN Explorer')]") ).isDisplayed();	
		}catch(Exception e){
			//System.out.println(e);	
		}
		try{
			//check for Navigator
			 pinNav = driver.findElement(By.xpath("//a[contains(text(),'PIN Navigator')]") ).isDisplayed();
			
		}catch(Exception e){
			//System.out.println(e);
		}
		//System.out.println(" Pin Ex: "+ pinEx + " PIN Nav: "+pinNav);
		fw.writeToFile(pinEx+",");
		 fw.writeToFile(pinNav+" ");
	}
	
	
}