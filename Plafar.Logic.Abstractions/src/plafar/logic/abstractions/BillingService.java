package plafar.logic.abstractions;
import java.util.List;
import plafar.domain.Bill;

public interface BillingService {
	List<Bill> getAllBills();
	Bill getBill(int billId);
	boolean registerBill(Bill bill);
	boolean editBill(Bill bill);
	boolean deleteBill(int billId);
}
