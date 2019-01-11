package app;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import plafar.logic.abstractions.*;
import plafar.persistence.abstractions.*;
import plafar.domain.*;

public class CoreModule extends AbstractModule {
	@Override 
	  protected void configure() {
		bind(BillingService.class).to(PluginManager.getLogicPlugin().getBillingServiceClass());
		bind(ShopingServices.class).to(PluginManager.getLogicPlugin().getShopingServiceClass());
		
		bind(new TypeLiteral<Persistent<Bill>>() {}).to(PluginManager.getPersistencePlugin().getBillPersistenceClass());
		bind(new TypeLiteral<Persistent<StoreItem>>() {}).to(PluginManager.getPersistencePlugin().getItemPersistenceClass());
	  }
}