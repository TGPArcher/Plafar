package plafar.logic.abstractions.plugin;

import interfaces.Plugable;
import plafar.logic.abstractions.*;

public interface PlugableLogic extends Plugable{
	Class<? extends BillingService> getBillingServiceClass();
	Class<? extends ShopingServices> getShopingServiceClass();
}
