package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.Method;

/**
 * @author zero
 */
public interface MethodService {
	
	public List<Method> get0();

	public List<Method> get1();
	
	public Method findID(Integer methodID);

	public boolean insert(Method method,String userName);

	public boolean update(Method method,String userName);

	public boolean deleteLogic(Integer methodID,String userName);

	public boolean deletePhysical(Integer methodID,String userName);

	public boolean restore(Integer methodID,String userName);
}
