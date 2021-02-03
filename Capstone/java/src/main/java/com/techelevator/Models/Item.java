package com.techelevator.Models;

public class Item {
	// Attributes
	private String slot; // Slot which the item can be accessed from
	private String name;
	private double price;
	private int quantity; // How much is left
	private String type; // Type of vending machine item
	
	// Getters and Setters
	public String getSlot() {
		return slot;
	}
	
	public void setSlot(String slot) {
		this.slot = slot;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	// Constructor
	public Item(String slot,String name, double price, String type) {
		this.slot = slot;
		this.name = name;
		this.price = price;
		this.type = type;
		this.quantity = 5;
	}
	
	/*
	 * Returns a string based on the type of item
	 * All chip items will print “Crunch Crunch, Yum!”
	 * All candy items will print “Munch Munch, Yum!”
	 * All drink items will print “Glug Glug, Yum!”
	 * All gum items will print “Chew Chew, Yum!”
	 */
	public String getPurchaseMessage() {
		if (this.type.equals("Chip")) {
			return "Crunch Crunch, Yum!";
		} else if (this.type.equals("Candy")) {
			return "Munch Munch, Yum!";
		} else if (this.type.equals("Drink")) {
			return "Glug Glug, Yum!";
		} else if (this.type.equals("Gum")) {
			return "Chew Chew, Yum!";
		}
		
		return "";
	}
}
