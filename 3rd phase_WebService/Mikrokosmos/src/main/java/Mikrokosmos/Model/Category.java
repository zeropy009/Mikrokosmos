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
@Table(name = "categories")
public class Category implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "categoryid")
	private String categoryID;
	
	@Column(name = "categoryname")
	private String categoryName;
	
	public Category() {
		super();
	}
	
	public String getCategoryID() {
		return categoryID;
	}
	
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
