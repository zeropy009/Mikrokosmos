package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.Receipt;
import Mikrokosmos.Model.ReceiptDetailDTO;

/**
 * @author zero
 */
public interface ReceiptService {
	
	public List<Receipt> get0();

	public List<Receipt> get1();
	
	public List<ReceiptDetailDTO> findID(String receiptID);

	public boolean insert(Receipt receipt,String userName);

	public boolean update(Receipt receipt,String userName);

	public boolean deleteLogic(String receiptID,String userName);

	public boolean deletePhysical(String receiptID,String userName);

	public boolean restore(String receiptID,String userName);
}
