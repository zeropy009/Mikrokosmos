package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.Book;
import Mikrokosmos.Model.BookTableDTO;

/**
 * @book zero
 */
public interface BookService {
	
	public List<Book> get0();

	public List<BookTableDTO> get1();
	
	public Book findID(String bookID);

	public boolean insert(Book book,String userName);

	public boolean update(Book book,String userName);

	public boolean deleteLogic(String bookID,String userName);

	public boolean deletePhysical(String bookID,String userName);

	public boolean restore(String bookID,String userName);
}
