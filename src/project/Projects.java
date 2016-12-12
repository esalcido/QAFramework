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
		
	  //print the hashmap
	  		Set set = hmap.entrySet();
	  		Iterator iterator = set.iterator();
	  		while(iterator.hasNext()){
	  			Map.Entry<String, String> mentry = (Map.Entry<String, String>)iterator.next();
	  			
	  			User user = new User(mentry.getKey(),mentry.getValue(),environment,driver);
	  			
	  			System.out.print("User: "+mentry.getKey() + " password: "+mentry.getValue()+"\n");
	  			fh.writeToFile("User: "+mentry.getKey()+",");
	  			if(user.signIn()){
	  				//write password result to file
	  				fh.writeToFile("True,");
	  				
	  				//check what project users belong to.  Explorer or navigator
	  				checkProjects(pinEx,pinNav,fh);
	  				
	  				//user.runReport(1);
	  				
	  				
		  			user.signOut();
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
		
		//runCheck(user1,fh);

		 
		//create the user
		//User user1 = new User("qausa","4Quality@WLV",environment,driver);	
		//get reports from file		
//		user1.signIn();
//		user1.runReport(2);
//		user1.signOut();
	
		
	  	System.out.println("End of script.");
		
	  	fh.close();
	  	
		
	}

//	public static void runCheck(User user, FileHandler writer) throws IOException{
//		boolean pinEx=false,pinNav=false,pwIncorrect=false;
//		
//		user.signIn();
//		//System.out.print("user: "+ user.getUid());
//		writer.writeToFile(user.getUid()+",");
//		
//		//check password
//		//pwIncorrect = checkPW(pwIncorrect,writer);
//		try{
//		if( !user.isPasswordValid()){
//			//write to file
//			writer.writeToFile(pwIncorrect+",");
//			user.signOut();
//		}
//		}catch(Exception e){
//			writer.writeToFile(pwIncorrect+",");
//			
//		}
//		
//		
//		checkProjects(pinEx,pinNav,writer);
//		
//		user.signOut();
//	}
//	
//	public static boolean checkPW(boolean pwIncorrect,FileHandler fw) throws IOException{
//		try{
//		//check if pw is correct	
//		pwIncorrect = driver.findElement(By.xpath(".//*[@id='mstrWeb_error']/div/div[2]") ).isDisplayed(); 
//		
//		//System.out.print(" password: "+pwIncorrect+" ");
//		
//		//write to file
//				fw.writeToFile(pwIncorrect+",");
//				
//		}catch(Exception e){
//			//System.out.print(" caught. password: "+pwIncorrect+" ");
//			fw.writeToFile(pwIncorrect+",");
//			
//		}
//		
//		return pwIncorrect;
//	}
	
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