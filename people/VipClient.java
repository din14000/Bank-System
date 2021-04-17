package people;

public class VipClient extends Client {

	private static int vipId = 1;

	public VipClient() {
		super();
		this.interestRate = 0.1f;
		this.setName(this.getClass().getSimpleName() + vipId++);

	}

	@Override
	public String toString() {
		return "ID=" + id + ", Name= " + name + ", Age= " + String.format("%.2f", age) + " years old. Account no."
				+ account.getId() + ", Account balance:" + String.format("%.2f", account.getBalance()) + "$"
				+ ", Interest rate=" + (int) (interestRate * 100) + "%";
	}

}