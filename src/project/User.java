package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import database.Database;
import files.FileHandler;

public class User {

	private  String uid;
	private  String password ;
	private  String environment;
	private   WebDriver driver;
	private int numOfUsers;
	private ArrayList<Report> reports;
	private String client;
	
	
	//selenium objects
	public static final String logoutButton = "//*[@id='menuTabs']/div[2]/div[1]/span[14]/a";
	//public static final String errorMessage = ".//*[@id='mstrWeb_error']/div/div[2]";
	public static final String errorMessage = "//*[@id='mstrWeb_error']/div/div[1]";//from dev3
	public static final String runReportButton = "//*[@value='Run Report']";
//	public static final String topNavHomeLink = "//*[@name='tbHome']";
	public static final String topNavHomeLink = "//*[@name='tbHome']";//from dev3
	//public static final String topNavHomeLink = "//*[@id='projects_ProjectsStyle']/table/tbody/tr/td[1]/div/table/tbody/tr/td[2]/a";
	public static final String logoutButton_qa = "//*[@id='menuTabs']/div[3]/div/span[11]/a";
	
	public User(String id, String pw, String env,WebDriver drv){
		uid = id;
		password = pw;
		environment = env;
		driver= drv;
	}
	
	public User(String id, String pw,String c){
		uid = id;
		password = pw;
		client = c;
		
	}
	public User(String id, String pw){
		uid = id;
		password = pw;
	}
	
	public ArrayList<Report> getReports() {
		return reports;
	}

	public void setReports(ArrayList<Report> reports) {
		this.reports = reports;
	}

	
	
	public  String getUid(){
		return uid;
	}
	public  String getPw(){
		return password;
	}
	public WebDriver getDriver(){
		return driver;
	}
	
	public int getNumOfUsers() {
		return numOfUsers;
	}

