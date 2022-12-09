package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.Log;

/**
 * @author zero
 */
public interface LogService {
	
	public List<Log> get0();

	public List<Log> get1();
	
	public Log findID(Integer logID);

	public boolean deleteLogic(Integer logID,String userName);

	public boolean deletePhysical(Integer logID,String userName);

	public boolean restore(Integer logID,String userName);
}
