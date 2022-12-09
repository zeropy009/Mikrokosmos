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
@Table(name = "historyprices")
public class HistoryPrice implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "historypriceid")
	private Integer historyPriceID;
	
	@Column(name = "bookid")
	private String bookID;
	
	@Column(name = "startdate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@Column(name = "price")
	private Double price;
	
	public HistoryPrice() {
		super();
	}

	public Integer getHistoryPriceID() {
		return historyPriceID;
	}

	public void setHistoryPrice(Integer historyPriceID) {
		this.historyPriceID = historyPriceID;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
