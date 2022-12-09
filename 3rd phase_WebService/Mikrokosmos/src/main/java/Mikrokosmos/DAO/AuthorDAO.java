package Mikrokosmos.DAO;

import java.util.List;

import Mikrokosmos.Model.Author;

/**
 * @author zero
 */
public interface AuthorDAO {

    public List<Author> get0();

    public List<Author> get1();

    public Author findID(String authorID);

    public boolean insert(Author author, String userName);

    public boolean update(Author author, String userName);

    public boolean deleteLogic(String authorID, String userName);

    public boolean deletePhysical(String authorID, String userName);

    public boolean restore(String authorID, String userName);
}
