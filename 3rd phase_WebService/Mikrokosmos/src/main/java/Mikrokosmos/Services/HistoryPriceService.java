package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.HistoryPrice;

/**
 * @author zero
 */
public interface HistoryPriceService {
	
	public List<HistoryPrice> get0();

	public List<HistoryPrice> get1();
	
	public List<HistoryPrice> findID(String bookID);

	public boolean insert(HistoryPrice historyPrice,String userName);

	public boolean update(HistoryPrice historyPrice,String userName);

	public boolean deleteLogic(Integer historyPriceID,String userName);

	public boolean deletePhysical(Integer historyPriceID,String userName);

	public boolean restore(Integer historyPriceID,String userName);
}
