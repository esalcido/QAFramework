package project;

import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.Arrays;

public class Report {

	String [] filePath;
	String fileName;
	String filePathStr;
	
	public Report(){
		
		fileName = "";
	}
	
	public Report(String [] fp){
		
		filePath = fp;
		fileName = fp[fp.length-1];
		
		//remove last element from the array
		filePath = ArrayUtils.remove(filePath, filePath.length-1);
	}
public Report(String path, String fn){
		
		filePathStr = path;
		fileName = fn;

	}


	public String[] getFilePath() {
		return filePath;
	}


	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public String toString(){
		String result="";

		for(int i=0;i<filePath.length;i++){
			result+=filePath[i]+",";
			//System.out.println("fp: "+ filePath[i]);
		}
		
		result +=","+ getFileName();
		
		return result;
	}
	
	
	
}
