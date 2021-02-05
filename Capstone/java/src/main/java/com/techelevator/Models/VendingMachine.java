package com.techelevator.Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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
		this.items = new TreeMap<String, Item>();
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
	
	/*
	 * This method will take in money and add to the current balance
	 * 
	 * Takes $1, $2, $5 or $10
	 */
	public String feedMoney(double amount) throws IOException {
		String message = "";
		
		if (amount == 1) {
			this.balance += amount;
			message = "$1 added.";
			this.appendLog("FEED MONEY", amount);
		} else if (amount == 2) {
			this.balance += amount;
			message = "$2 added.";
			this.appendLog("FEED MONEY", amount);
		} else if (amount == 5) {
			this.balance += amount;
			message = "$5 added.";
			this.appendLog("FEED MONEY", amount);
		} else if (amount == 10) {
			this.balance += amount;
			message = "$10 added.";
			this.appendLog("FEED MONEY", amount);
		} else {
			message = "Only an amount of $1, $2, $5 or $10 can be added to the machine";
		}
		
		return message;
	}
	
	/*
	 * This method will give the user the item (Give Yum message)
	 * Take out from balance and subtract from the stock
	 */
	public String purchaseItem(String slot) throws IOException {
		String message = "";
		
		Item chosenItem = this.items.get(slot.toUpperCase());
		
		if (chosenItem == null) {
			message = "Incorect slot number. Choose from one of the numbers above.";
		} else if (this.balance < chosenItem.getPrice()) {
			message = "Not enough funds to purchase item! Please make sure you have added money.";
		} else if (chosenItem.getQuantity() <= 0) {
			message = "Sorry! " + chosenItem.getName() + " is sold out!";
		} else {
			this.balance -= chosenItem.getPrice();
			chosenItem.setQuantity(chosenItem.getQuantity() - 1);
			message = "You have purchased: " + chosenItem.getName() + ". " + chosenItem.getPurchaseMessage();
			this.appendLog(chosenItem.getName() + " " + chosenItem.getSlot(),  chosenItem.getPrice());
		}
		
		return message;
	}
	
	/*
	 * Ends transaction and gives user back their change
	 */
	public int[] finishTransaction() throws IOException {
		double originalBalance = this.balance;
		double currentBalance = this.balance;
		int[] change;
		
		int numberOfQuarters = (int)(currentBalance / .25);
		currentBalance %= .25;
		
		int numberOfDimes = (int)(currentBalance / .10);
		currentBalance %= .10;
		
		int numberOfNickels = (int)(currentBalance / .05);
		currentBalance %= .05;
		
		this.balance = currentBalance;
		change = new int[] { numberOfQuarters, numberOfDimes, numberOfNickels };
		this.appendLog("GIVE CHANGE", originalBalance);
		return change;
	}
	
	/*
	 * Creates or appends to a log file that give transaction info
	 */
	private void appendLog(String transactionType, double amount) throws IOException {
		DecimalFormat formatter = new DecimalFormat("#.00");
		File log = new File("./Log.txt");
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		PrintWriter printWriter;
		Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());
		
		if (log.exists() && log.isFile()) {
			fileWriter = new FileWriter(log, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			printWriter = new PrintWriter(bufferedWriter);
			printWriter.println(timestampNow + " " + transactionType + ": $" + formatter.format(amount) + " $" + formatter.format(this.balance));
		} else {
			log.createNewFile();
			fileWriter = new FileWriter(log, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			printWriter = new PrintWriter(bufferedWriter);
			printWriter.println(timestampNow + " " + transactionType + ": $" + formatter.format(amount) + " $" + formatter.format(this.balance));
		}
		
		printWriter.close();
	}
	
	/*
	 * Creates a new sales report when the program is ended showing the total sales and the amount 
	 */
	public void salesReport() throws IOException {
		DecimalFormat formatter = new DecimalFormat("#.00");
		Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());
		File newReport = new File("./reports/Sales-Report-" + timestampNow.getYear() + "-" + timestampNow.getMonth() + "-" + timestampNow.getDate() + "_" + 
				timestampNow.getHours() + "-" + timestampNow.getMinutes() +".txt");
		newReport.createNewFile();
		FileWriter fileWriter = new FileWriter(newReport, false);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);
		double totalAmount = 0;
		
		printWriter.println("Sales Report, Date: " + timestampNow);
		printWriter.println();
		
		for (Map.Entry<String, Item> item : this.items.entrySet()) {
			int totalSold = 5 - item.getValue().getQuantity();
			totalAmount += item.getValue().getPrice() * totalSold;
			printWriter.println(item.getValue().getName() + " | " + totalSold);
		}
		
		printWriter.println();
		printWriter.println("Total Sales: " + formatter.format(totalAmount));
		printWriter.close();
	}
}
