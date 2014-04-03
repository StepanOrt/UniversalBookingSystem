package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.io.IOException;
import java.io.Serializable;

import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.DataStore;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.AccountDao;

public class GoogleDataStoreFactory extends AbstractDataStoreFactory {
	
	private AccountDao accountDao;

	public GoogleDataStoreFactory(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@Override
	protected <V extends Serializable> DataStore<V> createDataStore(String id)
			throws IOException {
		return new GoogleDataStore<V>(this, id, accountDao);
	}
}
