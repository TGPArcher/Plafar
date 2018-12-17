package plafar.logic.abstractions;
import java.util.List;
import plafar.domain.Bill;

public interface BillingService {
	List<Bill> getAllBills();
	Bill getBill(int billId);
	void registerBill(Bill bill);
	boolean deleteBill(int billId);
}
