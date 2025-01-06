package Model;

public class Contact {
    private int id;
    private String contactName;
    private String contactPhoneNumber;
    private String contactAddedAt;

    public Contact() {
    }
    public Contact(int id, String contactName, String contactPhoneNumber) {
        this.id = id;
        this.contactName = contactName;
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public Contact(String contactName, String contactPhoneNumber) {
        this.contactName = contactName;
        this.contactPhoneNumber = contactPhoneNumber;
    }
    public Contact(String contactName, String contactPhoneNumber, String contactAddedAt) {
        this.contactName = contactName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactAddedAt = contactAddedAt;
    }
    public Contact(int id,String contactName, String contactPhoneNumber, String contactAddedAt) {
        this.id = id;
        this.contactName = contactName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactAddedAt = contactAddedAt;
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

    public String getContactAddedAt() {
        return contactAddedAt;
    }

    public void setContactAddedAt(String contactAddedAt) {
        this.contactAddedAt = contactAddedAt;
    }
}
