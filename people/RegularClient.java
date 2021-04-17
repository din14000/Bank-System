package people;

public class RegularClient extends Client {
	private static int regId = 1;

	public RegularClient() {
		this.interestRate = 0.05f;
		this.setName(this.getClass().getSimpleName() + regId++);
	}

	@Override
	public String toString() {
		return "ID=" + id + ", Name= " + name + ", Age= " + String.format("%.2f", age) + " years old. Account no."
				+ account.getId() + ", Account balance:" + String.format("%.2f", account.getBalance()) + "$"
				+ ", Interest rate=" + (int) (interestRate * 100) + "%";
	}

}
