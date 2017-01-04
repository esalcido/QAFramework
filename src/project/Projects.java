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
			
			//set up hashmap for toyota xpath assignments
			HashMap dev90_toyota_xpath = new HashMap();

			dev90_toyota_xpath.put("input_box","//input[@id='id_mstr45_txt']");
			dev90_toyota_xpath.put("attribute_available",".//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[4]/div[1]/span");
			dev90_toyota_xpath.put("add_attribute_value","//*[@id='id_mstr80']/img");
			dev90_toyota_xpath.put("status_radio_btn",".//*[@id='id_mstr94ListContainer']/div[2]");
			dev90_toyota_xpath.put("run_btn","//*[@id='id_mstr99']");
			dev90_toyota_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
			
		
			//for(Aggregate ag:aggs){
			for(int i = 7 ;i<aggs.size(); i ++){
				user.waitSec(5);
				
				System.out.println("Aggregate: "+ aggs.get(i).toString());
			
				user.waitSec(10);
				
				//click on aggregates in the left menu 
				user.clickON("//a[contains(text(),'Aggregates')]");
				
				// Store the current window handle
				String parent_Window = driver.getWindowHandle();

				handleWindows(driver,parent_Window);
				
				//switching to child window
				
				user.clickON("//a[contains(text(),'Create Aggregates')]");

				user.waitSec( 5);

				driver.findElement(By.xpath("//a[text()='" + aggs.get(i).getName() + "']")).click();
				
				//enter aggregate name
				if( aggs.get(i).getName().equals("Toyota Region Aggregates")){
					
//					//expand the list of attribute options
//					String input_box = "//input[@id='id_mstr45_txt']";
//					driver.findElement(By.xpath(input_box)).sendKeys(aggs.get(i).getName() + " "+ aggs.get(i).getUserType() );
//	
//					//choose the value for the attribute
//					String attribute_available = ".//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[4]/div[1]/span";
//					driver.findElement(By.xpath(attribute_available)).click();
//					
//					//print the attribute for testing purposes
//					String element = driver.findElement(By.xpath(attribute_available)).getText();
//					System.out.println("\n" + element);
//				
//					//add the value to the selected box
//					String add_attribute_value = "//*[@id='id_mstr80']/img";
//					user.clickON(add_attribute_value);
//					
//					//select status
//					String status_radio_btn = ".//*[@id='id_mstr94ListContainer']/div[2]";
//					driver.findElement(By.xpath(status_radio_btn)).click();
//					
//					//wait for the page to respond
//					user.waitMin( 15);
//					
//					try
//					{
//						//click run document
//						String run_btn = "//*[@id='id_mstr99']";
//						driver.findElement(By.xpath(run_btn)).click();
//					}
//					catch(Exception e)
//					{
//						System.out.println(aggs.get(i).getName() +" Timed Out.");
//					}
//						
//					//click on continue box90
//					String top_breadcrumb = "//*[@id='mstr61']/div/div[2]/span[7]/a";
//					driver.findElement(By.xpath(top_breadcrumb)).click();
//				
//					//switch back to parent window
//					driver.switchTo().window(parent_Window);
//					System.out.println("Back to parent window = " + driver.getTitle());
					
					createAggregate(dev90_toyota_xpath, aggs.get(i) , user, parent_Window);
				
				}
				else{
					
					driver.findElement(By.xpath("//input[@id='id_mstr44_txt']")).sendKeys(aggs.get(i).getName() + " "+ aggs.get(i).getUserType() );

					String titleText = driver.findElement(By.xpath("//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span")).getText();
					
					boolean hasAggregateText = titleText.contains("Aggregate");
					System.out.println("titiletext has agg text in it? "+ hasAggregateText);
					
					if(!hasAggregateText){
		
						System.out.println("titleText: " + titleText);
						
						//expand the list of attribute options
						driver.findElement(By.xpath("//span[@id='id_mstr78']/div[2]/div/div/div[2]"+ "/div[3]/div/div[2]/div[3]/div[2]/div[1]/img")).click();
						
		
						//choose the value for the attribute
						driver.findElement(By.xpath("//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div/div/div[1]/div[1]")).click();
						
						//print the attribute for testing purposes
						String element = driver.findElement(By.xpath("//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div/div/div[1]/div[1]")).getText();
						System.out.println("\n" + element);
		
						
						//add the value to the selected box
						//driver.findElement(By.xpath("//*[@id='id_mstr79']/img")).click();
		
						//select status
						driver.findElement(By.xpath("//*[@id='id_mstr93ListContainer']/div[2]")).click();
					
					}
				
					
					else{
							//==================================================================================
							// expand first attribute
							
							//box 90
							driver.findElement(By.xpath("//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/img")).click();
							
							// choose the value
	//						driver.findElement(By.xpath("//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div[5]")).click();
							driver.findElement(By.xpath(".//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div[2]/div[1]/span")).click();
							
							String element = driver.findElement(By.xpath(".//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div[2]/div[1]/span")).getText();
							System.out.println("\n" + element);
					
							user.waitSec(15);
							
							//add the value to the selected box
							//driver.findElement(By.xpath("//*[@id='id_mstr79']/img")).click();
							
							//select status
							driver.findElement(By.xpath("//*[@id='id_mstr93ListContainer']/div[2]")).click();
					}
					//driver.findElement(By.xpath("//*[@id='id_mstr79']/img")).click();
					user.clickON("//*[@id='id_mstr79']/img");
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
	
	private static void createAggregate(HashMap hm, Aggregate agg, User user, String parent_Window){

		// 
		//agg.getName() + " "+ agg.getUserType()
		driver.findElement(By.xpath(hm.get("input_box").toString() ) ).sendKeys(agg.getName() + " "+ agg.getUserType());;

		//choose the value for the attribute
		
		driver.findElement(By.xpath( hm.get("attribute_available").toString() )).click();
		
		//print the attribute for testing purposes
		String element = driver.findElement(By.xpath(hm.get("attribute_available").toString() )).getText();
		System.out.println("\n" + element);

		//add the value to the selected box
		user.clickON(hm.get("add_attribute_value").toString() );
		
		//select status
		driver.findElement(By.xpath(hm.get("status_radio_btn").toString() )).click();
		
		//wait for the page to respond
		user.waitMin( 15);
		
		try
		{
			//click run document
			driver.findElement(By.xpath(hm.get("run_btn").toString()) ).click();
		}
		catch(Exception e)
		{
			System.out.println(agg.getName() +" Timed Out.");
		}
			
		//click on continue box90
		driver.findElement(By.xpath(hm.get("top_breadcrumb").toString()) ).click();
	
		//switch back to parent window
		driver.switchTo().window(parent_Window);
		System.out.println("Back to parent window = " + driver.getTitle());

	}
	
}