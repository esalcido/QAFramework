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
	private WebDriver driver;
	
	public User(String id, String pw, String env,WebDriver drv){
		uid = id;
		password = pw;
		environment = env;
		driver = drv;
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
		
		
		if(this.isPasswordValid()){
			System.out.println("Signed in. ");
			return true;
		}
		else{
			System.out.println("Password invalid\n");
			return false;
		}
		
		
	}
	
	public void signOut(){
				
		//Find Logout and click on it;
		//driver.findElement(By.id("3054")).click();
		driver.findElement(By.xpath("//a[contains(text(),'click here')]") ).click();
		//driver.findElement(By.className("mi-dskt-logout")).click();
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
		
		//Instantiate an Action Object to perform an action an "element".
		Actions actions = new Actions(driver);
		
		//click on "element" which holds "PIN Explorer Project" element 
		actions.moveToElement(element).doubleClick().perform();
		
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
	
	
	public boolean isPasswordValid()throws NoSuchElementException{
		try{
		if(driver.findElement(By.xpath(".//*[@id='mstrWeb_error']/div/div[2]") ).isDisplayed()){
			return false;
		}else{
		return true;
		}
		}catch(Exception e ){
			return true;
		}
	}
	
	public void runReport(int maxAmnt) throws IOException{
		
		//get report from file
		FileHandler fh2 = new FileHandler("resources/reports.csv","resources/output/reportsoutput.csv");
		fh2.createFile();
		
		//get to home page
		clickON("//a[text()='PIN Explorer']",10);
		
		System.out.println("Fetching reports from Text file "+ fh2.getFileName());
		ArrayList<Report> arrList = fh2.readFileArr();
		System.out.println("Got 'em.\n");
		
		
		//run through all reports in the text file
		//for(Report r : arrList){
		for( int i =0;i< maxAmnt;i++){
			
			//get to the report
			//get file path and click through to the project
			
			//System.out.println("Report: " + r.toString());
			System.out.println("Report: " + arrList.get(i).toString());
			
			//click through to project
			//String [] filePath = r.getFilePath();
			String [] filePath = arrList.get(i).getFilePath();
			for(String pth: filePath){
				System.out.println("clicked on "+pth);
				clickON("//a[text()='"+pth+"']");
				
			}
			 
			//click on project
			//clickON("//a[text()='"+r.getFileName()+"']",5);
			clickON("//a[text()='"+arrList.get(i).getFileName()+"']",5);
			
			//click on run report
			clickON("//*[@value='Run Report']",5);
			
			//wait 15 minutes
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.MINUTES);
			
			//click back home
			clickON("//*[@name='tbHome']",1000);
			
		}
		
		
		
	}
	

}
