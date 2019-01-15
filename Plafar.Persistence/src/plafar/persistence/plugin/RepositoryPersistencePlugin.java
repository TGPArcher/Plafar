package plafar.persistence.plugin;

import plafar.domain.*;
import plafar.persistence.BillRepository;
import plafar.persistence.ItemRepository;
import plafar.persistence.SQLiteHelper;
import plafar.persistence.abstractions.Persistent;
import plafar.persistence.abstractions.plugin.PlugablePersistence;

/**
 * RepositoryPersistencePlugin is a implementation for the PlugablePersistence using SQLite database
 */
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

	@Override
	public void intitialize(String args[]) {
		SQLiteHelper.setDirectoryPath(args[0]);
		SQLiteHelper.setExternalJarName("sqlite-jdbc-3.23.1.jar");
		SQLiteHelper.setExternal(true);
		
		if(SQLiteHelper.getConnection() == null) {
			System.err.println("Plafar.Persistence.SQLite failed to initialize");
		}
		else {
			System.out.println("Plafar.Persistence.SQLite is initialized");			
		}
	}

}