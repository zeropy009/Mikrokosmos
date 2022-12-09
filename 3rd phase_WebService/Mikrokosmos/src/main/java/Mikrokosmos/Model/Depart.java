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
@Table(name = "departs")
public class Depart implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "departid")
	private String departID;
	
	@Column(name = "departname")
	private String departName;
	
	public Depart() {
		super();
	}

	public String getDepartID() {
		return departID;
	}

	public void setDepartID(String departID) {
		this.departID = departID;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
}
