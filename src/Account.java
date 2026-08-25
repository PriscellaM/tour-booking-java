//Priscella Maenar 104511548 - COS10033 Assignment 2
//Account.java
import java.io.*;

public class Account implements Serializable {
	private String username;
	private String password;
	private String name;
	
	//constructor
	public Account(String name, String username, String password) throws illegalArgException {
		this.name=name;
		this.username=username;
		if(!(password.isEmpty())) { this.password=password; }
		else { throw new illegalArgException("Password cannot be empty. Account creation failed."); }
	}
	
	public String getUsername() { return this.username; }
	public String getPassword() { return this.password; }
	public String getSEName() { return this.name; }
	
	public String toString() {
		return ("  Issued By: "+this.name);
	}
}