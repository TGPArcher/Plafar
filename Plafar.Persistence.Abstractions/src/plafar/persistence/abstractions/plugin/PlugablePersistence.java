package plafar.persistence.abstractions.plugin;

import interfaces.Plugable;
import plafar.domain.*;
import plafar.persistence.abstractions.Persistent;

/**
 * This is a extension interface of the Plugable interface.
 * The extensions provide more details about Persistence plugins which should offer more specific information
 */
public interface PlugablePersistence extends Plugable {
	/**
	 * This method is used to retrieve the class which is responsible of Bills persistence
	 * @return ? extends Persistent<Bill> - The class implementing Bill persistence
	 */
	Class<? extends Persistent<Bill>> getBillPersistenceClass();
	/**
	 * This method is used to retrieve the class which is responsible of StoreItems persistence
	 * @return ? extends Persistent<StoreItem> - The class implementing StoreItem persistence
	 */
	Class<? extends Persistent<StoreItem>> getItemPersistenceClass();
}
