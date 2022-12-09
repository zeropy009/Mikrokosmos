package Mikrokosmos.DAO;

import java.util.List;

import Mikrokosmos.Model.Depart;

/**
 * @author zero
 */
public interface DepartDAO {

    public List<Depart> get0();

    public List<Depart> get1();

    public Depart findID(String departID);

    public boolean insert(Depart depart, String userName);

    public boolean update(Depart depart, String userName);

    public boolean deleteLogic(String departID, String userName);

    public boolean deletePhysical(String departID, String userName);

    public boolean restore(String departID, String userName);
}
