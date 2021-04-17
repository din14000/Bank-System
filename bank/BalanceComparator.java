package bank;

import java.util.Comparator;

import people.Client;

public class BalanceComparator implements Comparator<Client> {

	@Override
	public int compare(Client o1, Client o2) {
		if (o1.getId()==o2.getId())
			return 0;
		else if (o1.getAccount().getBalance()>o2.getAccount().getBalance())
			return -1;
		return 1;
	}

}
