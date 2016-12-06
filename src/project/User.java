package project;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
	
	
	public void sigIn(){
		driver.get(environment);
		
		//Find User name field and enter qausa
		driver.findElement(By.id("Uid")).sendKeys(uid);
				
		//Find Password field and enter qausa
		driver.findElement(By.id("Pwd")).sendKeys(password);
				
		//Find Login and click on it;
		driver.findElement(By.id("3054")).click();
		
	}
	
	public void signOut(){
				
		//Find Logout and click on it;
		//driver.findElement(By.id("3054")).click();
		driver.findElement(By.xpath("//a[contains(text(),'click here')]") ).click();
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
	
	public String toString(){
		
		String str = "User id is "+uid+ " Password is "+password;
		
		
		return str;
	}
	
	
}
