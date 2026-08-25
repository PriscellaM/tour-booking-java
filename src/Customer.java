//Priscella Maenar 104511548 - COS10033 Assignment 2
//Customer.java
import java.io.*;

public class Customer implements Insurance, Serializable{
	private String name;
	private String contact;
	private double insurance;
	
	//constructor
	public Customer(String name, String contact) {
		this.name=name;
		this.contact=contact;
	}
	
	//get set method
	public String getCustName() {return this.name;}
	public String getCustContact() {return this.contact;}
	public double getInsurance() {return this.insurance;}
	public void setInsurance(Tour tour, double insuranceAmt) throws illegalArgException {
		if(tour.compareTo("International")==0) {
			if(insuranceAmt==0){
				throw new illegalArgException("Ticket Reservation failed. Insurance cover is needed for International tour");
			}else { this.insurance=insuranceAmt; }
		}else { this.insurance=insuranceAmt; }
	}
	
	public String toString() {
		return ("  Customer Name: "+this.getCustName()+"  Contact: "+this.getCustContact()+"  Insurance($): "+this.getInsurance());
	}
	
	
	public double Insurance(int pax, int userInput) {		//to calc insurance cover
		double insurance=0.0;
		if(userInput!=1) { insurance=0.0; }
		else {
			while(pax>0) {
				if(pax<=0) {break;}
				else if(pax==1) { pax=pax-1; insurance=insurance+200.0; }
				else if(pax==2) { pax=pax-2; insurance=insurance+350.0; }
				else if(pax>=3 && pax<=5) { 
					if(pax==3) { pax=pax-3; }
					if(pax==4) { pax=pax-4; }
					else { pax=pax-5; }
					insurance=insurance+500.0; 
				}else { pax=pax-5; insurance=insurance+500.0; }
			}
		}return insurance;
	}
}
