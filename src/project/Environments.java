package project;

public enum Environments {

	DEV86("http://10.162.0.86/Navigator/asp/Main.aspx"),
	DEV90("http://10.162.0.90/Navigator/asp/Main.aspx"),
	DEV2("http://10.22.106.2/pin/asp/Main.aspx"),
	DEV3("http://10.22.106.3/pin/asp/Main.aspx"),
	DEV("https://www.dev-pin.jdpower.com/pin/asp/Main.aspx"),
	
	QAEXP("https://www.pinexplorerqa.jdpa.com/MicroStrategy/asp/Main.aspx"),
	QA2("http://10.22.104.2/PIN/asp/Main.aspx"),
	QA3("http://10.22.104.3/PIN/asp/Main.aspx"),
	
	
	PRODEXP("https://www.pinexplorer.jdpa.com/MicroStrategy/asp/Main.aspx");
	
	private String env;
	
	Environments(String env){
		this.env = env;
	}
	
	public String url(){
		return env;
	}
	
	
	//System.out.println(Environments.DEV86.url());
}
