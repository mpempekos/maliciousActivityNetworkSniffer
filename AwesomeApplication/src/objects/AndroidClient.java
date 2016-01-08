package objects;

public class AndroidClient {
	private String isLoggedIn;
	private String username;
	private String password;

	public AndroidClient() {}
	
	public AndroidClient(String username, String password) {
		this.isLoggedIn = "no";
		this.username = username;
		this.password = password;
	}
	
	public AndroidClient(String isLoggedIn, String username, String password) {
		this.isLoggedIn = isLoggedIn;
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getIsLoggedIn() {
		return isLoggedIn;
	}
	public void setIsLoggedIn(String isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
