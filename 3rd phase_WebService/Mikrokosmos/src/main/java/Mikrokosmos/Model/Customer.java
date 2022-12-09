package Mikrokosmos.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author zero
 */
@Entity
public class Customer implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "customerid")
    private String customerID;

    @Column(name = "customername")
    private String customerName;

    @Column(name = "email")
    private String email;

    @Column(name="customerimage")
    private String customerImage;
    
    @Column(name = "customerpoint")
    private double customerPoint;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    public Customer() {
        super();
    }

    public double getCustomerPoint() {
        return customerPoint;
    }

    public String getCustomerimage() {
        return customerImage;
    }

    public void setCustomerimage(String customerimage) {
        this.customerImage = customerimage;
    }
    
    public void setCustomerPoint(double customerPoint) {
        this.customerPoint = customerPoint;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
