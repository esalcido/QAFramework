package aggregates;

public class Aggregate {

	private String name;
	private String userType;
	private String aggType;
	
	public Aggregate(String nm, String ut){
		name = nm;
		userType = ut;
		
	}

	public Aggregate(String nm, String ut, String at){
		name = nm;
		userType = ut;
		aggType = at;
	}

	public String getAggType() {
		return aggType;
	}

	public void setAggType(String aggType) {
		this.aggType = aggType;
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
