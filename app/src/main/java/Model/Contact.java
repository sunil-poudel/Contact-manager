package Model;

public class Contact {
    private int id;
    private String contactName;
    private String contactPhoneNumber;

    public Contact() {
    }
    public Contact(int id, String contactName, String contactPhoneNumber) {
        this.id = id;
        this.contactName = contactName;
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public Contact(String contactPhoneNumber, String contactName) {
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactName = contactName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }
}
