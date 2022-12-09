package Mikrokosmos.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zero
 */
@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "invoiceid")
    private String invoiceID;

    @Column(name = "soldate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date solDate;

    @Column(name = "customerid")
    private String customerID;

    @Column(name = "staffid")
    private String staffID;

    @Column(name = "discount")
    private double discount;

    @Column(name = "shipstatus")
    private String shipStatus;

    @Column(name = "paystatus")
    private String payStatus;

    @Column(name = "methodid")
    private Integer methodID;

    public Invoice() {
        super();
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Integer getMethodID() {
        return methodID;
    }

    public void setMethodID(Integer methodID) {
        this.methodID = methodID;
    }

    

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Date getSolDate() {
        return solDate;
    }

    public void setSolDate(Date solDate) {
        this.solDate = solDate;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
