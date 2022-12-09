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
@Table(name = "historypoints")
public class HistoryPoint implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "historypointid")
	private Integer historyPointID;
	
	@Column(name = "customerid")
	private String customerID;
	
	@Column(name = "date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	
	@Column(name = "point")
	private Integer point;
	
	public HistoryPoint() {
		super();
	}

	public Integer getHistoryPointID() {
		return historyPointID;
	}

	public void setHistoryPointID(Integer historyPointID) {
		this.historyPointID = historyPointID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
}
