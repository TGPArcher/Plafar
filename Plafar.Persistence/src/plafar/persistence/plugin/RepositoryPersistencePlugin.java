package plafar.persistence.plugin;

import plafar.domain.*;
import plafar.persistence.BillRepository;
import plafar.persistence.ItemRepository;
import plafar.persistence.abstractions.Persistent;
import plafar.persistence.abstractions.plugin.PlugablePersistence;

public class RepositoryPersistencePlugin implements PlugablePersistence{

	@Override
	public String getName() {
		return "Plafar.Persistence.SQLite";
	}

	@Override
	public String getKey() {
		return "Persistence";
	}

	@Override
	public String getVersion() {
		return "0.0.1-SNAPSHOT";
	}

	@Override
	public Class<? extends Persistent<Bill>> getBillPersistenceClass() {
		return BillRepository.class;
	}

	@Override
	public Class<? extends Persistent<StoreItem>> getItemPersistenceClass() {
		return ItemRepository.class;
	}

}
