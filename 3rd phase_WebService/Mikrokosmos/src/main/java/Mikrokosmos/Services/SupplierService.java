package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.Supplier;

/**
 * @author zero
 */
public interface SupplierService {
	
	public List<Supplier> get0();

	public List<Supplier> get1();
	
	public Supplier findID(String supplierID);

	public boolean insert(Supplier supplier,String userName);

	public boolean update(Supplier supplier,String userName);

	public boolean deleteLogic(String supplierID,String userName);

	public boolean deletePhysical(String supplierID,String userName);

	public boolean restore(String supplierID,String userName);
}
