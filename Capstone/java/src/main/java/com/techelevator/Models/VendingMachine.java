package com.techelevator.Models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
	public VendingMachine() throws FileNotFoundException {
		this.balance = 0;
		this.items = new HashMap<String, Item>();
		// method that adds items to the map on program execution
		run();
	}
	
	// Methods
	
	/*
	 * This method will run on program execution
	 * 
	 * It will fill the items Map with all the item objects
	 */
	private void run() throws FileNotFoundException {
		// Initialize a file object and give it the path
		File file = new File("./vendingmachine.csv");
		
		// Create a scanner object and loop through each line
		try (Scanner fileReader = new Scanner(file)) {
			while (fileReader.hasNext()) {
				// Initialize Variables
				String line = fileReader.nextLine();
				// Within the loop, split by pipe |
				String[] data = line.split("\\|");
				Item newItem;
				// Create variables that are  to each part of the line
				String slot = data[0];
				String name = data[1];
				double price = Double.parseDouble(data[2]);
				String type = data[3];
				// Create a item object and pass in data to constructor
				newItem = new Item(slot, name, price, type);
				// Add item to the items Map
				this.items.put(slot, newItem);
			}
		} catch (Exception e) {
			System.out.println("Uh oh, something went wrong!");
			System.out.println(e.getMessage());
		}
	}
}
