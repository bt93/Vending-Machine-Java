package com.techelevator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

import com.techelevator.Models.Item;
import com.techelevator.Models.VendingMachine;
/**************************************************************************************************************************
*  This is your Vending Machine Command Line Interface (CLI) class
*
*  It is the main process for the Vending Machine
*
*  THIS is where most, if not all, of your Vending Machine interactions should be coded
*  
*  It is instantiated and invoked from the VendingMachineApp (main() application)
*  
*  Your code vending machine related code should be placed in here
***************************************************************************************************************************/
import com.techelevator.view.Menu;         // Gain access to Menu class provided for the Capstone

public class VendingMachineCLI {

    // Main menu options defined as constants

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													    MAIN_MENU_OPTION_PURCHASE,
													    MAIN_MENU_OPTION_EXIT
													    };
	private Menu vendingMenu;              // Menu object to be used by an instance of this class
	private PurchaseMenu purchaseMenu;
	private VendingMachine vendingMachine;
	private DecimalFormat formatter = new DecimalFormat("#.00");
	
	public VendingMachineCLI(Menu menu) throws FileNotFoundException {  // Constructor - user will pas a menu for this class to use
		this.vendingMenu = menu;           // Make the Menu the user object passed, our Menu
		
		this.vendingMachine = new VendingMachine();
		this.purchaseMenu = new PurchaseMenu(menu, vendingMachine, formatter);
	}
	/**************************************************************************************************************************
	*  VendingMachineCLI main processing loop
	*  
	*  Display the main menu and process option chosen
	*
	*  It is invoked from the VendingMachineApp program
	*
	*  THIS is where most, if not all, of your Vending Machine objects and interactions 
	*  should be coded
	*
	*  Methods should be defined following run() method and invoked from it
	 * @throws IOException 
	*
	***************************************************************************************************************************/

	public void run() throws IOException {

		this.header();
		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String choice = (String)vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayItems();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseItems();          // invoke method to purchase items from Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_EXIT:
					endMethodProcessing();    // Invoke method to perform end of method processing
					shouldProcess = false;    // Set variable to end loop
					break;                    // Exit switch statement
			}	
		}
		return;                               // End method and return to caller
	}
/********************************************************************************************************
 * Methods used to perform processing
 ********************************************************************************************************/
	public void displayItems() {      // static attribute used as method is not associated with specific object instance
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
	
	public void purchaseItems() throws IOException {	 // static attribute used as method is not associated with specific object instance
		// Code to purchase items from Vending Machine
		this.purchaseMenu.run();
	}
	
	public void endMethodProcessing() throws IOException { // static attribute used as method is not associated with specific object instance
		// Any processing that needs to be done before method ends
		this.vendingMachine.salesReport();
	}
	
	private void header() {
		System.out.println(" _   _                _            ___  ___      _   _        _____ _____  _____ ");
		System.out.println("| | | |              | |           |  \\/  |     | | (_)      |  _  |  _  ||  _  |");
		System.out.println("| | | | ___ _ __   __| | ___ ______| .  . | __ _| |_ _  ___   \\ V /| |/' || |/' |");
		System.out.println("| | | |/ _ \\ '_ \\ / _` |/ _ \\______| |\\/| |/ _` | __| |/ __|  / _ \\|  /| ||  /| |");
		System.out.println("\\ \\_/ /  __/ | | | (_| | (_) |     | |  | | (_| | |_| | (__  | |_| \\ |_/ /\\ |_/ /");
		System.out.println(" \\___/ \\___|_| |_|\\__,_|\\___/      \\_|  |_/\\__,_|\\__|_|\\___| \\_____/\\___/  \\___/ ");
		System.out.println("                                                                                 ");
		System.out.println("______         _   _           _              _ _         _____                  ");
		System.out.println("| ___ \\       | | | |         | |            | | |       /  __ \\                 ");
		System.out.println("| |_/ /_   _  | | | |_ __ ___ | |__  _ __ ___| | | __ _  | /  \\/ ___  _ __ _ __  ");
		System.out.println("| ___ \\ | | | | | | | '_ ` _ \\| '_ \\| '__/ _ \\ | |/ _` | | |    / _ \\| '__| '_ \\ ");
		System.out.println("| |_/ / |_| | | |_| | | | | | | |_) | | |  __/ | | (_| | | \\__/\\ (_) | |  | |_) |");
		System.out.println("\\____/ \\__, |  \\___/|_| |_| |_|_.__/|_|  \\___|_|_|\\__,_|  \\____/\\___/|_|  | .__/ ");
		System.out.println("        __/ |                                                             | |    ");
		System.out.println("       |___/                                                              |_|    ");
	}
}
