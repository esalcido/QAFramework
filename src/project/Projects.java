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

		
		//HashMap<String,String> hmap = new HashMap<String,String>();
		boolean pinEx=false,pinNav=false,pwIncorrect=false;
		
		String environment = Environments.QA3.url();
		//String environment = Environments.QA.url();
		
		String currentUser = "qagst";
		//======================= Handle the files =================================
		
		//handles file with usernames and passwords
		FileHandler fh = new FileHandler("resources/users1.csv","resources/output/useroutput.csv");
		fh.createFile();
		
		//handles output file from agg creation
		FileHandler agFh = new FileHandler("resources/users1.csv","resources/output/aggResults.csv");
		//runReportsFromFile(fh, environment);
		
		//reads in users and inserts them to the DB
		//fh.readFileAddUsersToDB();
		
		//Run reports of users from the database
			//runReportsFromDB(environment);
			//User.runReportsFromDB(environment,driver);

		//=============================================================================

		//create aggs  
		CreateAggs( currentUser, driver,environment,agFh);
		
		//delete aggs
		//deleteAggs( currentUser, driver,environment);
		
		//===============================================================================================
			
		
	  	System.out.println("End of script.");
		
	  	//fh.close();
	  	
		
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
			//usr.setNumOfUsers(users.size());
			
			
  			fh.writeToFile("User: "+usr.getUid()+",");
  			if(usr.signIn()){
  				//write password result to file
  				fh.writeToFile("True,");
  				
  				//check what project users belong to.  Explorer or navigator
  				//checkProjects(pinEx,pinNav,fh);
  			
  				usr.waitSec(5);
  				//run a report
  				usr.runReport(marker);
  				
	  			//usr.signOut();
	  			
	  			marker += usr.getNumOfUsers();
  			}
  			else{
  				fh.writeToFile("False,");
  			}
  			fh.writeToFile("\r\n");
			
		}
	  			
		fh.close();
	}

	public static void CreateAggs( String userName, WebDriver driver, String environment, FileHandler fh){


		//grab users from DB
					Database db = new Database("localhost/qa_platform","root","qazwsx");
					db.connect();
					
					String aggUserName = "";
					User user = new User(userName,"4Quality@WLV",environment,driver);
					user.signIn();
					
					switch(userName){
					case "qausa":
						aggUserName = "usa";
						break;
					case "qacan":
						aggUserName = "can";
						break;
					case "qagst":
						aggUserName = "gst";
						break;
					}
					ArrayList<Aggregate> aggs = db.getAggregates(aggUserName);
					
					db.disconnect();
					
					
					
					//get to home page
					user.clickON("//a[text()='PIN Explorer']",1);
					
					HashMap initial_xpath = new HashMap();

					initial_xpath.put("input_box","//input[@id='id_mstr44_txt']");
					initial_xpath.put("title_text_xpath","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span");
					initial_xpath.put("dropdown_arrow","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
					initial_xpath.put("attribute_available","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div[1]/div[1]/span");
					initial_xpath.put("add_attribute_value","//*[@id='id_mstr79']/img");
					initial_xpath.put("status_radio_btn","//*[@id='id_mstr93ListContainer']/div[2]");
					initial_xpath.put("run_btn","//*[@id='id_mstr98']");
					initial_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					HashMap hasAggText_xpath = new HashMap();
					
					hasAggText_xpath.put("input_box","//input[@id='id_mstr44_txt']");
					hasAggText_xpath.put("title_text_xpath","//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span");
					hasAggText_xpath.put("dropdown_arrow","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/img");
					hasAggText_xpath.put("attribute_available","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div[2]/div[1]/span");
					hasAggText_xpath.put("add_attribute_value","//*[@id='id_mstr79']/img");
					hasAggText_xpath.put("status_radio_btn","//*[@id='id_mstr93ListContainer']/div[2]");
					hasAggText_xpath.put("run_btn","//*[@id='id_mstr98']");
					hasAggText_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					//set up hashmap for toyota xpath assignments
					HashMap dev90_toyota_xpath = new HashMap();

					dev90_toyota_xpath.put("input_box","//input[@id='id_mstr45_txt']");
					dev90_toyota_xpath.put("dropdown_arrow","//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img");
					dev90_toyota_xpath.put("attribute_available",".//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[4]/div[1]/span");
					dev90_toyota_xpath.put("add_attribute_value","//*[@id='id_mstr80']/img");
					dev90_toyota_xpath.put("status_radio_btn",".//*[@id='id_mstr94ListContainer']/div[2]");
					dev90_toyota_xpath.put("run_btn","//*[@id='id_mstr99']");
					dev90_toyota_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
				
					HashMap dev90_fordmarkets_xpath = new HashMap();
					
					dev90_fordmarkets_xpath.put("input_box","//input[@id='id_mstr45_txt']");
					dev90_fordmarkets_xpath.put("dropdown_arrow","//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img");
					dev90_fordmarkets_xpath.put("attribute_available",".//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]");
					dev90_fordmarkets_xpath.put("add_attribute_value","//*[@id='id_mstr80']/img");
					dev90_fordmarkets_xpath.put("status_radio_btn",".//*[@id='id_mstr94ListContainer']/div[2]");
					dev90_fordmarkets_xpath.put("run_btn","//*[@id='id_mstr99']");
					dev90_fordmarkets_xpath.put("top_breadcrumb",".//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					HashMap new_aggs_xpath = new HashMap();
					
					new_aggs_xpath.put("input_box","//*[@id='id_mstr44_txt']");
					new_aggs_xpath.put("dropdown_arrow","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/img");
					new_aggs_xpath.put("attribute_available","//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span");
					new_aggs_xpath.put("add_attribute_value","//*[@id='id_mstr79']/img");
					new_aggs_xpath.put("status_radio_btn","//*[@id='id_mstr93ListContainer']/div[2]");
					new_aggs_xpath.put("run_btn","//*[@id='id_mstr98']");
					new_aggs_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					HashMap used_aggs_xpath = new HashMap();
					
					used_aggs_xpath.put("input_box","//*[@id='id_mstr44_txt']");
					used_aggs_xpath.put("dropdown_arrow","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/img");
					used_aggs_xpath.put("attribute_available","//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span");
					used_aggs_xpath.put("add_attribute_value","//*[@id='id_mstr79']/img");
					used_aggs_xpath.put("status_radio_btn","//*[@id='id_mstr93ListContainer']/div[2]");
					used_aggs_xpath.put("run_btn","//*[@id='id_mstr98']");
					used_aggs_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					System.out.println("aggs size: "+ aggs.size() );
					//for(Aggregate ag:aggs){
					for(int i = 0 ;i<aggs.size(); i++){
						
						user.waitSec(5);
						Aggregate agg = aggs.get(i);
						
						System.out.println("\nAggregate: "+ agg.toString());
					
						user.waitSec(10);
						

						//click on aggregates in the left menu 
						user.clickON("//a[contains(text(),'Aggregates')]");
						
						// Store the current window handle
						String parent_Window = driver.getWindowHandle();

						handleWindows(driver,parent_Window);
						
						//switching to child window
						
						
//						user.clickON("//a[contains(text(),'Delete Aggregates')]");
						user.clickON("//a[contains(text(),'Create Aggregates')]");


						//if new or used
						if(agg.getAggType().equals("new")){
							System.out.println("this agg is new");
							user.clickON("//a[contains(text(),'New Vehicle Aggregates')]");
						}
						if(agg.getAggType().equals("used")){
							System.out.println("this agg is used");
							user.clickON("//a[contains(text(),'Used Vehicle Aggregates')]");
						}
						
						//clck on aggregate
						driver.findElement(By.xpath("//a[text()='" + agg.getName() + "']")).click();
						
						//enter aggregate name
						if( agg.getName().equals("Toyota Region Aggregates")){
							user.waitSec(5);
							doAggregateActionsToyotaFord(dev90_toyota_xpath, agg, user, parent_Window,fh);
							
						}
						else if( agg.getName().equals("Ford Market & Region Aggregates") ){
							
							doAggregateActionsToyotaFord(dev90_fordmarkets_xpath, agg, user, parent_Window,fh);
						}
						else if(agg.getAggType().equals("new") ){
							System.out.println("creating new");
							doAggregateActions(new_aggs_xpath, agg , user, parent_Window,fh);
						}
						else if(agg.getAggType().equals("used") ){
							System.out.println("creating used");
							doAggregateActions(used_aggs_xpath, agg , user, parent_Window,fh);
						}
						else{

							String titleText = driver.findElement(By.xpath("//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span")).getText();
							
							boolean hasAggregateText = titleText.contains("Aggregate");
							System.out.println("title text has agg text in it? "+ hasAggregateText);
							
							if(!hasAggregateText){
				
								System.out.println("titleText: " + titleText);
								
								doAggregateActions(initial_xpath, agg , user, parent_Window,fh);

							}
							else{
			
								doAggregateActions(hasAggText_xpath, agg , user, parent_Window,fh);
									
							}
						}
						
					}
					user.signOut();
			
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
	
	private static void doAggregateActions(HashMap hm, Aggregate agg, User user, String parent_Window,FileHandler fh){
		
		driver.findElement(By.xpath(hm.get("dropdown_arrow").toString() ) ).click();
		
		//input the agg name
		driver.findElement(By.xpath(hm.get("input_box").toString() ) ).sendKeys(agg.getName() + " "+ agg.getUserType());
		
		//choose the value for the attribute
		
		driver.findElement(By.xpath( hm.get("attribute_available").toString() )).click();
		
		//print the attribute for testing purposes
		String element = driver.findElement(By.xpath(hm.get("attribute_available").toString() )).getText();
		System.out.println("\n" + element);
//		try {
//			fh.writeToFile(agg.getName()+ ","+element);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
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
		}catch(Exception e){
			System.out.println(agg.getName() +" Timed Out.");
		}
			
		//click on continue box90
		driver.findElement(By.xpath(hm.get("top_breadcrumb").toString()) ).click();
	
		//switch back to parent window
		driver.switchTo().window(parent_Window);
		System.out.println("Back to parent window = " + driver.getTitle());
		
	}
	private static void doAggregateActionsToyotaFord(HashMap hm, Aggregate agg, User user, String parent_Window,FileHandler fh){
		
		//if dropdown is not collapsed
		if( (driver.findElement(By.xpath(hm.get("dropdown_arrow").toString() )).getClass()).equals("mstrBGIcon_treeNodeClosedOrphan mstrTreeViewNodeConnector")  ){
			driver.findElement(By.xpath(hm.get("dropdown_arrow").toString() ) ).click();
		}

	
	//input the agg name
	driver.findElement(By.xpath(hm.get("input_box").toString() ) ).sendKeys(agg.getName() + " "+ agg.getUserType());
	

	//choose the value for the attribute
	
	driver.findElement(By.xpath( hm.get("attribute_available").toString() )).click();
	
	//print the attribute for testing purposes
	String element = driver.findElement(By.xpath(hm.get("attribute_available").toString() )).getText();
	System.out.println("\n" + element);
//	try {
//		fh.writeToFile(agg.getName()+ ","+element);
//	} catch (IOException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
	
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
	}catch(Exception e){
		System.out.println(agg.getName() +" Timed Out.");
	}
		
	//click on continue box90
	driver.findElement(By.xpath(hm.get("top_breadcrumb").toString()) ).click();

	//switch back to parent window
	driver.switchTo().window(parent_Window);
	System.out.println("Back to parent window = " + driver.getTitle());
	
}
	
	public static void deleteAggs( String un, WebDriver driver, String environment,FileHandler fh){


		//grab users from DB
					Database db = new Database("localhost/qa_platform","root","qazwsx");
					db.connect();
					
					String userName = un;
					String aggUserName = "";
					User user = new User(userName,"4Quality@WLV",environment,driver);
					user.signIn();
					
					switch(userName){
					case "qausa":
						aggUserName = "usa";
						break;
					case "qacan":
						aggUserName = "can";
						break;
					case "qagst":
						aggUserName = "gst";
						break;
					}
					ArrayList<Aggregate> aggs = db.getAggregates(aggUserName);
					
					db.disconnect();
					
					
					
					//get to home page
					user.clickON("//a[text()='PIN Explorer']",1);
					
					HashMap initial_xpath = new HashMap();

					initial_xpath.put("input_box","//input[@id='id_mstr44_txt']");
					initial_xpath.put("title_text_xpath","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span");
					initial_xpath.put("dropdown_arrow","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
					initial_xpath.put("attribute_available","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div[1]/div[1]/span");
					initial_xpath.put("add_attribute_value","//*[@id='id_mstr79']/img");
					initial_xpath.put("status_radio_btn","//*[@id='id_mstr93ListContainer']/div[2]");
					initial_xpath.put("run_btn","//*[@id='id_mstr98']");
					initial_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					HashMap hasAggText_xpath = new HashMap();
					
					hasAggText_xpath.put("input_box","//input[@id='id_mstr44_txt']");
					hasAggText_xpath.put("title_text_xpath","//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span");
					hasAggText_xpath.put("dropdown_arrow","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/img");
					hasAggText_xpath.put("attribute_available","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div[2]/div[1]/span");
					hasAggText_xpath.put("add_attribute_value","//*[@id='id_mstr79']/img");
					hasAggText_xpath.put("status_radio_btn","//*[@id='id_mstr93ListContainer']/div[2]");
					hasAggText_xpath.put("run_btn","//*[@id='id_mstr98']");
					hasAggText_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					//set up hashmap for toyota xpath assignments
					HashMap dev90_toyota_xpath = new HashMap();

					dev90_toyota_xpath.put("input_box","//input[@id='id_mstr45_txt']");
					dev90_toyota_xpath.put("dropdown_arrow","//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img");
					dev90_toyota_xpath.put("attribute_available",".//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[4]/div[1]/span");
					dev90_toyota_xpath.put("add_attribute_value","//*[@id='id_mstr80']/img");
					dev90_toyota_xpath.put("status_radio_btn",".//*[@id='id_mstr94ListContainer']/div[2]");
					dev90_toyota_xpath.put("run_btn","//*[@id='id_mstr99']");
					dev90_toyota_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
				
					HashMap dev90_fordmarkets_xpath = new HashMap();
					
					dev90_fordmarkets_xpath.put("input_box","//input[@id='id_mstr45_txt']");
					dev90_fordmarkets_xpath.put("dropdown_arrow","//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img");
					dev90_fordmarkets_xpath.put("attribute_available",".//*[@id='id_mstr79']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]");
					dev90_fordmarkets_xpath.put("add_attribute_value","//*[@id='id_mstr80']/img");
					dev90_fordmarkets_xpath.put("status_radio_btn",".//*[@id='id_mstr94ListContainer']/div[2]");
					dev90_fordmarkets_xpath.put("run_btn","//*[@id='id_mstr99']");
					dev90_fordmarkets_xpath.put("top_breadcrumb",".//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					HashMap new_aggs_xpath = new HashMap();
					
					new_aggs_xpath.put("input_box","//*[@id='id_mstr44_txt']");
					new_aggs_xpath.put("dropdown_arrow","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/img");
					new_aggs_xpath.put("attribute_available","//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span");
					new_aggs_xpath.put("add_attribute_value","//*[@id='id_mstr79']/img");
					new_aggs_xpath.put("status_radio_btn","//*[@id='id_mstr93ListContainer']/div[2]");
					new_aggs_xpath.put("run_btn","//*[@id='id_mstr98']");
					new_aggs_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					HashMap used_aggs_xpath = new HashMap();
					
					used_aggs_xpath.put("input_box","//*[@id='id_mstr44_txt']");
					used_aggs_xpath.put("dropdown_arrow","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/img");
					used_aggs_xpath.put("attribute_available","//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span");
					used_aggs_xpath.put("add_attribute_value","//*[@id='id_mstr79']/img");
					used_aggs_xpath.put("status_radio_btn","//*[@id='id_mstr93ListContainer']/div[2]");
					used_aggs_xpath.put("run_btn","//*[@id='id_mstr98']");
					used_aggs_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");
					
					HashMap delete_aggs_xpath = new HashMap();
					
					delete_aggs_xpath.put("input_box","//*[@id='id_mstr44_txt']");
					delete_aggs_xpath.put("dropdown_arrow","//*[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/img");
					delete_aggs_xpath.put("attribute_available","//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span");
					delete_aggs_xpath.put("add_attribute_value",".//*[@id='id_mstr59']/img");
					delete_aggs_xpath.put("status_radio_btn","//*[@id='id_mstr93ListContainer']/div[2]");
					delete_aggs_xpath.put("run_btn","//*[@id='id_mstr64']");
					delete_aggs_xpath.put("top_breadcrumb","//*[@id='mstr61']/div/div[2]/span[7]/a");		
					
					System.out.println("aggs size: "+ aggs.size() );
					//for(Aggregate ag:aggs){
					for(int i = 3 ;i<4; i++){
						
						user.waitSec(5);
						Aggregate agg = aggs.get(i);
						
						System.out.println("\nAggregate: "+ agg.toString());
						

						//click on aggregates in the left menu 
						user.clickON("//a[contains(text(),'Aggregates')]");
						
						// Store the current window handle
						String parent_Window = driver.getWindowHandle();

						handleWindows(driver,parent_Window);
						
						
						user.clickON("//a[contains(text(),'Delete Aggregates')]");
						
						//if new or used
						if(agg.getAggType().equals("new")){
							System.out.println("this agg is new");
							user.clickON("//a[contains(text(),'Delete New Vehicle Aggregates')]");
						}
						if(agg.getAggType().equals("used")){
							System.out.println("this agg is used");
							user.clickON("//a[contains(text(),'Delete Used Vehicle Aggregates')]");
						}
						
						//strip off the 's' from the aggregate name
						String truncatedAggName = agg.getName().substring(0,agg.getName().length() -1 );
						
						//click on aggregate
						driver.findElement(By.xpath("//a[text()='Delete " + truncatedAggName + "']")).click();
						
						//enter aggregate name
						if( agg.getName().equals("Toyota Region Aggregates")){
							user.waitSec(5);
							doAggregateActionsToyotaFord(dev90_toyota_xpath, agg, user, parent_Window,fh);
							
						}
						else if( agg.getName().equals("Ford Market & Region Aggregates") ){
							
							doAggregateActionsToyotaFord(dev90_fordmarkets_xpath, agg, user, parent_Window,fh);
						}
						else if(agg.getAggType().equals("new") ){
							System.out.println("creating new");
							doAggregateActions(new_aggs_xpath, agg , user, parent_Window,fh);
						}
						else if(agg.getAggType().equals("used") ){
							System.out.println("creating used");
							doAggregateActions(used_aggs_xpath, agg , user, parent_Window,fh);
						}
						else{
							System.out.println("its here!");
							//remove 
							//driver.findElement(By.xpath("//input[@id='id_mstr44_txt']")).sendKeys(agg.getName() + " "+ agg.getUserType() );
							
//							String aggregateText = driver.findElement(By.xpath("//*[@id='id_mstr58']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[9]/div[1]/span")).getText();
//							System.out.println("agg text is: " + aggregateText);
							
							String aggText="",aggxp="";
							int j = 1;
							//for(int j =1;j<=10;j++){
							do{	
								//find the aggregate name  that matches the current agg name and delete
								aggText = driver.findElement(By.xpath("//*[@id='id_mstr58']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div["+j+"]/div[1]/span")).getText();
								System.out.println(aggText ) ;
								System.out.println(agg.getName() + " "+ agg.getUserType());
								System.out.println(aggText.equals(agg.getName() ) ) ;
								
								String currAggText = agg.getName() + " "+ agg.getUserType()  ;
								if( !aggText.equals(currAggText)){
									user.waitSec(5);
									System.out.println("i am in if statement");
									//aggxp="//*[@id='id_mstr58']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[";
									//driver.findElement(By.xpath( "//*[@id='id_mstr58']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div["+j+"]/div[1]/span" ) ).click();
									driver.findElement(By.xpath( "//*[@id='id_mstr58']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div["+j+"]/div[1]/span" ) ).click();
									
									//*[@id='id_mstr59']/img
									//add the value to the selected box
									user.clickON(delete_aggs_xpath.get("add_attribute_value").toString() );
									
									//wait for the page to respond
									user.waitMin( 15);
									
									try
									{
										//click run document
										driver.findElement(By.xpath(delete_aggs_xpath.get("run_btn").toString()) ).click();
									}catch(Exception e){
										System.out.println(agg.getName() +" Timed Out.");
									}
										
									//click on continue box90
									driver.findElement(By.xpath(delete_aggs_xpath.get("top_breadcrumb").toString()) ).click();
								
									//switch back to parent window
									driver.switchTo().window(parent_Window);
									System.out.println("Back to parent window = " + driver.getTitle());
									j++;
									break;
								
								}else{
									break;
								
								}
								
							}while(aggText != null);
							
							//click on continue box90
							driver.findElement(By.xpath("//*[@id='pathBean_RWPromptsPathStyle']/div[2]/div[1]/div[2]/span[7]/a") ).click();
						
							System.out.println(" i am out of loop");
							
							
							//driver.findElement(By.xpath(aggxp ) ).click();
//							String titleText = driver.findElement(By.xpath("//span[@id='id_mstr78']/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span")).getText();
//							
//							boolean hasAggregateText = titleText.contains("Aggregate");
//							System.out.println("title text has agg text in it? "+ hasAggregateText);
//							
//							if(!hasAggregateText){
//				
//								System.out.println("titleText: " + titleText);
//								
//								doAggregateActions(initial_xpath, agg , user, parent_Window);
//
//							}
//							else{
//			
//								doAggregateActions(hasAggText_xpath, agg , user, parent_Window);
//									
//							}
							
						}
						
					}
					user.signOut();
			
			}



}