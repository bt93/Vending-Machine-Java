package com.techelevator;

import java.text.DecimalFormat;

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
															PURCHASE_MENU_OPTION_SELECT_PRODUCT
															};
	
	
	private Menu vendingMenu;
	private VendingMachine vendingMachine;
	private DecimalFormat formatter;
	
	public PurchaseMenu(Menu vendingMenu, VendingMachine vendingMachine, DecimalFormat formatter) {
		this.vendingMenu = vendingMenu;
		this.vendingMachine = vendingMachine;
		this.formatter = formatter;
	}
}
