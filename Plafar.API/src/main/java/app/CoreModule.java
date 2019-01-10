package app;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import plafar.logic.*;
import plafar.logic.abstractions.*;
import plafar.persistence.abastractions.*;
import plafar.persistence.*;
import plafar.domain.*;

public class CoreModule extends AbstractModule {
	@Override 
	  protected void configure() {
		bind(BillingService.class).to(BillServices.class);
		bind(ShopingServices.class).to(ShopServices.class);
		
		bind(new TypeLiteral<Persistent<Bill>>() {}).to(BillRepository.class);
		bind(new TypeLiteral<Persistent<StoreItem>>() {}).to(ItemRepository.class);
	  }
}
