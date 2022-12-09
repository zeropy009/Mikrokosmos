package Mikrokosmos.Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author zero
 */
@Entity
public class ShipInfor implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "shipinforid")
    private Integer shipInforID;

    @Column(name = "invoiceid")
    private String invoiceID;

    @Column(name = "fullname")
    private String fullName;
    
    @Column(name="shipdate")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String shipDate;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    public ShipInfor() {
        super();
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }
    
    

    public Integer getShipInforID() {
        return shipInforID;
    }

    public void setShipInforID(Integer shipInforID) {
        this.shipInforID = shipInforID;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
}
