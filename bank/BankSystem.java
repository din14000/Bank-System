package bank;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import exceptions.deleteException;
import exceptions.noSuchClientException;
import exceptions.withdrawException;
import people.Client;
import people.RegularClient;
import people.VipClient;

public class BankSystem extends Thread{
	/*************************************** ATTRIBUTES ***********************************************/

	private static BankSystem instance = null;
	private static Set<Client> clients = null;
	private static Scanner scanner = null;
	private static InterestTask task = null;
	private static Thread thread = null;
	/*************************************** CTOR ***********************************************/

	public BankSystem() {
		scanner = new Scanner(System.in);
		clients = new TreeSet<>((client1, client2) -> (((Client) client1).getId() - ((Client) client2).getId()));
		task = new InterestTask();
        thread = new Thread(task);

	}
	/*************************************** REQUESTED METHODS ***********************************************/

	public static BankSystem getInstance() {
		if (instance == null) {
			synchronized (BankSystem.class) {
				if (instance == null) {
					instance = new BankSystem();
				}
			}
		}
		return instance;
	}

	public static void showMenu() {
		int choice = 0;
		while (true) {
			System.out.println("\nBank System Main Menu\n");
			System.out.print("1.) Add a client \n");
			System.out.print("2.) Delete a client.\n");
			System.out.print("3.) Withdraw money for a client.\n");
			System.out.print("4.) Deposit money for a client.\n");
			System.out.print("5.) Print all clients from richest to poorest.\n");
			System.out.print("6.) Statistics - Get amount of clients\n");
			System.out.print("7.) Statistics - Get total bank balance\n");
			System.out.print("8.) Statistics - Get the richest client\n");
			System.out.print("9.) Statistics - Get the poorest client\n");
			System.out.print("0.) Exit\n");

			System.out.print("\nEnter Your Choice: ");

			try {
				choice = scanner.nextInt();
			} catch (Exception e) {
				System.err.println(e.getMessage());
}

			switch (choice) {
			case 1:
				addClient(choice);
				break;

			case 2:
				System.out.println("Enter the ID number of the client you want to delete");
				System.out.println("Or enter 0 to go back to main menu");
				int input = scanner.nextInt();
				if (input == 0)
					backToMenu();
				try {
					deleteClient(input);
				} catch (deleteException|noSuchClientException e) {
					System.err.println("Exception thrown  :" + e);
				}
				break;

			case 3:
				System.out.println("Enter the ID number of the client you would like to withdraw money for");
				System.out.println("Or enter 0 to go back to main menu");
				int id = scanner.nextInt();
				if (id == 0)
					backToMenu();
				System.out.println("Enter the amount of money you want to withdraw");
				double amount = scanner.nextDouble();
				try {
					withdraw(id, amount);
				} catch (withdrawException|noSuchClientException e) {
					System.err.println("Exception thrown  :" + e);
				}
				break;

			case 4:
				System.out.println("Enter the ID number of the client you would like to deposit money for");
				System.out.println("Or enter 0 to go back to main menu");
				id = scanner.nextInt();
				if (id == 0)
					backToMenu();
				System.out.println("Enter the amount of money you want to deposit");
				amount = scanner.nextDouble();
				try {
					deposite(id, amount);
				} catch (noSuchClientException e) {
					System.err.println("Exception thrown  :" + e);
				}
				break;
			case 5:
				clearConsole();
				System.out.println("Printing all the clients of the bank");
				printAll();
				break;
			case 6:
				System.out.println("The bank currently has " + BankStatistics.countMembers() + " clients");
				break;
			case 7:
				System.out.println("The bank's total account balance is: "+BankStatistics.getBankBalance() + "$");
				break;
			case 8:
				System.out.println("The richest client currently is: " + BankStatistics.getRichestClient());
				break;
			case 9:
				System.out.println("The poorest client currently is: " + BankStatistics.getPoorestClient());
				break;
			case 0:
				endSystem();
				break;

			default:
				System.out.println("This is not a valid Menu Option! Please Select Another");
				backToMenu();
				break;
			}
		}

	}

	public static void startSystem() {
		System.out.println("Bank System is booting...\n");
		try {
			BankSystem.sleep(1500);
		} catch (InterruptedException e) {};
		System.out.println(bankBanner);
		BankSystem.getInstance();
		thread.start();
		showMenu();
	}

	public static void endSystem() {
		System.out.println("Exiting Program...");
		thread.interrupt();
		scanner.close();
		System.exit(0);
	}

	public static void addClient(Client client) {
		clients.add(client);
		System.out.println("Client #" + client.getId() + "Added!");
	}

	public static void deleteClient(int id) throws deleteException, noSuchClientException {
		Client client = null;
		if( clients.stream().anyMatch(c->c.getId()==id) ) 
			client = (Client) clients.toArray()[id - 1];
		else
			throw new noSuchClientException("", new Date(), id);
		if (client.getAccount().getBalance() < 0)
			throw new deleteException("", new Date(), client.getId(), client.getName(),
					client.getAccount().getBalance());
		clients.remove(client);
		System.out.println("Client #" + client.getId() + "Removed!");
	}

