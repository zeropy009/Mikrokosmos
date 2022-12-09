/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mikrokosmos.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Admin
 */

@Entity
public class BookTableDTO {
    
    @Id
    @Column(name="bookid")
    private String bookID;
    
    @Column(name="booktitle")
    private String bookTitle;
    
    @Column(name="price")
    private double price;
    
    @Column(name="amount")
    private int amount;

    public String getBookID() {
        return bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
    
    
}
