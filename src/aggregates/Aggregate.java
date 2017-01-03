package aggregates;

public class Aggregate {

	private String name;
	private String userType;
	
	
	public Aggregate(String nm, String ut){
		name = nm;
		userType = ut;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String toString(){
		String str="";
		
		str = name+" " +userType;
		
		return str;
	}
	
	
	
}
