package Mikrokosmos.DAO;

import Mikrokosmos.Model.BookInvoiceDTO;
import java.util.List;

import Mikrokosmos.Model.InvoiceDetail;

/**
 * @author zero
 */
public interface InvoiceDetailDAO {

    public List<InvoiceDetail> get0();

    public List<InvoiceDetail> get1();

    public List<BookInvoiceDTO> findInvoiceID(String invoiceID);
    
    public InvoiceDetail findBookID(String bookID);

    public boolean insert(InvoiceDetail invoiceDetail,String invoiceID, String userName);

    public boolean update(InvoiceDetail invoiceDetail, String invoiceID,String userName);

    public boolean deleteLogic(String invoiceID, String bookID, String userName);

    public boolean deletePhysical(String invoiceID, String bookID, String userName);

    public boolean restore(String invoiceID, String bookID, String userName);
}
