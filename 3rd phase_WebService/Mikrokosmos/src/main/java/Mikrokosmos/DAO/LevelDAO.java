package Mikrokosmos.DAO;

import java.util.List;

import Mikrokosmos.Model.Level;

/**
 * @author zero
 */
public interface LevelDAO {

    public List<Level> get0();

    public List<Level> get1();

    public Level findID(Integer levelID);

    public boolean insert(Level level, String userName);

    public boolean update(Level level, String userName);

    public boolean deleteLogic(Integer levelID, String userName);

    public boolean deletePhysical(Integer levelID, String userName);

    public boolean restore(Integer levelID, String userName);
}
