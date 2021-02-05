package com.techelevator;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.Models.Item;
import com.techelevator.Models.VendingMachine;
import com.techelevator.view.Menu;

public class PurchaseMenu {
	// Purchase menu items
    // (1) Feed Money
	// (2) Select Product
	// (3) Finish Transaction
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {
															PURCHASE_MENU_OPTION_FEED_MONEY,
															PURCHASE_MENU_OPTION_SELECT_PRODUCT,
															PURCHASE_MENU_OPTION_FINISH_TRANSACTION
														};
	
	
	private Menu vendingMenu;
	private VendingMachine vendingMachine;
	private DecimalFormat formatter;
	private Scanner scanner;
	
	public PurchaseMenu(Menu vendingMenu, VendingMachine vendingMachine, DecimalFormat formatter) {
		this.vendingMenu = vendingMenu;
		this.vendingMachine = vendingMachine;
		this.formatter = formatter;
		this.scanner = new Scanner(System.in);
	}
	
	public void run() {
		System.out.println("Purchase Menu");
		
		boolean shouldProcess = true;
		
		while (shouldProcess) {
			String choice = (String)vendingMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
			
			switch(choice) {
				case PURCHASE_MENU_OPTION_FEED_MONEY:
					feedMoney();
					break;
				
				case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
					selectProduct();
					break;
				case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
					finishTransaction();
					shouldProcess = false;
					break;
			}
		}
		
		return;
	}
	
	public void feedMoney() {
		boolean shouldProcess = true;
		
		while (shouldProcess) {
			System.out.println("Add money or press Q to go back. Can only be $1, $2, $5 or $10.");
			
			String userInput = this.scanner.nextLine();
			
			if (userInput.equalsIgnoreCase("Q")) {
				shouldProcess = false;
				break;
			} else {
				try {
					String message = vendingMachine.feedMoney(Double.parseDouble(userInput));
					System.out.println();
					System.out.println(message);
					System.out.println();
					System.out.println("Current total: $" + formatter.format(vendingMachine.getBalance()));
				} catch (NumberFormatException e) {
					System.out.println("Must be a number.");
					System.out.println();
				}
			}
		}
		
		return;
	}
	
	public void selectProduct() {
		boolean shouldProcess = true;
		
		while(shouldProcess) {
			this.displayItems();
			System.out.println("Your current balance: $" + formatter.format(this.vendingMachine.getBalance()));
			System.out.println("Select a item using its slot number or press Q to go back.");
			System.out.println();
			String userInput = this.scanner.nextLine();
			
			if (userInput.equalsIgnoreCase("Q")) {
				shouldProcess = false;
				break;
			} else {
				String message = this.vendingMachine.purchaseItem(userInput);
				System.out.println();
				System.out.println(message);
			}
		}
		
		return;
	}
	
	public void finishTransaction() {
		
	}
	
	private void displayItems() {      // static attribute used as method is not associated with specific object instance
		// Code to display items in Vending Machine
		// TODO: Create easy to read list.
		System.out.println("-------------------------------------------------------------");
		System.out.printf("%-2s %-2s %-20s %-1s %-5s %-2s %-7s %-2s %-2s %-2s\n", 
				"Slot", "|", "Name", "|", "Price", "|", "Type", "|", "Quantity", "|");
		System.out.println("-------------------------------------------------------------");
		for (Map.Entry<String, Item> item : this.vendingMachine.getItems().entrySet()) {
			System.out.printf("%-4s %-2s %-20s %-1s %-5s %-2s %-7s %-2s %-8s %-2s\n", 
					item.getKey(), "|", item.getValue().getName(), "|", "$" 
			+ formatter.format(item.getValue().getPrice()), "|", item.getValue().getType(), "|", 
			(item.getValue().getQuantity() == 0) ? "Sold Out" :item.getValue().getQuantity(), "|");
		}
		System.out.println("-------------------------------------------------------------");
	}
}
