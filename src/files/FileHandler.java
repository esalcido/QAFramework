package files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileHandler {

	String fileName;
	String outfileName;
	File infile;
	File outfile;
	FileWriter fw ;
	BufferedWriter writer ;
	
	public FileHandler(String ifn,String ofn) throws IOException{
		fileName = ifn;
		outfileName= ofn;
	
		infile = new File(fileName);
		outfile = new File(outfileName);
		
		fw = new FileWriter(outfile,true);
		writer = new BufferedWriter(fw);
	
	}
	
	public boolean createFile() throws IOException{

		if(!infile.exists()){
			infile.createNewFile();
			
			return true;
		}
		else{
			return false;
		}
	}
	
	public static HashMap<String,String> readFile(String fileName) throws IOException,NullPointerException
	{
		
		HashMap<String,String> hmap = new HashMap<String,String>();
		
				//Open file to read from
				FileReader fil = new FileReader (fileName);
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
	
	
	public boolean writeToFile(String message) throws IOException{

		System.out.println(message);
		writer.write(message);
		
		return true;
	}
	
	public void close() throws IOException{
		writer.flush();
		writer.close();
	}
	
}
