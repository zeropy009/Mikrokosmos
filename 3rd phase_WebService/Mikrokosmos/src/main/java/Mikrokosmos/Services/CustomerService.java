package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.Customer;
import Mikrokosmos.Model.CustomerDTO;

/**
 * @author zero
 */
public interface CustomerService {
	
	public List<Customer> get0();

	public List<CustomerDTO> get1();
	
	public Customer findID(String customerID);

	public boolean insert(Customer customer,String userName);

	public boolean update(Customer customer,String userName);

	public boolean deleteLogic(String customerID,String userName);

	public boolean deletePhysical(String customerID,String userName);

	public boolean restore(String customerID,String userName);
}
