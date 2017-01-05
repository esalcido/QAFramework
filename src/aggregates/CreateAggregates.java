package aggregates;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import project.User;

public class CreateAggregates {

	
	
	
private static void createAggregate(HashMap hm, Aggregate agg, User user, String parent_Window, WebDriver driver){
		
		driver.findElement(By.xpath(hm.get("dropdown_arrow").toString() ) ).click();
		
		//input the agg name
		driver.findElement(By.xpath(hm.get("input_box").toString() ) ).sendKeys(agg.getName() + " "+ agg.getUserType());
		
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
		}catch(Exception e){
			System.out.println(agg.getName() +" Timed Out.");
		}
			
		//click on continue box90
		driver.findElement(By.xpath(hm.get("top_breadcrumb").toString()) ).click();
	
		//switch back to parent window
		driver.switchTo().window(parent_Window);
		System.out.println("Back to parent window = " + driver.getTitle());
		
	}
	private static void createAggregateToyotaFord(HashMap hm, Aggregate agg, User user, String parent_Window, WebDriver driver){
		
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
	
	
}
