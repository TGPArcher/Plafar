package test.persistence;

import java.util.List;

import plafar.domain.Bill;
import plafar.persistence.BillRepository;
import plafar.persistence.abastractions.Persistent;

public class TestBillRepository {
	
	/*public static void main(String[] args) {
		add();
		getAll();
		edit();
		delete();
		getAll();
	}*/
	
	@SuppressWarnings("unused")
	private static void add() {
		Persistent<Bill> bills = new BillRepository();
		Bill b1 = new Bill(2, 20, 1.5f);
		bills.addObject(b1);
		
		b1 = new Bill(3, 1, 25f);
		bills.addObject(b1);
		
		b1 = new Bill(4, 12, 0.5f);
		bills.addObject(b1);
	}
	
	@SuppressWarnings("unused")
	private static void delete() {
		Persistent<Bill> bills = new BillRepository();
		bills.deleteObject(3);
	}
	
	@SuppressWarnings("unused")
	private static void edit() {
		Persistent<Bill> bills = new BillRepository();
		Bill b1 = bills.getObject(1);
		b1.setItemId(7);
		
		bills.editObject(b1);
	}
	
	@SuppressWarnings("unused")
	private static void getAll() {
		Persistent<Bill> bills = new BillRepository();
		
		List<Bill> bls = bills.getAllObjects();
		for(int i = 0; i < bls.size(); i++) {
			Bill bill = bls.get(i);
			System.out.println(bill.getId() + " " + bill.getItemId() + " " + bill.getCuantity() + " " + bill.getPrice());
		}
	}
}
