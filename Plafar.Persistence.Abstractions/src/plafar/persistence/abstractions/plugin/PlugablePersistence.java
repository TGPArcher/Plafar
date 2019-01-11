package plafar.persistence.abstractions.plugin;

import interfaces.Plugable;
import plafar.domain.*;
import plafar.persistence.abstractions.Persistent;

public interface PlugablePersistence extends Plugable {
	Class<? extends Persistent<Bill>> getBillPersistenceClass();
	Class<? extends Persistent<StoreItem>> getItemPersistenceClass();
}
