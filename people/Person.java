package people;

public abstract class Person {
	static int idCounter = 1;
	int id;
	String name;
	double age;

	public Person() {
		id = idCounter++;
		name = getClass().getSimpleName() + id;
		age = (Math.random() * 104.0) + 16.0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
