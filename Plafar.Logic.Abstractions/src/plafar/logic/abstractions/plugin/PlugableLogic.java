package plafar.logic.abstractions.plugin;

import interfaces.Plugable;
import plafar.logic.abstractions.*;

/**
 * This is a extension interface of Plugable, used to present more information needed for the Logic plugins.
 */
public interface PlugableLogic extends Plugable{
	/**
	 * This method is used to retrieve the class responsible of Bills handling
	 * @return ? extends BillingService - a class implementing the BillingService
	 */
	Class<? extends BillingService> getBillingServiceClass();
	
	/**
	 * This method is used to retrieve the class responsible of StoreItem handling
	 * @return ? extends ShopingServices - a class implementing the ShopingServices
	 */
	Class<? extends ShopingServices> getShopingServiceClass();
}
