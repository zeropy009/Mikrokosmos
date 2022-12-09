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
@Table(name = "levels")
public class Level implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "levelid")
	private Integer levelID;
	
	@Column(name = "point")
	private Integer point;
	
	@Column(name = "name")
	private String name;
	
	public Level() {
		super();
	}

	public Integer getLevelID() {
		return levelID;
	}
	
	public void setLevelID(Integer levelID) {
		this.levelID = levelID;
	}
	
	public Integer getPoint() {
		return point;
	}
	
	public void setPoint(Integer point) {
		this.point = point;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
