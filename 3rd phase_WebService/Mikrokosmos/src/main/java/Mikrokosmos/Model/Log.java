package Mikrokosmos.Model;

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
@Table(name = "logs")
public class Log implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "logid")
	private Integer logID;
	
	@Column(name = "logname")
	private String logName;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "referenceid")
	private String referenceID;
	
	public Log() {
		super();
	}
	
	public Integer getLogID() {
		return logID;
	}

	public void setLogID(Integer logID) {
		this.logID = logID;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}
}
