package Mikrokosmos.DAO;

import java.util.List;

import Mikrokosmos.Model.Invoice;
import Mikrokosmos.Model.InvoiceTableDTO;

/**
 * @author zero
 */
public interface InvoiceDAO {

    public List<Invoice> get0();

    public List<InvoiceTableDTO> get1();

    public Invoice findID(String invoiceID);

    public boolean insert(Invoice invoice, String userName);

    public boolean update(Invoice invoice, String userName);

    public boolean deleteLogic(String invoiceID, String userName);

    public boolean deletePhysical(String invoiceID, String userName);

    public boolean restore(String invoiceID, String userName);
}
