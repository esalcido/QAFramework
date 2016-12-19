package files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import database.Database;
import project.Report;
import project.User;

public class FileHandler {

	String fileName;
	String outfileName;
	public static File infile;
	File outfile;
	FileWriter fw ;
	BufferedWriter writer ;
	
	
	//constructor for both an input and out file
	public FileHandler(String ifn,String ofn) throws IOException{
		fileName = ifn;
		outfileName= ofn;
	}
	
	public boolean createFile() throws IOException,FileNotFoundException{
		
		infile = new File(fileName);
		outfile = new File(outfileName);
		
		fw = new FileWriter(outfile,true);
		writer = new BufferedWriter(fw);
		
		if(!infile.exists()){
			infile.createNewFile();
			
			return true;
		}else if(!outfile.exists()){
			outfile.createNewFile();
			return true;
		}
		else{
			return false;
		}
	}
	
	public static HashMap<String,String> readFile() throws IOException,NullPointerException
	{
		
		HashMap<String,String> hmap = new HashMap<String,String>();
		
				//Open file to read from
				FileReader fil = new FileReader (infile);
				//Load data from file to buffer 
				BufferedReader textReader = new BufferedReader(fil);
				
				String lineofText;
				while((lineofText = textReader.readLine()) !=null){
					
					String [] params ;
					params = lineofText.split(",");
					
					hmap.put(params[0], params[1]);
				}
				
				textReader.close();
				return hmap;
	}
	
	public static ArrayList<User> readFileUsersArr() throws IOException{
		ArrayList aList = new ArrayList<User>();
		
		//Open file to read from
		FileReader fil = new FileReader (infile);
		//Load data from file to buffer 
		BufferedReader textReader = new BufferedReader(fil);
		
		//read in all tokens from line of text
		String lineofText;
		while((lineofText = textReader.readLine()) !=null){
			
			String [] params ;
			params = lineofText.split(",");
			
			//put all tokens into a report
			//the report object will get the filename automatically
			
			User rept = new User(params[0],params[1],Integer.parseInt(params[2]) );
			aList.add(rept);
		
		}
		
		textReader.close();
		
		
		return aList;
		
	}
	
	public static boolean readFileAddUsersToDB() throws IOException{
		try{
		ArrayList aList = new ArrayList<User>();
		
		//Open file to read from
		FileReader fil = new FileReader (infile);
		//Load data from file to buffer 
		BufferedReader textReader = new BufferedReader(fil);
		
		//create db and add users in here
		//DATABASE STUFF
		Database db = new Database("localhost/qa_platform","root","qazwsx");
		db.connect();
		
		//read in all tokens from line of text
		String lineofText;
		while((lineofText = textReader.readLine()) !=null){
			
			String [] params ;
			params = lineofText.split(",");
			
			System.out.println(params[0]+" "+params[1]);
			//put all tokens into a report
			//the report object will get the filename automatically
			db.insertUsers(params);
			
		
		}
		
		textReader.close();
		db.disconnect();
		return true;
		}catch(Exception e){
		return false;
		}
		
	}
	
	public static ArrayList<Report> readFileArr() throws IOException,NullPointerException
	{
		
		ArrayList aList = new ArrayList<Report>();
		
				//Open file to read from
				FileReader fil = new FileReader (infile);
				//Load data from file to buffer 
				BufferedReader textReader = new BufferedReader(fil);
				
				
				//read in all tokens from line of text
				String lineofText;
				while((lineofText = textReader.readLine()) !=null){
					
					String [] params ;
					params = lineofText.split(",");
					
					//put all tokens into a report
					//the report object will get the filename automatically
					Report rept = new Report(params);
					aList.add(rept);
				
				}
				
				textReader.close();
				return aList;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean writeToFile(String message) throws IOException{

		System.out.println("wrote '"+ message +"' to file.");
		writer.write(message);
		
		return true;
	}
	
	public void close() throws IOException{
		writer.flush();
		writer.close();
	}
	
}
