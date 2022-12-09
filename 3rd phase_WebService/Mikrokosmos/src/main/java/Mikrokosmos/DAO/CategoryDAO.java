package Mikrokosmos.DAO;

import java.util.List;

import Mikrokosmos.Model.Category;

/**
 * @author zero
 */
public interface CategoryDAO {

    public List<Category> get0();

    public List<Category> get1();

    public Category findID(String categoryID);

    public boolean insert(Category category, String userName);

    public boolean update(Category category, String userName);

    public boolean deleteLogic(String categoryID, String userName);

    public boolean deletePhysical(String categoryID, String userName);

    public boolean restore(String categoryID, String userName);
}
