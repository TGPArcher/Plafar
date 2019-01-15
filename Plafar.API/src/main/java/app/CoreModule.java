package app;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import plafar.logic.abstractions.*;
import plafar.persistence.abstractions.*;
import plafar.domain.*;

/**
 * CoreModule is responsible for binding abstractions with implementations
 * And then during runtime when there will be a need to inject something will inject the right code in the right place thus decreasing coupling
 */
public class CoreModule extends AbstractModule {
	/**
	 * This is the method responsible for binding interfaces with their implementations
	 */
	@Override 
	  protected void configure() {
		bind(BillingService.class).to(PluginManager.getLogicPlugin().getBillingServiceClass());
		bind(ShopingServices.class).to(PluginManager.getLogicPlugin().getShopingServiceClass());
		
		bind(new TypeLiteral<Persistent<Bill>>() {}).to(PluginManager.getPersistencePlugin().getBillPersistenceClass());
		bind(new TypeLiteral<Persistent<StoreItem>>() {}).to(PluginManager.getPersistencePlugin().getItemPersistenceClass());
	  }
}