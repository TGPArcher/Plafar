package plafar.logic;

import java.util.List;
import com.google.inject.Inject;
import plafar.domain.Bill;
import plafar.logic.abstractions.BillingService;
import plafar.persistence.abstractions.Persistent;

/**
 * BillServices is a implementation of BillingService using the services architecture for the store.
 */
public class BillServices implements BillingService {
	/**
	 * This is the access point to the database where the service data is persisted
	 */
	private Persistent<Bill> billRepository = null;
	
	/**
	 * Initializes the service with dependency injection, injecting the database layer
	 * @param billRepository - a persistence class which implements the Persistent<Bill>
	 */
	@Inject
	public BillServices(Persistent<Bill> billRepository) {
		this.billRepository = billRepository;
	}
	
	@Override
	public List<Bill> getAllBills() {
		return billRepository.getAllObjects();
	}

	@Override
	public Bill getBill(int billId) {
		return billRepository.getObject(billId);
	}

	@Override
	public boolean registerBill(Bill bill) {
		if(bill == null) {
			return false;
		}
		
		return billRepository.addObject(bill);
	}

	@Override
	public boolean deleteBill(int billId) {
		return billRepository.deleteObject(billId);
	}

	@Override
	public boolean editBill(Bill bill) {
		if(bill == null) {
			return false;
		}
		
		return billRepository.editObject(bill);
	}
	
}
