////Priscella Maenar 104511548 - COS10033 Assignment 2
///*
// */
//import java.util.*;
//
//public class StageB {
//
//	public static void main(String[] args) {
//		Scanner input = new Scanner(System.in);
//		CityTour[] ctour=new CityTour[5];
//		Attractions[] attr=new Attractions[4];
//		InterstateInternational[] inter=new InterstateInternational[5];
//		ArrayList<Ticket> ticList=new ArrayList<Ticket>();
//		Ticket tic=null;
//		String choice="null",tourName="null", pickup="N/A";
//		int choice2=0, adultPax=0, childPax=0, capacity=40, uInput=0;
//		double ticketsTotal=0;
//		boolean bookingSuccessful=true, enoughCapacity=true;
//		do {
//			printMenu();
//			choice=input.nextLine();
//			switch(choice) {
//			case "A":
//				int buyMoreTic=1, index=0, count=0;
//				ticketsTotal=0;
//				while(buyMoreTic==1) {
//					enoughCapacity=true; bookingSuccessful=true;
//					do {
//						printTourAttrMenu();
//						System.out.print("\nWhich tour/attractions do you wish to buy ticket/s for: ");
//						choice2=input.nextInt();
//					}while(choice2<1 || choice2>14);	
//					tourName=tourName(choice2);	//to get tour/attractions name
//					System.out.println("Booking tickets for "+tourName+".");
//					System.out.print("How many adult tickets (Max 40): ");
//					adultPax=input.nextInt();
//					System.out.print("How many child tickets (Max 40): ");
//					childPax=input.nextInt();
//					input.nextLine();
//					if(adultPax>40 || childPax>40 ||(adultPax+childPax)>40) { 
//						System.out.println("Number of Adults and/or Child cannot be more than 40."); 
//						enoughCapacity=false;
//					}
//					
//					//check if the tour still has enough capacity for the no of child/adult the user input.
//					Ticket t=null;
//					t=searchTourName2(ticList, tourName);
//					if(ticList!=null && t!=null) {
//						if(t.tour.getCapacity()<(adultPax+childPax)) {
//							System.out.println("Not enough capacity for the tour selected.");
//							enoughCapacity=false;
//						}else { enoughCapacity=true; }
//					}
//					System.out.println("enough cap: "+enoughCapacity);		//test
//					//if the tour still has enough capacity
//					if(enoughCapacity==true) {
//						if((adultPax==1 && childPax==1) || (adultPax==2 && childPax==1)) {
//							FamilyTic1 temp=new FamilyTic1(adultPax, childPax);
//							tic=(FamilyTic1)temp;
//						}else if((adultPax+childPax)>=4) {
//							FamilyTic2 temp=new FamilyTic2(adultPax, childPax);
//							tic=(FamilyTic2)temp;
//						}else { 
//							NormalTic temp=new NormalTic(adultPax,childPax);
//							tic=(NormalTic)temp;
//						}
//						count++;		//ticket creation count
//						
//						if(choice2>=1 && choice2<=5) {
//							System.out.println("choice2 1-5: "+choice2);	//test
//							index=tourIndex(choice2);
//							if(ctour[index]==null) {
//								ctour[index]=cityTour(tourName, adultPax, childPax);
//								ctour[index].setCapacity(capacity);
//							}
//							ctour[index].setCapacity((ctour[index].getCapacity())-adultPax-childPax);
//							tic.setPickup(pickup);
//							tic.addTour(ctour[index]);			
//	//						System.out.println(tic.tour);		//test
//	//						System.out.println(ctour[index]);	//test
//						}else if(choice2>=6 && choice2<=9){
//							System.out.println("choice2 6-9: "+choice2);	//test
//							int pickupChoice=0;
//							do { 
//								System.out.print(" 1-Travel Australia CBD Office\n 2-Flinders Station\n 3-Southern Cross Station\n 4-Queen Victoria Market\n 5-Melbourne Museum\nChoose a PickUp location: ");
//								pickupChoice=input.nextInt();
//								pickup=pickupLoc(pickupChoice);
//							}while(!(pickupChoice>0 && pickupChoice<6));
//							index=tourIndex(choice2);
//							if(attr[index]==null) {
//								attr[index]=attr(tourName, adultPax, childPax);
//								attr[index].setCapacity(capacity);
//							}
//							attr[index].setCapacity((attr[index].getCapacity())-adultPax-childPax);
//	//						System.out.println(attr[index]);	//test
//	//						System.out.println(pickup);			//test
//							tic.setPickup(pickup);
//							tic.addTour(attr[index]);
//						}else if(choice2>=10 && choice2<=14) {								//for choice2>=10 && choice2<=14
//							System.out.println("choice2 10-14: "+choice2);	//test
//							double pickupCost=150.0;
//							index=tourIndex(choice2);
//							if(inter[index]==null) {
//								inter[index]=inter(tourName, adultPax, childPax);
//							}
//							System.out.print("Enter boarding airport: ");
//							pickup=input.nextLine();
//							if(adultPax+childPax>6) { 
//								System.out.print("Number of guests more than 6. Enter airport Pickup & Drop Off cost: $");
//								pickupCost=input.nextDouble(); 
//							}
//							System.out.println("Enter 1 to Add airport Pickup & Drop Off at the destination for $"+pickupCost+" OR any other number to skip.");
//							uInput=input.nextInt();
//							input.nextLine();
//							if(uInput!=1) { pickupCost=0.0; }
////								if(inter[index].compareTo("International")==0) {  bookingSuccessful=bookingFailed(); count--;}
//								
//							
//							tic.setPickup(pickup);
//							tic.setPickupCost(pickupCost);
//							System.out.print("For interstate/international tour, enter Customer Name and Contact.\n Name: ");
//							String name=input.nextLine();
//							System.out.print("Contact No.: ");
//							String contactNo=input.nextLine();
//							tic.setCustNameAndContact(name, contactNo);
//							tic.addTour(inter[index]);
//						}
//						if(bookingSuccessful!=false) {
//							tic.setTotal(tic.calcTotal());
//							tic.setDisc(tic.getTotal());
//							ticketsTotal=ticketsTotal+tic.getTotal();
//							System.out.println("cap: "+tic.tour.getCapacity());	//test
//							ticList.add(tic);
//						}
//					}
//					
//					System.out.println("\nEnter 1 to reserve another tour/attraction ticket to this customer\nOR press any other number if you do not wish to reserve more ticket.");
//					System.out.print("Enter your choice: ");
//					buyMoreTic=input.nextInt();
//					if(buyMoreTic!=1) {
//						break; 
//					}
//				}
//				for(int i=ticList.size()-count; i<ticList.size(); i++) {
//					System.out.println();
//					System.out.println(ticList.get(i));	
//				}
////				ticketsTotal=calcTotal(ticList);
//				System.out.println("\nTotal($) --- : "+ticketsTotal);
////				System.out.println(ticList);		//test
//				input.nextLine();
//				break;
//			case "B":
//				System.out.print("Enter Ticket ID to search: ");
//				int searchID=input.nextInt();
//				Ticket t=searchID(ticList, searchID);
//				if(t==null){ System.out.println("Ticket ID not found."); }
//				else { System.out.println(t); }
//				input.nextLine();
//				break;
//			case "C":
//				double refund=0;
//				uInput=0;
//				System.out.print("Enter Ticket ID for refund: ");
//				searchID=input.nextInt();
//				t=searchID(ticList, searchID);
//				if(t==null){ System.out.println("Ticket ID not found."); }
//				else { 
//					System.out.println(t);
//					System.out.println("\nCancelling this ticket will refund you 75% of the ticket price\nEnter 1 to proceed with the cancellation OR any other number to abort.");
//					uInput=input.nextInt();
//					if(uInput!=1) {System.out.println("Refund aborted."); break;}
//					refund=t.refund();
//					int newCap=t.tour.getCapacity()+t.getAdultPax()+t.getChildPax();
//					t.tour.setCapacity(newCap);
//					t.setStatus("Cancelled");
//					System.out.println("Ticket cancelled. $"+refund+" has been refunded to you.");
////					System.out.println(ticList);	//test
//				}
//				input.nextLine();
//				break;
//			case "D":
//				printAvailability(ctour, attr);
//				break;
//			case "E":
//				if(ticList.isEmpty()) { System.out.println("\nNo tickets have been reserved."); break;}
//				System.out.println("Reserved Tickets:");
//				for(int i=0; i<ticList.size(); i++) {		
//					System.out.println();
//					System.out.println(ticList.get(i));
//				}
//				ticketsTotal=calcTotal(ticList);
//				System.out.println("\nTotal($) --- : "+ticketsTotal);
//				break;
//			case "F":
//				printTourAttrMenu();
//				System.out.print("\nSelect the tour number: ");
//				int tourNum=input.nextInt();
//				tourName=tourName(tourNum);
//				ArrayList<Ticket> tList=new ArrayList<Ticket>();
//				tList=searchTourName(ticList, tourName);
//				if(tList.isEmpty()) { System.out.println("\nNo tickets reserved for this tour."); break; }
//				for(int i=0; i<tList.size(); i++) {
//					System.out.println();
//					System.out.println(tList.get(i));					
//				}ticketsTotal=calcTotal(tList);
//				System.out.println("\nTotal($) --- : "+ticketsTotal);
//				input.nextLine();
//				break;
//			case "X":
//				System.out.println("Exiting...");
//				break;
//			default:
//				System.out.println("Invalid user input. Please enter A-F or X.");
//				break;
//			}
//			
//		}while(!(choice.equals("X")));
//	}
//	
//	//methods
//	public static void printMenu() {	//main menu
//		System.out.println("\nTravel Australia Ticket Reservation.");
//		System.out.println(" A-Reserve Ticket\n B-Search Ticket\n C-Refund Ticket\n D-List Availability\n E-List Sales\n F-List Sales for a Tour\n X-Exit");
//		System.out.print("Enter your choice: ");
//	}
//	public static void printAMenu() {	//menu for choice A
//		System.out.println("\nWhich Type of tour do you wish to book\n A-City Tour\n B-Attractions");
//		System.out.print("Enter your choice: ");
//	}
//	public static void printTourAttrMenu() {	//tour & attraction menu
//		System.out.println("\nCity Tour\t\t\t\t\t|Attractions");
//		System.out.println(" 1-Melbourne City\t\t\t\t| 6-Great Ocean Road\n 2-Melbourne City + Yarra River Boat Cruise\t| 7-Yarra Valley Wine Tasting\n 3-Yarra River Cruise + Melbourne Zoo\t\t| 8-Wilson Prom\n 4-Melbourne City + Melbourne Zoo\t\t| 9-Phillip Island + Penguin Parade\n 5-Melbourne City + Melbourne Aquarium\t\t|");
//		System.out.println("\t\t\t\t\t\t|\nQueensland Tour \t\t\t\t|International Tour");
//		System.out.println(" 10-Cairns\t\t\t\t\t| 12-Phuket\n 11-Cairns\t\t\t\t\t| 13-Pattaya\n\t\t\t\t\t\t| 14-Singapore");
//	}
//	public static boolean bookingFailed() {
//		System.out.println("Ticket Reservation failed.");
//		return false;
//	}
//	public static String tourName(int choice) {
//		String name="null";
//		switch(choice) {
//		case 1:
//			name="Melbourne City"; break;
//		case 2:
//			name="Melbourne City + Yarra River Boat Cruise"; break;
//		case 3:
//			name="Yarra River Cruise + Melbourne Zoo"; break;
//		case 4:
//			name="Melbourne City + Melbourne Zoo"; break;
//		case 5:
//			name="Melbourne City + Melbourne Aquarium"; break;
//		case 6:
//			name="Great Ocean Road"; break;
//		case 7:
//			name="Yarra Valley Wine Tasting"; break;
//		case 8:
//			name="Wilson Prom"; break;
//		case 9:
//			name="Phillip Island + Penguin Parade"; break;
//		case 10:
//			name="Cairns"; break;
//		case 11: 
//			name="Gold Coast"; break;
//		case 12:
//			name="Phuket"; break;
//		case 13:
//			name="Pattaya"; break;
//		case 14:
//			name="Singapore"; break;
//		default:
//			System.out.println("Invalid user input. Please enter 1-14");
//			break;
//		}
//		return name;
//	}
//	public static String pickupLoc(int choice) {
//		String pickup="null";
//		switch(choice) {
//		case 1: pickup="Travel Australia CBD Office"; break;
//		case 2: pickup="Flinders Station"; break;
//		case 3: pickup="Southern Cross Station"; break;
//		case 4: pickup="Queen Victoria Market"; break;
//		case 5: pickup="Melbourne Museum"; break;
//		default: System.out.println("Invalid user input. Please enter 1-5\n"); break;
//		} return pickup;
//	}
//	public static int tourIndex(int choice) {
//		if(choice==1 || choice==6 || choice==10) {return 0;}
//		if(choice==2 || choice==7 || choice==11) {return 1;}
//		if(choice==3 || choice==8 || choice==12) {return 2;}
//		if(choice==4 || choice==9 || choice==13) {return 3;}
//		else {return 4;}	//for choice==5, choice==14
//	}
//	public static CityTour cityTour(String tourName,int adultPax, int childPax) {
//		String type="CityTour";
//		CityTour ctour=null;
//		switch(tourName) {
//		case "Melbourne City":
//			ctour=new CityTour(type, 35.00, 25.00, tourName); break;
//		case "Melbourne City + Yarra River Boat Cruise":
//			ctour=new CityTour(type, 65.00, 40.00, tourName); break;
//		case "Yarra River Cruise + Melbourne Zoo":
//			ctour=new CityTour(type, 75.00, 45.00, tourName); break;
//		case "Melbourne City + Melbourne Zoo":
//			ctour=new CityTour(type, 65.00, 40.00, tourName); break;
//		case "Melbourne City + Melbourne Aquarium":
//			ctour=new CityTour(type, 75.00, 45.00, tourName); break;
//		}
//		return ctour;
//	}
//	public static Attractions attr(String tourName,int adultPax, int childPax) {
//		String type="Attractions";
//		Attractions attr=null;
//		switch(tourName) {
//		case "Great Ocean Road":
//			attr=new Attractions(type, 135.00, 90.00, tourName); break;
//		case "Yarra Valley Wine Tasting":
//			attr=new Attractions(type, 85.00, 60.00, tourName); break;
//		case "Wilson Prom":
//			attr=new Attractions(type, 110.00, 90.00, tourName); break;
//		case "Phillip Island + Penguin Parade":
//			attr=new Attractions(type, 160.00, 120.00, tourName); break;	
//		}
//		return attr;
//	}
//	public static InterstateInternational inter(String tourName,int adultPax, int childPax) {
//		String type1="Interstate", type2="International";
//		InterstateInternational inter=null;
//		switch(tourName) {
//		case "Cairns":
//			inter=new InterstateInternational(type1, 850.00, 700.00, tourName); break;
//		case "Gold Coast":
//			inter=new InterstateInternational(type1, 600.00, 450.00, tourName); break;
//		case "Phuket":
//			inter=new InterstateInternational(type2, 1350.00, 1100.00, tourName); break;
//		case "Pattaya":
//			inter=new InterstateInternational(type2, 1400.00, 1150.00, tourName); break;
//		case "Singapore":
//			inter=new InterstateInternational(type2, 1200.00, 1000.00, tourName); break;
//		}
//		return inter;
//	}
//	
//	public static void printAvailability(CityTour[] citytour, Attractions[] attractions) {
//		int capacity=0;
//		System.out.println("Tour Availability:");
//		for(int i=0; i<citytour.length; i++) {
//			if(citytour[i]!=null) { capacity=citytour[i].getCapacity(); }
//			else { capacity=40; }
//			System.out.println("Tour: "+tourName(i+1)+"\n  Capacity: "+capacity);
//		}
//		for(int j=5; j<(5+attractions.length); j++) {
//			if(attractions[j-5]!=null) { capacity=attractions[j-5].getCapacity(); }
//			else { capacity=40; }
//				System.out.println("Tour: "+tourName(j+1)+"\n  Capacity: "+capacity);
//		}
//	}
//	
//	public static double calcTotal(ArrayList<Ticket> ticketList) {
//		double total=0.0;
//		for(Ticket t:ticketList) {
//			total=total+t.getTotal();
//		}
//		return total;
//	}
//	
//	public static Ticket searchID(ArrayList<Ticket> ticketList, int searchID) {
//		Ticket tic=null;
//		for(Ticket t:ticketList) {
//			if(t.getID()==searchID) {
//				tic=t;
//				break;
//			}
//		}
//		return tic;
//	}
//	//search tour Name for array list
//	public static ArrayList<Ticket> searchTourName(ArrayList<Ticket> ticketList, String searchName) {
//		ArrayList<Ticket> tic=new ArrayList<Ticket>();
//		for(Ticket t:ticketList) {
//			if(t.tour instanceof CityTour) {
//				if((((CityTour) t.tour).getName()).equals(searchName)) {
//					tic.add(t);
//				}				
//			}else if(t.tour instanceof Attractions){
//				if((((Attractions) t.tour).getName()).equals(searchName)) {
//					tic.add(t);
//				}
//			}else {
//				if((((InterstateInternational) t.tour).getName()).equals(searchName)) {
//					tic.add(t);
//				}
//			}
//		}
//		return tic;
//	}
//	//search tour name for Ticket
//	public static Ticket searchTourName2(ArrayList<Ticket> ticketList, String searchName) {
//		Ticket tic=null;
//		for(Ticket t:ticketList) {
//			if(t.tour instanceof CityTour) {
//				if((((CityTour) t.tour).getName()).equals(searchName)) {
//					tic=t;
//					break;
//				}				
//			}else if(t.tour instanceof Attractions){
//				if((((Attractions) t.tour).getName()).equals(searchName)) {
//					tic=t;					
//					break;
//				}
//			}else {
//				if((((InterstateInternational) t.tour).getName()).equals(searchName)) {
//					tic=t;					
//					break;
//				}
//			}
//		}
//		return tic;
//	}
//}