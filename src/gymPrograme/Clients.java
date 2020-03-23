package gymPrograme;

public class Clients {
	private String name;
	private String idCard;
	private int age;
	private String phoneNumber;
	private DateTime paymentDate;

	public Clients(String name, String idCard,String phoneNumber, int age, DateTime date) {
		super();
		this.name = name;
		this.idCard = idCard;
		this.age = age;
		this.paymentDate = date;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public String getIdCard() {
		return idCard;
	}

	public int getAge() {
		return age;
	}

	public DateTime getPaymentDate() {
		return paymentDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setAge(int age) {
		this.age = age;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	

}
