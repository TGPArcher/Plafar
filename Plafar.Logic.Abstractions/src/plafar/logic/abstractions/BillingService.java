package plafar.logic.abstractions;
import java.util.List;
import plafar.domain.Bill;

/**
 * A interface describing the logic of Bill usage in the application
 */
public interface BillingService {
	/**
	 * This method is used to retrieve all bills from the store
	 * @return List< Bill > - a list containing all bills from store
	 */
	List<Bill> getAllBills();
	
	/**
	 * This method is used to retrieve a specific bill from the store
	 * @param billId - the ID of the desired bill
	 * @return Bill - the Bill object
	 */
	Bill getBill(int billId);
	
	/**
	 * This method is used to register a new bill to the store
	 * @param bill - the new Bill to be registered
	 * @return boolean - the status of the operation (true - success, false - failure)
	 */
	boolean registerBill(Bill bill);
	
	/**
	 * This method is used to edit an existing bill from the store
	 * @param bill - the edited bill
	 * @return boolean - the status of the operation (true - success, false - failure)
	 */
	boolean editBill(Bill bill);
	
	/**
	 * This method is used to delete an existing bill from the store
	 * @param billId - the ID of the bill desired to be deleted
	 * @return boolean - the status of the operation (true - success, false - failure)
	 */
	boolean deleteBill(int billId);
}