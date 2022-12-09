package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.ReceiptDetail;

/**
 * @author zero
 */
public interface ReceiptDetailService {

    public List<ReceiptDetail> get0();

    public List<ReceiptDetail> get1();

    public List<ReceiptDetail> findReceiptID(String receiptID);

    public List<ReceiptDetail> findBookID(String bookID);

    public boolean insert(ReceiptDetail receiptDetail, String receiptID, String userName);

    public boolean update(ReceiptDetail receiptDetail, String receiptID, String userName);

    public boolean deleteLogic(String receiptID, String bookID, String userName);

    public boolean deletePhysical(String receiptID, String bookID, String userName);

    public boolean restore(String receiptID, String bookID, String userName);
}
