package bank;

public class InterestTask implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("\n*******************INTEREST THREAD HAS STARTED*******************************");
			BankSystem.updateAfterInterests();
			System.out.println("*********************ALL CLIENTS AFTER UPDATE********************************\n");
			BankSystem.printAll();
			System.out.println("\n**********INTEREST THREAD ENDED AND WILL START AGAIN IN 24 HOURS*************");
			BankSystem.showMenuString();
		}
	}

}
