package com.techelevator.Models;

import java.util.Map;

public class VendingMachine {
	// Attributes
	private Map<String, Item> items; // All the items, the string is the slot for the specific item.
	private double balance; // Holds the current balance for the current run through
	
	// Getters and setters
	public Map<String, Item> getItems() {
		return items;
	}
	
	public void setItems(Map<String, Item> items) {
		this.items = items;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	// Constructor
	public VendingMachine() {
		this.balance = 0;
		
		// TODO: Create a method that adds items to the map on program execution
	}
}
