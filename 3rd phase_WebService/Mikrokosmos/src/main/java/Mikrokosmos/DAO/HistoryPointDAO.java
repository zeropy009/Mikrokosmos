package Mikrokosmos.DAO;

import java.util.List;

import Mikrokosmos.Model.HistoryPoint;

/**
 * @author zero
 */
public interface HistoryPointDAO {

    public List<HistoryPoint> get0();

    public List<HistoryPoint> get1(String customerID);

    public HistoryPoint findID(Integer historyPointID);

    public boolean insert(HistoryPoint historyPoint, String userName);

    public boolean update(HistoryPoint historyPoint, String userName);

    public boolean deleteLogic(Integer historyPointID, String userName);

    public boolean deletePhysical(Integer historyPointID, String userName);

    public boolean restore(Integer historyPointID, String userName);
}
