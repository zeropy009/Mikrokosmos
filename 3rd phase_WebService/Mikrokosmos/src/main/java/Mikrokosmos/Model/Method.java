package Mikrokosmos.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zero
 */
@Entity
@Table(name = "methods")
public class Method implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "methodid")
	private Integer methodID;
	
	@Column(name = "methodname")
	private String methodName;
	
	@Column(name = "note")
	private String note;
	
	public Method() {
		super();
	}

	public Integer getMethodID() {
		return methodID;
	}

	public void setMethodID(Integer methodID) {
		this.methodID = methodID;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
