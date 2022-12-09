package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.ShipInfor;

/**
 * @author zero
 */
public interface ShipInforService {
	
	public List<ShipInfor> get0();

	public List<ShipInfor> get1();
	
	public ShipInfor findID(String invoiceID);

	public boolean insert(ShipInfor shipInfor,String userName);

	public boolean update(ShipInfor shipInfor,String userName);

	public boolean deleteLogic(Integer shipInforID,String userName);

	public boolean deletePhysical(Integer shipInforID,String userName);

	public boolean restore(Integer shipInforID,String userName);
}
