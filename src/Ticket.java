//Priscella Maenar 104511548 - COS10033 Assignment 2
//Ticket.java
import java.io.*;
import java.util.*;

class illegalArgException extends Exception {
	public illegalArgException(String msg) {
		super(msg);
	}
}

public class Ticket implements Serializable{
	private int ticketID;
	private int adultPax;
	private int childPax;
	private double disc;
	private String pickup;
	private double pickupCost;
	private double total;
	private String status;
	public Tour tour;
	public Customer customer;
	public Account account;
	
	//Constructor
	public Ticket(int ticketID, int adultPax, int childPax, Account acc) throws illegalArgException {
			this.ticketID=ticketID;
			this.status="Valid";
			this.adultPax=adultPax;
			this.childPax=childPax;
			this.account=acc;
		}
	
	//set and get method
	public int getID() {return this.ticketID;}
	public int getAdultPax() {return this.adultPax;}
	public int getChildPax() {return this.childPax;}
	public double getTotal() {return this.total;}
	public double getDisc() {return this.disc; }
	public String getPickup() {return this.pickup;}
	public double getPickupCost() {return this.pickupCost;}
	public String getStatus() {return this.status;}
	public void setPickup(String pickup) {this.pickup=pickup; }
	public void setStatus(String status) {this.status=status;}
	public void setTotal(double total) {this.total=total;}
	public void setDisc(double totalBeforeDisc) {this.disc=0.0;}
	public void addTour(Tour tour) { this.tour=tour; }
	public double calcTotal() {
		double total=(this.adultPax*this.tour.getaPrice())+(this.childPax*this.tour.getcPrice());
		return total;
	}
	public double refund() {
		double refundRate=0.75;
		double refundAmt=refundRate*this.total;
		this.total=this.total-refundAmt;
		this.disc=0.0;
		return refundAmt;
	}
	public void setPickupCost(int pax, Scanner in) {		//for pickupCost for Interstate/International tour
		double pickupCost=150.0;
		if(pax>6) { 
			System.out.print("Number of guests more than 6. Enter airport Pickup & Drop Off cost: $");
			pickupCost=in.nextDouble(); 
		}
		System.out.println("Enter 1 to Add airport Pickup & Drop Off at the destination for $"+pickupCost+" OR enter any other number to skip. ");
		int choice=in.nextInt();
		in.nextLine();
		if(choice!=1) { pickupCost=0.0; }
		this.pickupCost=pickupCost;
	}
	
	public String toString() {
		return ("TicketID: "+this.ticketID+"  Adult(pax): "+this.adultPax+"  Child(pax): "+this.childPax+"  Status: "+this.status+this.account.toString()+"  Total($): "+this.total);
	}
}

class NormalTic extends Ticket{		//disc 0%, no disc	
	//constructor
	public NormalTic(int ticketID, int adultPax, int childPax, Account acc) throws illegalArgException{
		super(ticketID, adultPax, childPax, acc);
	}
	
	public String toString(){
		String temp=super.toString();
		temp=temp+("  Discount($): "+this.getDisc()+"\n  "+this.tour.toString()+"  PickUp/Airport: "+this.getPickup()+"  AirportP&D($): "+this.getPickupCost());
		if(this.customer!=null && this.customer!=null){
			temp=temp+"\n "+this.customer.toString();
		}
		return temp;
	}
}
class FamilyTic1 extends Ticket{	//1 adult 1 child or 2 adult 1 child 
	private double discRate;		//disc 10%
	private double disc;
	//constructor
	public FamilyTic1(int ticketID, int adultPax, int childPax, Account acc) throws illegalArgException{
		super(ticketID, adultPax, childPax, acc);
		this.discRate=0.10;
	}
	
	public void setDisc(double totalBeforeDisc) {
		this.disc=totalBeforeDisc*this.discRate;
		this.setTotal(totalBeforeDisc-this.disc);
	}
	
	public String toString(){
		String temp=super.toString();
		temp=temp+("  Discount10%($): "+this.disc+"\n  "+this.tour.toString()+"  PickUp/Airport: "+this.getPickup()+"  AirportP&D($): "+this.getPickupCost());
		if(this.customer!=null && this.customer!=null){
			temp=temp+"\n "+this.customer.toString();
		}
		return temp;
	}
}
class FamilyTic2 extends Ticket{	//4 pax or more
	private double discRate;		//disc 15%
	private double disc;
	//constructor
	public FamilyTic2(int ticketID, int adultPax, int childPax, Account acc) throws illegalArgException{
		super(ticketID, adultPax, childPax, acc);
		this.discRate=0.15;
	}
	
	public void setDisc(double totalBeforeDisc) {
		this.disc=totalBeforeDisc*this.discRate;
		this.setTotal(totalBeforeDisc-this.disc);
	}
	
	public String toString(){
		String temp=super.toString();
		temp=temp+("  Discount15%($): "+this.disc+"\n  "+this.tour.toString()+"  PickUp/Airport: "+this.getPickup()+"  AirportP&D($): "+this.getPickupCost());
		if(this.customer!=null && this.customer!=null){
			temp=temp+"\n "+this.customer.toString();
		}
		return temp;
	}
}