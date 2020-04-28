package gymPrograme;

public class Clients {

    private String name;
    private String idCard;
    private int age;
    private String phoneNumber;
    private DateTime paymentDate;

    public Clients(String name, String idCard, String phoneNumber, int age, DateTime date) {
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

    public boolean needsToPay() {
        return paymentDate.getNeedsTopay();
    }

    //TODO change the implementation  of hashcode and equals
    @Override
    public int hashCode() {
        return this.getIdCard().hashCode() + 57;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || (obj.getClass() != this.getClass())) {
            return false;
        }
        String objID = ((Clients) obj).getIdCard();
        return this.getIdCard().equals(objID);
    }
}
