package plafar.logic;
import java.util.List;
import plafar.domain.Bill;
import plafar.logic.abstractions.BillingService;
import plafar.persistence.abastractions.Persistent;

public class BillServices implements BillingService {
	
	private Persistent<Bill> billRepository = null;
	
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