	public static void withdraw(int id, double amount) throws withdrawException, noSuchClientException {
		Client client = null;
		if( clients.stream().anyMatch(c->c.getId()==id) ) 
			client = (Client) clients.toArray()[id - 1];
		else
			throw new noSuchClientException("", new Date(), id);
		double balance = client.getAccount().getBalance();
		if (balance < amount)
			throw new withdrawException("", new Date(), client.getId(), client.getName(), balance);
		client.getAccount().setBalance(balance - amount);
		System.out.println(amount+ "$ withdrew from Client #" + id + "'s account");
	}

	public static void deposite(int id, double amount) throws noSuchClientException {
		Client client = null;
		if( clients.stream().anyMatch(c->c.getId()==id) ) 
			client = (Client) clients.toArray()[id - 1];
		else
			throw new noSuchClientException("", new Date(), id);
		double balance = client.getAccount().getBalance();
		client.getAccount().setBalance(balance + amount);
		System.out.println(amount+ "$ deposited into Client #" + id + "'s account");
	}

	public static void printAll() {
		Set<Client> sortedClients = new TreeSet<>(new BalanceComparator());
		sortedClients.addAll(clients);
		printClientSet(sortedClients);
	}
	
	/*************************************** HELPER METHODS ***********************************************/

	private static void backToMenu() {
		System.out.println("Going back to main menu");
		clearConsole();
		showMenu();
	}
	
	 static void showMenuString() {
		System.out.println("\nBank System Main Menu\n");
		System.out.print("1.) Add a client \n");
		System.out.print("2.) Delete a client.\n");
		System.out.print("3.) Withdraw money for a client.\n");
		System.out.print("4.) Deposit money for a client.\n");
		System.out.print("5.) Print all clients.\n");
		System.out.print("6.) Get amount of clients\n");
		System.out.print("7.) Get total bank balance\n");
		System.out.print("8.) Get the richest client\n");
		System.out.print("9.) Get the poorest client\n");
		System.out.print("0.) Exit\n");

		System.out.print("\nEnter Your Choice: ");
	}

	private static void clearConsole() { // only works with IntelliJ - otherwise prints a "?" (>.<)
										//  found no way of clearing the console while in runtime for Eclipse
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {
		}
	}

	private static void addClient(int choice) {
		Client client = null;
		System.out.println("Please enter the type of client you want to add");
		System.out.println("Enter 1 for a regualr client or 2 for a VIP client");
		System.out.println("Or enter any other number to go back to main menu");

		int input = scanner.nextInt();
		if (input != 1 && input != 2) {
			backToMenu();
		} else if (input == 1)
			client = new RegularClient();
		else
			client = new VipClient();
		addClient(client);
	}

	private static void printClientSet(Set<Client> set) {
		for (Client client : set) {
			System.out.println(client);
		}
	}

	static void updateAfterInterests() {
		clients.forEach(client -> ((Client) client).getAccount()
				.setBalance(((Client) client).getAccount().getBalance() * (1+((Client) client).getInterestRate())));
	}

	public static Set<Client> getClients() {
		return clients;
	}
	public static final String bankBanner = " ▄▄▄▄    ▄▄▄       ███▄    █  ██ ▄█▀     ██████▓██   ██▓  ██████ ▄▄▄█████▓▓█████  ███▄ ▄███▓\r\n"
			+ "▓█████▄ ▒████▄     ██ ▀█   █  ██▄█▒    ▒██    ▒ ▒██  ██▒▒██    ▒ ▓  ██▒ ▓▒▓█   ▀ ▓██▒▀█▀ ██▒\r\n"
			+ "▒██▒ ▄██▒██  ▀█▄  ▓██  ▀█ ██▒▓███▄░    ░ ▓██▄    ▒██ ██░░ ▓██▄   ▒ ▓██░ ▒░▒███   ▓██    ▓██░\r\n"
			+ "▒██░█▀  ░██▄▄▄▄██ ▓██▒  ▐▌██▒▓██ █▄      ▒   ██▒ ░ ▐██▓░  ▒   ██▒░ ▓██▓ ░ ▒▓█  ▄ ▒██    ▒██ \r\n"
			+ "░▓█  ▀█▓ ▓█   ▓██▒▒██░   ▓██░▒██▒ █▄   ▒██████▒▒ ░ ██▒▓░▒██████▒▒  ▒██▒ ░ ░▒████▒▒██▒   ░██▒\r\n"
			+ "░▒▓███▀▒ ▒▒   ▓▒█░░ ▒░   ▒ ▒ ▒ ▒▒ ▓▒   ▒ ▒▓▒ ▒ ░  ██▒▒▒ ▒ ▒▓▒ ▒ ░  ▒ ░░   ░░ ▒░ ░░ ▒░   ░  ░\r\n"
			+ "▒░▒   ░   ▒   ▒▒ ░░ ░░   ░ ▒░░ ░▒ ▒░   ░ ░▒  ░ ░▓██ ░▒░ ░ ░▒  ░ ░    ░     ░ ░  ░░  ░      ░\r\n"
			+ " ░    ░   ░   ▒      ░   ░ ░ ░ ░░ ░    ░  ░  ░  ▒ ▒ ░░  ░  ░  ░    ░         ░   ░      ░   \r\n"
			+ " ░            ░  ░         ░ ░  ░            ░  ░ ░           ░              ░  ░       ░   \r\n"
			+ "      ░                                         ░ ░                                         ";
}
