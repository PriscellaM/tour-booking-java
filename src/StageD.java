//Priscella Maenar 104511548 - COS10033 Assignment 2
//StageD.java
/*Java filesA: Account.java, Customer.java, Insurance.java, Ticket.java, Tour.java, StageD.java,
 				  FileInputOutput.java, JunitTest.java

	The purpose of this program is to create a tour booking program that will issue a ticket with unique
ticket ID. The ticket will have details such as ID number, number of guests(adult and child), total price, discount,
the tour name, pickup/airport location, airport pickup cost, customer name and contact, ticket status, and name
of the sales executive who issues the ticket.
	There are 3 different tour types, CityTour, Attractions and InterstateInternational tour. For CityTour, customer
will not be asked for pickup details. For Attractions, customer is required to choose a pickup location but no pickup
cost will be incurred. For InterstateInternational tour, customer will be asked for boarding airport and whether customer
wants to be picked up and dropped off to/from that airport and additional cost will be incurred for the pickup dropoff.
	There are limited availability for CityTour and Attractions. the maximum capacity is 40 for each tour. Whereas, for
InterstateInternational tour, there is no capacity limit.
	In the InterstateInternational tour, there are the Interstate tour and the International tour. Customer booking the 
Interstate tour(Cairns, Gold Coast) is not required to purchase insurance cover, it is optional. However for International
tour(Phuket, Pattaya, Singapore), customer must purcahse insurance cover. In the case where customer choose not to add the
insurance cover, booking will fail and no ticket will be issued. In addition, for InterstateInternational tour, the program
will ask for customer name and contact number.
	Customer can choose to refund booking but only 75% of the total price will be refunded to the customer. Upon successful
refund, the ticket will not be deleted from the system; instead, the status will change to "Cancelled" and the total price
will show the rest of the 25% of the price.
	The booking data will be stored in a data.txt file when option "X" (exit program) is chosen. In that same .txt file,
Admin and sales executive login details will also be stored. If no sales executive account exists, the account can be 
created by logging in as Admin. To access the program, sales executive will need to login with his/her account. When login
is successful, the booking menu will then show. The program will then read from data.txt file if ticket booking data exists.
If data exists, the program will read the data from the file and load it. When option "E" (list sales) is selected, Tickets
that have been successfully booked in previous login session (if exists) will be shown. Updated data will then be written to the 
data.txt file when exiting the program.
 */
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class StageD {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		CityTour[] ctour=new CityTour[5];
		Attractions[] attr=new Attractions[4];
		InterstateInternational[] inter=new InterstateInternational[5];
		ArrayList<Account> accList=new ArrayList<Account>();
		ArrayList<Ticket> ticList=new ArrayList<Ticket>();
		Ticket tic=null;
		String choice="null",tourName="null", pickup="N/A", fileName="data.txt";
		int choice2=0, adultPax=0, childPax=0, capacity=40, uInput=0;
		double ticketsTotal=0;
		boolean bookingSuccessful=true;
		
		Data data=FileInputOutput.readFile(fileName);
		if(data==null) { data=new Data(accList, ticList, ctour, attr); }
		
		//add login details to accList if it does not exist and store to file permanently.
		//Username/password: Admin
		accList=FileInputOutput.readLoginFromFile(data, fileName, input);	//read ticket, tour, customer file data as well
		
		//sales executive login
		Account salesExec=FileInputOutput.SalesExecLogin(accList, input);
		
		data=FileInputOutput.readFile(fileName);	//update data by reading file after logging in
		accList=data.getAccList();
		ticList=data.getTicList();
		ctour=data.getCityTour();
		attr=data.getAttractions();
		int ticketCount=1+ticList.size();			//overall ticket count for ticket ID
		
		do {
			printMenu();
			choice=input.nextLine();
			switch(choice) {
			case "A":
				int buyMoreTic=1, index=0, count=0;
				ticketsTotal=0;
				while(buyMoreTic==1) {
					boolean enoughCapacity=true; bookingSuccessful=true;
					do {
						printTourAttrMenu();
						System.out.print("\nWhich tour/attractions do you wish to buy ticket/s for: ");
						choice2=input.nextInt();
						if(choice2<1 || choice2>14) {System.out.println("Invalid input. Please enter 1-14");}
					}while(choice2<1 || choice2>14);	
					tourName=tourName(choice2);	//to get tour/attractions name
					System.out.println("Booking tickets for "+tourName+".");
					System.out.print("How many adult tickets (Max 40): ");
					adultPax=input.nextInt();
					System.out.print("How many child tickets (Max 40): ");
					childPax=input.nextInt();
					input.nextLine();
					
					//max 40
					if(adultPax>40 || childPax>40 ||(adultPax+childPax)>40) { 
						System.out.println("Number of Adults and/or Child cannot be more than 40."); 
						enoughCapacity=false;
					}
					
					//check if the tour still has enough capacity for the no of child/adult the user input.
					Ticket t=null;
					t=searchTourName2(ticList, tourName);
					if(ticList!=null && t!=null) {
						enoughCapacity=enoughCapacity(t.tour, (adultPax+childPax));
					}
					//if the tour still has enough capacity
					if(enoughCapacity==true) {
						if((adultPax==1 && childPax==1) || (adultPax==2 && childPax==1)) {
							FamilyTic1 temp=new FamilyTic1(ticketCount, adultPax, childPax, salesExec);								
							tic=(FamilyTic1)temp;
						}else if((adultPax+childPax)>=4) {
							FamilyTic2 temp=new FamilyTic2(ticketCount, adultPax, childPax, salesExec);								
							tic=(FamilyTic2)temp;
						}else { 
							NormalTic temp=new NormalTic(ticketCount, adultPax, childPax, salesExec);								
							tic=(NormalTic)temp;
						}
						count++; 		//ticket creation count while still in option A. count will reset once option A is selected again.
						ticketCount++;	//overall ticket count for ticket ID
						
						if(choice2>=1 && choice2<=5) {
							index=tourIndex(choice2);
							if(ctour[index]==null) {
								ctour[index]=cityTour(tourName, adultPax, childPax);
								ctour[index].setCapacity(capacity);
							}
							ctour[index].setCapacity((ctour[index].getCapacity())-adultPax-childPax);
							tic.setPickup(pickup);
							tic.addTour(ctour[index]);			
						}else if(choice2>=6 && choice2<=9){
							int pickupChoice=0;
							do { 
								System.out.print(" 1-Travel Australia CBD Office\n 2-Flinders Station\n 3-Southern Cross Station\n 4-Queen Victoria Market\n 5-Melbourne Museum\nChoose a PickUp location: ");
								pickupChoice=input.nextInt();
								pickup=pickupLoc(pickupChoice);
							}while(!(pickupChoice>0 && pickupChoice<6));
							index=tourIndex(choice2);
							if(attr[index]==null) {
								attr[index]=attr(tourName, adultPax, childPax);
								attr[index].setCapacity(capacity);
							}
							attr[index].setCapacity((attr[index].getCapacity())-adultPax-childPax);
							tic.setPickup(pickup);
							tic.addTour(attr[index]);
						}else if(choice2>=10 && choice2<=14) {	
							index=tourIndex(choice2);
							if(inter[index]==null) {
								inter[index]=inter(tourName, adultPax, childPax);
							}
							tic.addTour(inter[index]);
							
							//airport pickup
							System.out.print("Enter boarding airport: ");
							pickup=input.nextLine();
							tic.setPickup(pickup);
							tic.setPickupCost((adultPax+childPax), input);
							System.out.print("For interstate/international tour, enter Customer Name and Contact.\nName: ");
							String name=input.nextLine();
							System.out.print("Contact No.: ");
							String contactNo=input.nextLine();
							tic.customer=new Customer(name, contactNo);
					
							//Insurance
							System.out.println("Enter 1 to Add Insurance Cover for the tour OR enter any other number to skip. ");
							uInput=input.nextInt();
							double insurance=tic.customer.Insurance((adultPax+childPax), uInput);
							if(uInput==1) {
								System.out.println("The tour is being planned for "+(adultPax+childPax)+" guest/s. The Insurance cover would cost $"+insurance+".\nEnter 1 to add insurance OR other number to skip.");
								uInput=input.nextInt();
							}
							insurance=tic.customer.Insurance((adultPax+childPax), uInput);
							try {
								tic.customer.setInsurance(tic.tour, insurance);								
							}catch (illegalArgException e) {
								System.out.println(e.getMessage());
								ticketCount--;
								bookingSuccessful=false; count--;
							}
					
						}
						if(bookingSuccessful!=false) {
							tic.setTotal(tic.calcTotal());	//total before disc
							tic.setDisc(tic.getTotal());
							ticketsTotal=ticketsTotal+tic.getTotal();
							ticList.add(tic);
						}
					}
					
					System.out.println("\nEnter 1 to reserve another tour/attraction ticket to this customer\nOR press any other number if you do not wish to reserve more ticket.");
					System.out.print("Enter your choice: ");
					buyMoreTic=input.nextInt();
					input.nextLine();
					if(buyMoreTic!=1) {
						break; 
					}
				}
				if(count==0) {System.out.println("\nTicket reservation unsuccessful. No ticket/s have been reserved"); break;}
				for(int i=ticList.size()-count; i<ticList.size(); i++) {
					System.out.println();
					System.out.println(ticList.get(i));	
				}
				System.out.println("\nTotal --- : $"+ticketsTotal);
				break;
			case "B":
				System.out.print("Enter Ticket ID to search: ");
				int searchID=input.nextInt();
				Ticket t=searchID(ticList, searchID);
				if(t==null){ System.out.println("Ticket ID not found."); }
				else { System.out.println(t); }
				input.nextLine();
				break;
			case "C":
				double refund=0;
				System.out.print("Enter Ticket ID for refund: ");
				searchID=input.nextInt();
				input.nextLine();
				t=searchID(ticList, searchID);
				if(t==null){ System.out.println("Ticket ID not found."); }
				else { 
					System.out.println(t);
					if(t.getStatus().equals("Cancelled")) {	System.out.println("This ticket is already cancelled"); break; }
					System.out.println("\nCancelling this ticket will refund you 75% of the ticket price\nEnter 1 to proceed with the cancellation OR any other number to abort.");
					uInput=input.nextInt();
					input.nextLine();
					if(uInput!=1) {System.out.println("Refund aborted."); break;}
					refund=t.refund();
					int newCap=t.tour.getCapacity()+t.getAdultPax()+t.getChildPax();
					t.tour.setCapacity(newCap);
					t.setStatus("Cancelled");
					System.out.println("Ticket cancelled. $"+refund+" has been refunded to you.");
				}
				break;
			case "D":
				printAvailability(ctour, attr);
				break;
			case "E":
				if(ticList.isEmpty()) { System.out.println("\nNo tickets have been reserved."); break;}
				System.out.println("Reserved Tickets:");
				for(int i=0; i<ticList.size(); i++) {		
					System.out.println();
					System.out.println(ticList.get(i));
				}
				ticketsTotal=calcTotal(ticList);
				System.out.println("\nTotal --- : $"+ticketsTotal);
				break;
			case "F":
				printTourAttrMenu();
				System.out.print("\nSelect the tour number: ");
				int tourNum=input.nextInt();
				input.nextLine();
				tourName=tourName(tourNum);
				ArrayList<Ticket> tList=new ArrayList<Ticket>();
				tList=searchTourName(ticList, tourName);
				if(tList.isEmpty()) { System.out.println("\nNo tickets reserved for this tour."); break; }
				for(int i=0; i<tList.size(); i++) {
					System.out.println();
					System.out.println(tList.get(i));					
				}ticketsTotal=calcTotal(tList);
				System.out.println("\nTotal --- : $"+ticketsTotal);
				break;
			case "G":
				System.out.println("Choose Sales Executive to show sales.");
				showSEname(accList);
				System.out.print("Enter Sales Executive Name: ");
				String SEname=input.nextLine();
				boolean checkInput=checkUserInputSEname(accList, SEname);
				if(checkInput==false) {	System.out.println("Sales Executive name not found."); break; }
				tList=searchSEticket(ticList, SEname);
				if(tList.isEmpty()) { System.out.println("No tickets issued by the selected Sales Executive."); break;}
				for(int i=0; i<tList.size(); i++) {
					System.out.println();
					System.out.println(tList.get(i));					
				}ticketsTotal=calcTotal(tList);
				System.out.println("\nTotal by "+SEname+" --- : $"+ticketsTotal);
				break;
			case "X":
				data=new Data(accList, ticList, ctour, attr);
				FileInputOutput.writeFile(data, fileName);
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid user input. Please enter A-F or X.");
				break;
			}
			
		}while(!(choice.equals("X")));
	}

	//methods
	public static void printMenu() {	//main menu
		System.out.println("\nTravel Australia Ticket Reservation.");
		System.out.println(" A-Reserve Ticket\n B-Search Ticket\n C-Refund Ticket\n D-List Availability\n E-List Sales\n F-List Sales for a Tour\n G-Show Sales by Sales Executives\n X-Exit");
		System.out.print("Enter your choice: ");
	}
	public static void printAMenu() {	//menu for choice A
		System.out.println("\nWhich Type of tour do you wish to book\n A-City Tour\n B-Attractions");
		System.out.print("Enter your choice: ");
	}
	public static void printTourAttrMenu() {	//tour & attraction menu
		System.out.println("\nCity Tour\t\t\t\t\t|Attractions");
		System.out.println(" 1-Melbourne City\t\t\t\t| 6-Great Ocean Road\n 2-Melbourne City + Yarra River Boat Cruise\t| 7-Yarra Valley Wine Tasting\n 3-Yarra River Cruise + Melbourne Zoo\t\t| 8-Wilson Prom\n 4-Melbourne City + Melbourne Zoo\t\t| 9-Phillip Island + Penguin Parade\n 5-Melbourne City + Melbourne Aquarium\t\t|");
		System.out.println("\t\t\t\t\t\t|\nQueensland Tour \t\t\t\t|International Tour");
		System.out.println(" 10-Cairns\t\t\t\t\t| 12-Phuket\n 11-Gold Coast\t\t\t\t\t| 13-Pattaya\n\t\t\t\t\t\t| 14-Singapore");
	}
	public static boolean bookingFailed() {
		System.out.println("Ticket Reservation failed. Cannot book International tour without insurance cover.");
		return false;
	}
	public static String tourName(int choice) {
		String name="null";
		switch(choice) {
		case 1:
			name="Melbourne City"; break;
		case 2:
			name="Melbourne City + Yarra River Boat Cruise"; break;
		case 3:
			name="Yarra River Cruise + Melbourne Zoo"; break;
		case 4:
			name="Melbourne City + Melbourne Zoo"; break;
		case 5:
			name="Melbourne City + Melbourne Aquarium"; break;
		case 6:
			name="Great Ocean Road"; break;
		case 7:
			name="Yarra Valley Wine Tasting"; break;
		case 8:
			name="Wilson Prom"; break;
		case 9:
			name="Phillip Island + Penguin Parade"; break;
		case 10:
			name="Cairns"; break;
		case 11: 
			name="Gold Coast"; break;
		case 12:
			name="Phuket"; break;
		case 13:
			name="Pattaya"; break;
		case 14:
			name="Singapore"; break;
		}
		return name;
	}
	
	public static boolean enoughCapacity(Tour tour ,int capacity) {
		if(tour.getCapacity()<capacity) {
			System.out.println("Not enough capacity for the tour selected.");
			return false;
		}else {return true;}
	}
	public static String pickupLoc(int choice) {
		String pickup="null";
		switch(choice) {
		case 1: pickup="Travel Australia CBD Office"; break;
		case 2: pickup="Flinders Station"; break;
		case 3: pickup="Southern Cross Station"; break;
		case 4: pickup="Queen Victoria Market"; break;
		case 5: pickup="Melbourne Museum"; break;
		default: System.out.println("Invalid user input. Please enter 1-5\n"); break;
		} return pickup;
	}
	public static int tourIndex(int choice) {
		if(choice==1 || choice==6 || choice==10) {return 0;}
		if(choice==2 || choice==7 || choice==11) {return 1;}
		if(choice==3 || choice==8 || choice==12) {return 2;}
		if(choice==4 || choice==9 || choice==13) {return 3;}
		else {return 4;}	//for choice==5, choice==14
	}
	public static CityTour cityTour(String tourName,int adultPax, int childPax) {
		String type="CityTour";
		CityTour ctour=null;
		switch(tourName) {
		case "Melbourne City":
			ctour=new CityTour(type, 35.00, 25.00, tourName); break;
		case "Melbourne City + Yarra River Boat Cruise":
			ctour=new CityTour(type, 65.00, 40.00, tourName); break;
		case "Yarra River Cruise + Melbourne Zoo":
			ctour=new CityTour(type, 75.00, 45.00, tourName); break;
		case "Melbourne City + Melbourne Zoo":
			ctour=new CityTour(type, 65.00, 40.00, tourName); break;
		case "Melbourne City + Melbourne Aquarium":
			ctour=new CityTour(type, 75.00, 45.00, tourName); break;
		}
		return ctour;
	}
	public static Attractions attr(String tourName,int adultPax, int childPax) {
		String type="Attractions";
		Attractions attr=null;
		switch(tourName) {
		case "Great Ocean Road":
			attr=new Attractions(type, 135.00, 90.00, tourName); break;
		case "Yarra Valley Wine Tasting":
			attr=new Attractions(type, 85.00, 60.00, tourName); break;
		case "Wilson Prom":
			attr=new Attractions(type, 110.00, 90.00, tourName); break;
		case "Phillip Island + Penguin Parade":
			attr=new Attractions(type, 160.00, 120.00, tourName); break;	
		}
		return attr;
	}
	public static InterstateInternational inter(String tourName,int adultPax, int childPax) {
		String type1="Interstate", type2="International";
		InterstateInternational inter=null;
		switch(tourName) {
		case "Cairns":
			inter=new InterstateInternational(type1, 850.00, 700.00, tourName); break;
		case "Gold Coast":
			inter=new InterstateInternational(type1, 600.00, 450.00, tourName); break;
		case "Phuket":
			inter=new InterstateInternational(type2, 1350.00, 1100.00, tourName); break;
		case "Pattaya":
			inter=new InterstateInternational(type2, 1400.00, 1150.00, tourName); break;
		case "Singapore":
			inter=new InterstateInternational(type2, 1200.00, 1000.00, tourName); break;
		}
		return inter;
	}
	
	public static void printAvailability(CityTour[] citytour, Attractions[] attractions) {
		int capacity=0;
		System.out.println("Tour Availability:");
		for(int i=0; i<citytour.length; i++) {
			if(citytour[i]!=null) { capacity=citytour[i].getCapacity(); }
			else { capacity=40; }
			System.out.println("Tour: "+tourName(i+1)+"\n  Capacity: "+capacity);
		}
		for(int j=5; j<(5+attractions.length); j++) {
			if(attractions[j-5]!=null) { capacity=attractions[j-5].getCapacity(); }
			else { capacity=40; }
				System.out.println("Tour: "+tourName(j+1)+"\n  Capacity: "+capacity);
		}
	}
	
	public static double calcTotal(ArrayList<Ticket> ticketList) {
		double total=0.0;
		for(Ticket t:ticketList) {
			total=total+t.getTotal();
		}
		return total;
	}
	
	public static Ticket searchID(ArrayList<Ticket> ticketList, int searchID) {
		Ticket tic=null;
		for(Ticket t:ticketList) {
			if(t.getID()==searchID) {
				tic=t;
				break;
			}
		}
		return tic;
	}
	//search tour Name for array list
	public static ArrayList<Ticket> searchTourName(ArrayList<Ticket> ticketList, String searchName) {
		ArrayList<Ticket> tic=new ArrayList<Ticket>();
		for(Ticket t:ticketList) {
			if(t.tour instanceof CityTour) {
				if((((CityTour) t.tour).getName()).equals(searchName)) {
					tic.add(t);
				}				
			}else if(t.tour instanceof Attractions){
				if((((Attractions) t.tour).getName()).equals(searchName)) {
					tic.add(t);
				}
			}else {
				if((((InterstateInternational) t.tour).getName()).equals(searchName)) {
					tic.add(t);
				}
			}
		}
		return tic;
	}
	//search tour name for Ticket
	public static Ticket searchTourName2(ArrayList<Ticket> ticketList, String searchName) {
		Ticket tic=null;
		for(Ticket t:ticketList) {
			if(t.tour instanceof CityTour) {
				if((((CityTour) t.tour).getName()).equals(searchName)) {
					tic=t;
					break;
				}				
			}else if(t.tour instanceof Attractions){
				if((((Attractions) t.tour).getName()).equals(searchName)) {
					tic=t;					
					break;
				}
			}else {
				if((((InterstateInternational) t.tour).getName()).equals(searchName)) {
					tic=t;					
					break;
				}
			}
		}
		return tic;
	}
	//search and show sales executive
	public static void showSEname(ArrayList<Account> accList){
		ArrayList<Account> aList=new ArrayList<Account>();
		Account acc=null;
		for(int i=1; i<accList.size(); i++) {
			aList.add(accList.get(i));
		}
		for(Account a:aList) {
			acc=a;
			System.out.println(acc.getSEName());
		}
	}
	//check if user input SEname
	public static boolean checkUserInputSEname(ArrayList<Account> accList, String searchName) {
		boolean isFound=false;
		for(Account a:accList) {
			if(a.getSEName().equals(searchName)) {
				isFound=true;
				break;
			}
		}return isFound;
	}
	//search tickets issued by sales executive
	public static ArrayList<Ticket> searchSEticket(ArrayList<Ticket> ticketList, String searchName) {
		ArrayList<Ticket> tList=new ArrayList<Ticket>();
		for(Ticket t:ticketList) {
			if(t.account.getSEName().equals(searchName)) {
				tList.add(t);
				
			}
		}return tList;
	}
}