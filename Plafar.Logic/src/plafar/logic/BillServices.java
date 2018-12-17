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
	public void registerBill(Bill bill) {
		if(bill == null) {
			System.out.println("Trying to register a null bill");
			return;
		}
		
		boolean status = billRepository.addObject(bill);
		
		if(status) {
			System.out.println("Bill registered succesfully!");
		}
		else {
			System.out.println("Failed to register bill!");
		}
	}

	@Override
	public boolean deleteBill(int billId) {
		return billRepository.deleteObject(billId);
	}
	
}
