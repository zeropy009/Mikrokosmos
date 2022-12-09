package mikrokosmos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="pass")
	private String pass;
	
	public User() {
		super();
	}
	
	public User(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
