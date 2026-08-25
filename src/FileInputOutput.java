//Priscella Maenar 104511548 - COS10033 Assignment 2
//FileInputOutput.java
import java.util.*;
import java.io.*;

class Data implements Serializable {
	private ArrayList<Account> aList;
	private ArrayList<Ticket> tList;
	private CityTour[] cTour;
	private Attractions[] attractions;
	
	//constructor
	public Data(ArrayList<Account> aList, ArrayList<Ticket> tList, CityTour[] ctour, Attractions[] attr) {
		this.aList=aList;
		this.tList=tList;
		this.cTour=ctour;
		this.attractions=attr;
	}
	
	public ArrayList<Account> getAccList() { return this.aList; }
	public ArrayList<Ticket> getTicList() { return this.tList; }
	public CityTour[] getCityTour() { return this.cTour; }
	public Attractions[] getAttractions() { return this.attractions; }
}

public class FileInputOutput {
	public static Data readFile(String fName) throws Exception {
		Data data=null;
		FileInputStream fi=null;
		ObjectInputStream oi=null;
		try {
			fi = new FileInputStream(fName);
			oi = new ObjectInputStream(fi);
			while(true) { data=(Data) oi.readObject();}
		}catch (Exception e) {
			if(e.getMessage()!=null) {
				System.out.println("data.txt file does not exist. Creating file with admin login details stored in it.\nUsername/Password: Admin\n"); 
			}
		}finally {
			if(oi!=null){oi.close();}			
			if(fi!=null) {fi.close();}
		}
		return data;
	}
	
	public static void writeFile(Data data, String fName) {
		try {
			FileOutputStream fo = new FileOutputStream("data.txt");
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(data);
			fo.close();
			oo.close();
		}
		catch(Exception e) { System.out.println(e.getMessage()); }
	}
	
	public static ArrayList<Account> readLoginFromFile(Data data, String fName, Scanner in) throws Exception {
		int addMoreAcc=1;
		ArrayList<Account> accList=new ArrayList<Account>();
		if(data.getAccList().isEmpty()) {
			data.getAccList().add(new Account("Admin", "Admin", "Admin"));	//admin login
			writeFile(data, fName);		//write admin details to file
		}
		while(addMoreAcc==1) {
			if(data.getAccList().size()==1) {	//admin login exists but no sales executives login exist
				System.out.print("No Sales Executive Account Exists. Create new Sales Executive Accounts.\nEnter Admin username to login: ");
				String username=in.nextLine();
				System.out.print("Enter Admin password to login: ");
				String password=in.nextLine();
				if(username.equals("Admin") && password.equals("Admin")) {
					boolean loginCorrect=false;
					while(loginCorrect==false) {
						Account acc=addSalesExecAcc(in);
						if(acc!=null) { 
							data.getAccList().add(acc); 
							System.out.println("Sales Executive Account Successfully created.");
							loginCorrect=true;
						}
					}
				}else {System.out.println("Wrong Admin username/password. Please retry\n");}
			}
			if(data.getAccList().size()>1) {		//sales executives login exist
				System.out.println("\n"+(data.getAccList().size()-1)+" Sales Executive Account Exists.\nEnter 1 to add more Sales Executive account OR any other number to login with existing account.");
				addMoreAcc=in.nextInt();
				in.nextLine();
				if(addMoreAcc==1) { 
					Account acc=addSalesExecAcc(in);
					if(acc!=null) {  
						data.getAccList().add(acc);
						System.out.println("Sales Executive Account Successfully created.");
					}
				}else {break;}
			}
		}writeFile(data, fName);
		return data.getAccList();
	}
	
	public static Account addSalesExecAcc(Scanner in) {
		Account acc=null;
		String username="null", password="null", name="null"; 
		try {
			System.out.print("Enter Sales Executive Name: ");
			name=in.nextLine();
			System.out.print("Enter username: ");
			username=in.nextLine();
			System.out.print("Enter password: ");
			password=in.nextLine();
			acc=new Account(name, username, password);
			
		}catch( illegalArgException e ) {System.out.println(e.getMessage());}
		return acc;
	}
	
	public static Account SalesExecLogin(ArrayList<Account> accList, Scanner in){
		Account acc=null;
		String username="null", password="null"; 
		boolean loginCorrect=false;
		while(loginCorrect==false) {
			System.out.print("Enter Username: ");
			username=in.nextLine();
			for(Account a : accList) {
				if(a==null) { break; }
				if(a.getUsername().equals(username)) {
					acc=a;
					break;
				}
			}if(acc!=null) {
				System.out.print("Enter Password: ");
				password=in.nextLine();
				if(password.equals(acc.getPassword())) { loginCorrect=true; }
				else { System.out.println("Wrong password. Please retry\n"); }
			}else {System.out.println("Username not found. Please retry\n"); }
		}
		return acc;
	}
}