	public void setNumOfUsers(int numOfUsers) {
		this.numOfUsers = numOfUsers;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void setUid(String id){
		uid = id;
	}
	
	public void setPw(String pw){
		password = pw;
	}
	
	
	public boolean signIn(){

		try{	
			
		driver.get(environment);
		//Find User name field and enter qausa
		driver.findElement(By.id("Uid")).sendKeys(uid);
				
		//Find Password field and enter qausa
		driver.findElement(By.id("Pwd")).sendKeys(password);
				
		//Find Login and click on it;
		driver.findElement(By.id("3054")).click();
		
//		System.out.println("Signed in: "+ getUid());
//		return true;

		//isPasswordValid();
//		System.out.println(isPasswordValid());
//		if(isPasswordValid()){
//			System.out.println("Signed in: " + getUid());
//			return true;
//		}
//		else{
//			System.out.println("Password invalid\n");
//			return false;
//		}
//		
//		}catch(Exception e){
//			e.printStackTrace();
//			return true;
//		}
		
		
		//System.out.println("I am here at sign in");
		//try{
//			
//			System.out.println("I am here after isdisplayed");
//			//System.out.println("i am in try");
		
		waitSec(2);
		boolean isDisplayed  = driver.findElement(By.xpath(errorMessage) ).isDisplayed();
		
		if( ! isDisplayed){
		//	if(driver.findElement(By.xpath("//a[text()='PIN Explorer']") ).isDisplayed()){
				
				System.out.println("Signed in User: "+ getUid() );
				return true;
			}
			else{
//				System.out.println("is displayed: "+isDisplayed);
				System.out.println("unable to sign in "+ getUid());
				return false;
				
			}
		}catch(Exception e){
			
			System.out.println("Signed in User: "+ getUid() +". Password valid.");
			//e.printStackTrace();
			return true;
		}
		
		
		
	}
public  boolean isPasswordValid(){
		
		System.out.println("I am here at sign in");
		
		
		boolean errorIsDisplayed = driver.findElement(By.xpath(errorMessage) ).isDisplayed();
		
		try{
//			System.out.println("errorMessg:  "+errorIsDisplayed);
//			System.out.println("I am here after isdisplayed");
//			//System.out.println("i am in try");
		//if(driver.findElement(By.xpath(errorMessage) ).isDisplayed()){
			if(driver.findElement(By.xpath("//a[text()='PIN Explorer']") ).isDisplayed()){
				
				return false;
			}
			else{
				
				return true;
				
			}
		}catch(Exception e){
			System.out.println("i am in catch");
			System.out.println("Password valid.  did not find error message.");
			//e.printStackTrace();
			return false;
		}
	}
	
	public void signOut(){
		
		//Find Logout and click on it;
		//driver.findElement(By.id("3054")).click();
//		driver.findElement(By.xpath("//a[contains(text(),'click here')]") ).click();
//		driver.findElement(By.xpath("//*[@id='menuTabs']/div[2]/div[1]/span[14]/a") ).click();
		waitSec(5);
		//driver.findElement(By.xpath("//td[@id='td_mstrWeb_dockLeft']/div/div[@id='leftToolbar']/div/div[3]/div/span[11]/a[text()='Logout']") ).click();
		driver.findElement(By.linkText("Logout")).click();
		/*try{
			driver.findElement(By.linkText("Logout")).click();
			//driver.findElement(By.xpath(logoutButton) ).click();
		}catch(Exception e){
			driver.findElement(by.l)
			//driver.findElement(By.xpath(logoutButton_qa) ).click();	
		}*/
		
		//driver.close();
		System.out.println("Signed out "+ getUid() +"\n");
	}
	
	public void clickON(String xpath,int waitSecs)
	{
		
		//Find the element for the specified path and save it in "element".
		WebElement element = driver.findElement(By.xpath(xpath));
		
		//Instantiate an Action Object to perform an action an "element".
		Actions actions = new Actions(driver);
		
		//click on "element" which holds "PIN Explorer Project" element 
		actions.moveToElement(element).doubleClick().perform();
		
		//wait 
		driver.manage().timeouts().implicitlyWait(waitSecs, TimeUnit.SECONDS);
	}
	
	public void clickON(String xpath)
	{
		
		//Find the element for the specified path and save it in "element".
		WebElement element = driver.findElement(By.xpath(xpath));
		//System.out.println("web element: "+element);
		//Instantiate an Action Object to perform an action an "element".
		//Actions actions = new Actions(driver);
		
		//click on "element" which holds "PIN Explorer Project" element 
		//actions.moveToElement(element).doubleClick().perform();
		element.click();
	}
	
	public void waitSec( int x)
	{
		driver.manage().timeouts().implicitlyWait(x, TimeUnit.SECONDS);
	}
	public void waitMin( int x)
	{
		driver.manage().timeouts().implicitlyWait(x, TimeUnit.MINUTES);
	}
	private  void explicitWait(String ElementxPath) {
		WebDriverWait wait = new WebDriverWait(driver, 4);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementxPath)));	
	}
	
	private  void explicitWait(String ElementxPath,int t) {
		WebDriverWait wait = new WebDriverWait(driver, t);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementxPath)));	
	}
	
	public String toString(){
		
		String str = "User id is "+uid+ " Password is "+password;
		
		
		return str;
	}
	
	public void printReports(){
		
		for(Report rpt:reports){
			
			System.out.print(rpt.fileName + "path: "+ rpt.getFilePathStr());
			
			
		}
		
	}
	
	public int runReport(int start) throws IOException{
		//TODO fool proof if file is not 
		
		 boolean found=false;
		
		 waitSec(5);
		 
		//get report from file
		FileHandler fh2 = new FileHandler("resources/reports.csv","resources/output/reportsoutput.csv");
		fh2.createFile();
		
//		String projectString = "PIN Explorer";
		String projectString = "PIN Navigator";
		
		try{
		//get to home page
		clickON("//a[text()='"+ projectString +"']",1);
		}catch(Exception e){
			System.out.println("could not find PIN Explorer button");
		}
		
		System.out.println("Fetching reports from Text file "+ fh2.getFileName());
		
			 ArrayList<Report> arrList = fh2.readFileArr();
			System.out.println("Got 'em.\n");
		
			int maxAmnt = start + arrList.size();
			
		//run through all reports in the text file
		for( int i =start;i < maxAmnt;i++){
			try{
				//get to the report
				//get file path and click through to the project
				System.out.println("Report: " + arrList.get(i).toString());
				
				//click through to project
				String [] filePath = arrList.get(i).getFilePath();
				
				for(String p : filePath){
					System.out.println("this is file path "+ p);
				}
	
				
				
				for(String pth: filePath){
					waitSec(10);
					//try{
						try{
							clickON("//a[text()='"+pth+"']",2);
							System.out.println("clicked on "+pth);
		
						}catch(Exception e){
							//clickON(".//*[@id='main']/div[1]/li/span/a",2);
							System.out.println("clicked on "+pth);
						}finally{
							//clickON("//*[@class='mstrMenuContent']/span/a[text()='"+pth+"']",1);
							//clickON("//*[@id='FolderIcons']/tbody/tr[1]/td[2]/div/table/tbody/tr/td[2]/div/span/a[text()='"+pth+"']");
							System.out.println("ex. clicked on "+pth);
							waitSec(5);
						}
						System.out.println("i am here ");
						waitSec(5);
				}
//				  found = driver.findElements(By.xpath("//a[text()='"+arrList.get(i).getFileName()+"']")).size() >0;
//				System.out.println("file found:" +found );
				
				//click on project
				
				//if(found){
					clickON("//a[text()='"+arrList.get(i).getFileName()+"']",1);
				
					//click on run report
					clickON(runReportButton,10);
					
					//look for second report button
					try{
						//click on run report
						clickON(runReportButton,10);
					}catch(Exception e){
						System.out.println("Did not find second run button");
					}
					
					//instantiate a stop watch
					StopWatch stopwatch = new StopWatch();
						
					//start the timer 
					stopwatch.start();
					//wait 15 minutes
					driver.manage().timeouts().implicitlyWait(15, TimeUnit.MINUTES);
					
					//look for toolbar
					//class="mstrTabbedMenuVBoxItem"
//					if( driver.findElement(By.xpath("//*[@class='mstrTabbedMenuVBoxItem']") ).isDisplayed()){
					if( driver.findElement(By.xpath("//*[@id='tbSave']") ).isDisplayed()){
					
						System.out.println("Ran Report: "+ arrList.get(i).getFileName());
						//stop the watch
						stopwatch.stop();
						
					}
					else{
						System.out.println("Did not run report");
					}
					
					
					
					System.out.println("time: "+ stopwatch);
					fh2.writeToFile(arrList.get(i).getFileName() + ","+ stopwatch+"\r\n");
					
					//click back home
					//clickON(topNavHomeLink,5000);
					clickON("//*[@id='pathBean_ReportPathStyle']/div[2]/div[1]/div[2]/span[1]/a[@class='mstrLink']",5000);
					System.out.println("clicked home");
					waitSec(5);
//				}else{
//					//click back home
//					clickON(topNavHomeLink,5000);
//					System.out.println("clicked home");
//				}
			
			}catch(Exception e){
				
				System.out.println("Something wrong happened. \n");
				
				e.printStackTrace();
				System.out.println("Trying next user.\n");
				
				//break;
			}
			
		}
		return maxAmnt;
	
	}
	
	//get reports pertaining to this user
	public void runReportDB() throws IOException{
		//TODO fool proof if file is not 
		
		System.out.println("entered run report method");
		waitSec(5);
		
		//get to home page
		clickON("//a[text()='PIN Explorer']",1);
//		clickON("//a[text()='PIN Navigator']",1);
		
		//run through all reports in the text file
		//for( int i =start;i< maxAmnt;i++){
		for( Report rpt: reports){
			try{
				
//				//get to the report
//				//get file path and click through to the project
				
				//click through to project
				String [] filePath = rpt.filePathStr.split("/");
				
				for(String pth: filePath){
					System.out.println("clicked on "+pth);
					//try{
						try{
							clickON("//a[text()='"+pth+"']",1);
							System.out.println("clicked on "+pth);
		
						}catch(Exception e){
							clickON("//*[@id='main']/div[2]/li/span/a/span[text()='"+pth+"']",1);
							System.out.println("ex. clicked on "+pth);
							
						}
						
						
				}
				//  found = driver.findElements(By.xpath("//a[text()='"+ rpt.getFileName()+"']")).size() >0;
				//System.out.println("file found:" +found );
				//click on project
				
				//if(found){
					clickON("//a[text()='"+rpt.getFileName()+"']",1);
				
					//click on run report
					clickON(runReportButton,10);
					
					
					 
					//look for second report button
					try{
						//click on run report
						clickON(runReportButton,10);
					}catch(Exception e){
						System.out.println("Did not find second run button");
					}
					
					 System.out.println("Running Report "+ rpt.getFileName() );
					//wait 15 minutes
					driver.manage().timeouts().implicitlyWait(15, TimeUnit.MINUTES);
					
					//look for toolbar
					//class="mstrTabbedMenuVBoxItem"
					if( driver.findElement(By.xpath("//*[@class='mstrTabbedMenuVBoxItem']") ).isDisplayed()){
					
						System.out.println("Ran Report: "+ rpt.getFileName());
					}
					else{
						System.out.println("Did not run report");
					}
					
					//click back home
					clickON(topNavHomeLink,5000);
					
					//break;
//				}else{
//					//click back home
//					clickON(topNavHomeLink,5000);
//					System.out.println("clicked home");
//				}
			
			}catch(Exception e){
				
				System.out.println("Something wrong happened. \n"+ e);
				System.out.println("Trying next user.\n");
				//break;
			}
			
		}
		
	}
	
	//run all reports for this user.
	//traverse and get all the folders and reports
	//run reports on current directory and move to the next 
	//TODO create method to dynamically run reports recursive 
	public void traverseReports(){
		
		
		clickON("//a[text()='PIN Explorer']",5);
		clickON("//a[text()='My Reports']",5);
		
		clickON("//a[text()='Edward Test Cases']",10);
		
		waitSec(10);
		
		//dynamically get the objects
		for(int i=1;i<3;i++){
//			String projectName = driver.findElement(By.xpath(".//*[@id='projects_ProjectsStyle']/table/tbody/tr/td["+i+"]/div/table/tbody/tr/td[2]/div/a")).getText();
			//String projectName = driver.findElement(By.xpath("//*[@id='FolderIcons']/tbody/tr/td["+i+"]/div/table/tbody/tr/td[2]/div/span/a")).getText();
			
			String projectName="";
			String projectType="";
			try{
				projectName = driver.findElement(By.xpath("//*[@id='FolderIcons']/tbody/tr[1]/td["+i+"]/div/table/tbody/tr/td[2]/div/span/a")).getText();
				projectType = driver.findElement(By.xpath("//*[@id='FolderIcons']/tbody/tr[1]/td["+i+"]/div/table/tbody/tr/td[2]")).getAttribute("oty");
				
			
				System.out.println("Folder name: "+projectType);
				System.out.println("Folder type: "+projectName);
			
			
			
			}catch(Exception e){
				projectType = driver.findElement(By.xpath("//*[@id='FolderIcons']/tbody/tr[2]/td/div/table/tbody/tr/td")).getAttribute("oty");
				
				System.out.println("Folder name: "+projectName);
			}
			
			//if it is a folder click through
//			if(projectType.equals("8")){
//				clickON("//a[text()='"+ driver.findElement(By.xpath("//*[@id='FolderIcons']/tbody/tr[1]/td["+i+"]/div/table/tbody/tr/td[2]/a")).getText() +"']",1);
//				
//			}else{
//				clickON(driver.findElement(By.xpath("//*[@id='FolderIcons']/tbody/tr[2]/td/div/table/tbody/tr/td")).getText(),1);
//			}
			
			
		}
	
	}
	
	public static void runReportsFromDB(String environment,WebDriver driver) throws IOException{
		
		
		
		//grab users from DB
		Database db = new Database("localhost/qa_platform","root","qazwsx");
		db.connect();
		ArrayList<User> users = db.getUsersDB();
		
		int numOfUsers=20;
		
		//link users with their reports
		for(int i=0;i<users.size();i++){
			User usr = users.get(i);
			//System.out.println("user: "+usr.getUid()+ " password: " +usr.getPw() + " environment: "+ usr.getEnvironment());
			
			//set environment 
			usr.setEnvironment(environment);
			usr.setDriver(driver);
		
			//get reports pertaining to that user
			ArrayList<Report> reports = new ArrayList<Report>();
			reports= db.getReport(usr);
			
			usr.setReports(reports);
	
	
		}
		//link users with their reports
				for(int i=0;i<users.size();i++){
					User usr = users.get(i);
					
					if(usr.signIn()){
						usr.runReportDB();
						usr.signOut();
					}
					
				}
				
				System.out.println("number of users: "+ users.size());
				
				db.disconnect();
	}
	
}
