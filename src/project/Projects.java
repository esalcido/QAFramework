package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

public class Projects {

	public static WebDriver driver = new FirefoxDriver();
			
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub


		HashMap<String,String> hmap = new HashMap<String,String>();
		boolean pinEx=false,pinNav=false,pwIncorrect=false;
		
		//======================= Handle the files =================================
		
		FileHandler fh = new FileHandler("resources/users.csv","resources/test.csv");
		fh.createFile();
		
		//create the user
		User user1 = new User("qausa","4Quality@WLV",Environments.DEV90.url(),driver);
		
		
//		try {
//			 //hmap = readFile(fileName);
//			hmap = fh.readFile(fileName);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	  //print the hashmap
//	  		Set set = hmap.entrySet();
//	  		Iterator iterator = set.iterator();
//	  		while(iterator.hasNext()){
//	  			Map.Entry<String, String> mentry = (Map.Entry<String, String>)iterator.next();
//	  			
//	  			User user = new User(mentry.getKey(),mentry.getValue(),Environments.DEV86.url(),driver);
//	  			
//	  			System.out.print("User: "+mentry.getKey() + " password: "+mentry.getValue()+"\n");
//	  			
//	  			runCheck(user,writer);
//
//	  		}
	  			
		
//		for(int i=1;i<3;i++){
//			String projectName = driver.findElement(By.xpath(".//*[@id='projects_ProjectsStyle']/table/tbody/tr/td["+i+"]/div/table/tbody/tr/td[2]/div/a")).getText();
//			
//			System.out.println(projectName);
//		}
		
		runCheck(user1,fh);

	  	System.out.println("End of script.");
		
	  	fh.close();
	  	
		
	}

	public static void runCheck(User user, FileHandler writer) throws IOException{
		boolean pinEx=false,pinNav=false,pwIncorrect=false;
		
		user.sigIn();
		
		//System.out.print("user: "+ user.getUid());
		writer.writeToFile(user.getUid()+",");
		
		pwIncorrect = checkPW(pwIncorrect,writer);

		//user.waitSec(10);
		if(pwIncorrect==true){
		user.signOut();
		}
		checkProjects(pinEx,pinNav,writer);
		
		user.signOut();
	}
	
	public static boolean checkPW(boolean pwIncorrect,FileHandler fw) throws IOException{
		try{
		//check if pw is correct	
		pwIncorrect = driver.findElement(By.xpath(".//*[@id='mstrWeb_error']/div/div[2]") ).isDisplayed(); 
		
		//System.out.print(" password: "+pwIncorrect+" ");
		
		//write to file
				fw.writeToFile(pwIncorrect+",");
				
		}catch(Exception e){
			//System.out.print(" caught. password: "+pwIncorrect+" ");
			fw.writeToFile(pwIncorrect+",");
			
		}
		
		return pwIncorrect;
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
		 fw.writeToFile(pinNav+",");
	}
	
	
}