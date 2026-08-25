//Priscella Maenar 104511548 - COS10033 Assignment 2
//Tour.java
import java.io.*;
import java.util.*;

abstract class Tour implements Comparable<String>, Serializable{
	private String type;
	private int capacity;
	private double aPrice;
	private double cPrice;
	
	//constructor
	public Tour(String type, double aPrice, double cPrice){
		this.aPrice=aPrice;
		this.cPrice=cPrice;
		this.type=type;
		this.type=type;
	}
	
	//get and set method
	public double getaPrice() { return this.aPrice;	}
	public double getcPrice() { return this.cPrice;	}
	public String getType() { return this.type; }
	public int getCapacity() { return this.capacity; }
	public void setCapacity(int capacity) { this.capacity=capacity; }
	
	public String toString() {
		return (" ");
	}
	
	@Override
	public int compareTo(String tourType) {
		if((this.type).equals(tourType)) { return 0; }
		else { return -1; }
	}
}

class CityTour extends Tour{
	private String name;
	//constructor
	public CityTour(String type, double aPrice, double cPrice, String name) {
		super(type, aPrice, cPrice);
		this.name=name;
	}
	
	public String getName() { return this.name; }
	
	public String toString() {
		String temp=super.toString();
		return (temp+"Tour: "+this.name);
	}
}

class Attractions extends Tour{
	private String name;
	//constructor
	public Attractions(String type, double aPrice, double cPrice, String name){
		super(type, aPrice, cPrice);
		this.name=name;
	}
	public String getName() { return this.name; }
	
	public String toString() {
		String temp=super.toString();
		return (temp+"Tour: "+this.name);
	}
}

class InterstateInternational extends Tour{
	private String name;
	//constructor
	public InterstateInternational(String type, double aPrice, double cPrice, String name){
		super(type, aPrice, cPrice);
		this.name=name;
		this.setCapacity(40);
	}
	public String getName() { return this.name; }
	
	public String toString() {
		String temp=super.toString();
		return (temp+"Tour: "+this.name);
	}
}