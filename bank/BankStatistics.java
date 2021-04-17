package bank;

import java.util.Set;

import people.Client;

public class BankStatistics {
	private static double sum;
	static Set<Client> clients = BankSystem.getClients();

	public static int countMembers() {
		return BankSystem.getClients().size();
	}

	public static double getBankBalance() {
		sum = 0;
		clients.forEach(client -> sum += client.getAccount().getBalance());
		return sum;
	}

	public static Client getRichestClient() {
		if (clients.size() > 0)
			return clients.stream().min(new BalanceComparator()).get();
		return null;

	}

	public static Client getPoorestClient() {
		if (clients.size() > 0)
			return clients.stream().max(new BalanceComparator()).get();
		return null;
	}
}
