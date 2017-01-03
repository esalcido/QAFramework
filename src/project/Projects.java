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

import aggregates.Aggregate;
import database.Database;


public class Projects {

	 public static WebDriver driver = new FirefoxDriver();
			
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		HashMap<String,String> hmap = new HashMap<String,String>();
		boolean pinEx=false,pinNav=false,pwIncorrect=false;
		
//		String environment = Environments.DEV86.url();
		String environment = Environments.DEV90.url();
		
		//======================= Handle the files =================================
		
		//handles file with usernames and passwords
		FileHandler fh = new FileHandler("resources/users1.csv","resources/output/useroutput.csv");
		fh.createFile();
		
		//reads in users and inserts them to the DB
		//fh.readFileAddUsersToDB();
		
		//Run reports of users from the database
			//runReportsFromDB(environment);

		//=============================================================================

		//grab users from DB
			Database db = new Database("localhost/qa_platform","root","qazwsx");
			db.connect();
			ArrayList<Aggregate> aggs = db.getAggregates("usa");
			db.disconnect();
			
			User user = new User("qausa","4Quality@WLV",environment,driver);
			user.signIn();
			
			//get to home page
			user.clickON("//a[text()='PIN Explorer']",1);
			
			//for(Aggregate ag:aggs){
			for(int i =0 ;i<1; i ++){
				System.out.println("name: "+ aggs.get(i).toString());
			
				//dev90
				//click on aggregates in the left menu
				user.waitSec(10);
				user.clickON("//a[contains(text(),'Aggregates')]");
				
				// Store the current window handle
				String parent_Window = driver.getWindowHandle();

				handleWindows(driver,parent_Window);
				
				//switching to child window
				
				user.clickON("//a[contains(text(),'Create Aggregates')]");

				user.waitSec( 5);

				driver.findElement(By.xpath("//a[text()='" + aggs.get(i).getName() + "']")).click();

				//enter aggregate name
				driver.findElement(By.xpath("//input[@id='id_mstr44_txt']")).sendKeys( );
				
				//expand the list of attribute options
				driver.findElement(
						By.xpath("//span[@id='id_mstr78']/div[2]/div/div/div[2]"
								+ "/div[3]/div/div[2]/div[3]/div[2]/div[1]/img"))
						.click();

				//choose the value for the attribute
				driver.findElement(By.xpath("//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div/div/div[1]/div[1]")).click();
				
				//print the attribute for testing purposes
				String element = driver.findElement(By.xpath("//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div/div/div[1]/div[1]")).getText();
				System.out.println("\n" + element);

				
				//add the value to the selected box
				driver.findElement(By.xpath("//*[@id='id_mstr79']/img")).click();

				//select status
				driver.findElement(By.xpath("//*[@id='id_mstr93ListContainer']/div[2]")).click();
				
				//wait for the page to respond
				user.waitMin( 15);
				
				try
				{
					//click run document
					driver.findElement(By.xpath("//*[@id='id_mstr98']")).click();
				
				}
				catch(Exception e)
				{
					System.out.println(aggs.get(i).getName() +" Timed Out.");
					
				}
				
					
				//click on continue box90
				driver.findElement(By.xpath(".//*[@id='mstr61']/div/div[2]/span[7]/a")).click();
						
				//driver.findElement(By.xpath("//*[@id='mstr29']/div")).click();

				
				driver.switchTo().window(parent_Window);
				System.out.println("Back to parent window = " + driver.getTitle());
			
			
			}
			
			user.signOut();

		
	  	System.out.println("End of script.");
		
	  	fh.close();
	  	
		
	}
	
	public static void runReportsFromDB(String environment) throws IOException{
	
		//grab users from DB
		Database db = new Database("localhost/qa_platform","root","qazwsx");
		db.connect();
		ArrayList<User> users = db.getUsersDB();
		
		int numOfUsers=10;
		
		//link users with their reports
		for(int i=0;i<numOfUsers;i++){
			User usr = users.get(i);
			//System.out.println("user: "+usr.getUid()+ " password: " +usr.getPw() + " environment: "+ usr.getEnvironment());
			
			//set environment 
			usr.setEnvironment(environment);
			usr.setDriver(driver);
		
			//get reports pertaining to that user
			ArrayList<Report> reports = new ArrayList<Report>();
			reports= db.getReport(usr);
			
			usr.setReports(reports);
		
			usr.printReports();
	
		}
		
		//link users with their reports
		for(int i=0;i<numOfUsers;i++){
			User usr = users.get(i);
			
			//System.out.println("user id: "+usr.getUid()+ " " +usr.getEnvironment()+ "Driver: "+usr.getDriver());
			
			if(usr.signIn()){
				usr.runReportDB();
				usr.signOut();
			}
			
		}
		
		System.out.println("number of users: "+ users.size());
		
		db.disconnect();
	}
	
	public static void runReportsFromFile(FileHandler fh, String environment) throws IOException{
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
	  			
		
	}

	public static void  handleWindows(WebDriver driver,String parent_Window){
		
		System.out.println("Before switching title is = " + driver.getTitle());

				// Click and open a new window
				// explicitWait(driver, xpath);
				// clickON(driver, xpath);

				Set<String> s1 = driver.getWindowHandles();
				Iterator<String> i1 = s1.iterator();

				while (i1.hasNext()) {
					String child_Window = i1.next();
					if (!parent_Window.equalsIgnoreCase(child_Window)) {
						driver.switchTo().window(child_Window);
						// Thread.sleep(5);
						System.out.println("After switching title is = "
								+ driver.getTitle());
						// driver.close();
					}
				}
				// driver.switchTo().window(parent_Window);
				// System.out.println("Back to parent window = " + driver.getTitle());

		}
	
}