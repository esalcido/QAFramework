package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import files.FileHandler;

public class User {

	private  String uid;
	private  String password ;
	private  String environment;
	private  WebDriver driver;
	private int numOfUsers;
	
	//selenium objects
	public static final String logoutButton = "//*[@id='menuTabs']/div[2]/div[1]/span[14]/a";
	public static final String errorMessage = ".//*[@id='mstrWeb_error']/div/div[2]";
	public static final String runReportButton = "//*[@value='Run Report']";
	public static final String topNavHomeLink = "//*[@name='tbHome']";
	//public static final String topNavHomeLink = "//*[@id='projects_ProjectsStyle']/table/tbody/tr/td[1]/div/table/tbody/tr/td[2]/a";
	
	
	public User(String id, String pw, String env,WebDriver drv){
		uid = id;
		password = pw;
		environment = env;
		driver = drv;
	}
	
	public User(String id, String pw,int nou){
		uid = id;
		password = pw;
		numOfUsers = nou;
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
		driver.get(environment);
		
		//Find User name field and enter qausa
		driver.findElement(By.id("Uid")).sendKeys(uid);
				
		//Find Password field and enter qausa
		driver.findElement(By.id("Pwd")).sendKeys(password);
				
		//Find Login and click on it;
		driver.findElement(By.id("3054")).click();
		
		System.out.println("Signed in: "+ getUid());
		return true;
//		try{
//		
//		if(this.isPasswordValid()){
//			System.out.println("Signed in: " + getUid());
//			return true;
//		}
//		else{
//			System.out.println("Password invalid\n");
//			return false;
//		}
//		}catch(Exception e){
//			return true;
//		}
		
		
	}
	
	public void signOut(){
				
		//Find Logout and click on it;
		//driver.findElement(By.id("3054")).click();
//		driver.findElement(By.xpath("//a[contains(text(),'click here')]") ).click();
//		driver.findElement(By.xpath("//*[@id='menuTabs']/div[2]/div[1]/span[14]/a") ).click();
		
		try{
			driver.findElement(By.xpath(logoutButton) ).click();
		}catch(Exception e){
			driver.findElement(By.xpath(".//*[@id='mstrWeb_content']/div[1]/a") ).click();	
		}
		
		
		System.out.println("Signed out\n");
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
		System.out.println("web element: "+element);
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
	
	
	public  boolean isPasswordValid(){
		System.out.println("I am here at sign in");
		
		
			System.out.println("errorMessg:  "+driver.findElement(By.xpath(errorMessage) ).isDisplayed());
			if(driver.findElement(By.xpath(errorMessage) ).isDisplayed())
				return false;
			else
				return true;
		
	}
	
	public int runReport(int start) throws IOException{
		//TODO fool proof if file is not 
		
		 boolean found=false;
		
		//get report from file
		FileHandler fh2 = new FileHandler("resources/reports.csv","resources/output/reportsoutput.csv");
		fh2.createFile();
		
		//get to home page
		clickON("//a[text()='PIN Explorer']",1);
		
		System.out.println("Fetching reports from Text file "+ fh2.getFileName());
		
			ArrayList<Report> arrList = fh2.readFileArr();
			System.out.println("Got 'em.\n");
		
			int maxAmnt = start + getNumOfUsers();
		//run through all reports in the text file
		for( int i =start;i< maxAmnt;i++){
			try{
				//get to the report
				//get file path and click through to the project
				System.out.println("Report: " + arrList.get(i).toString());
				
				//click through to project
				String [] filePath = arrList.get(i).getFilePath();
				for(String pth: filePath){
					
					//try{
						try{
							clickON("//a[text()='"+pth+"']",1);
							System.out.println("clicked on "+pth);
		
						}catch(Exception e){
							clickON("//*[@id='main']/div[2]/li/span/a/span[text()='"+pth+"']",1);
							System.out.println("ex. clicked on "+pth);
						}
						System.out.println("i am here ");
						
				}
				  found = driver.findElements(By.xpath("//a[text()='"+arrList.get(i).getFileName()+"']")).size() >0;
				System.out.println("file found:" +found );
				//click on project
				
				if(found){
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
					
					//wait 15 minutes
					driver.manage().timeouts().implicitlyWait(15, TimeUnit.MINUTES);
					
					//look for toolbar
					//class="mstrTabbedMenuVBoxItem"
					if( driver.findElement(By.xpath("//*[@class='mstrTabbedMenuVBoxItem']") ).isDisplayed()){
					
						System.out.println("Ran Report: "+ arrList.get(i).getFileName());
					}
					else{
						System.out.println("Did not run report");
					}
					
					//click back home
					clickON(topNavHomeLink,5000);
					System.out.println("clicked home");
					
				}else{
					//click back home
					clickON(topNavHomeLink,5000);
					System.out.println("clicked home");
				}
			
			}catch(Exception e){
				
				System.out.println("Something wrong happened. \n"+ e);
				System.out.println("Trying next user.\n");
				break;
			}
			
		}
		return maxAmnt;
		
		
		
	}
	

}
