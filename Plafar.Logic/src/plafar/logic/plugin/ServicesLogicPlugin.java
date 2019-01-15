package plafar.logic.plugin;

import plafar.logic.*;
import plafar.logic.abstractions.*;
import plafar.logic.abstractions.plugin.PlugableLogic;

/**
 * This is the implementation of PlugableLogic for the Logic Services plugin containing data about plugin, methods to initialize the plugin and also to retrieve the functionality
 */
public class ServicesLogicPlugin implements PlugableLogic{

	@Override
	public String getName() {
		return "Plafar.Logic.Services";
	}
	
	@Override
	public String getKey() {
		return "Logic";
	}

	@Override
	public String getVersion() {
		return "0.0.1-SNAPSHOT";
	}

	@Override
	public Class<? extends BillingService> getBillingServiceClass() {
		return BillServices.class;
	}

	@Override
	public Class<? extends ShopingServices> getShopingServiceClass() {
		return ShopServices.class;
	}
	
	@Override
	public void intitialize(String args[]) {
		// no initialization required
		System.out.println("Plafar.Logic.Services is initialized");
	}

}