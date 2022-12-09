package theego.model;

/**
 * 
 * @author TuanDuc
 *
 */

public class TaiKhoan {

	private int id;
	private String password;
	private String name;

	public TaiKhoan() {
		super();
	}

	public TaiKhoan(String password, String name) {
		super();
		this.password = password;
		this.name = name;
	}
	
	public TaiKhoan(int id, String password, String name) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
