package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;

public class StoredCredentialDataStoreFactory implements DataStoreFactory {
	
	private static final Logger log = LoggerFactory.getLogger(StoredCredentialDataStoreFactory.class);
	
	private static StoredCredentialDataStoreFactory singleton = null;
	private static StoredCredentialDataStore storedCredentialsDataStore = new StoredCredentialDataStore();
	private static Map<String,List<ChangeNotifyListener>> changeNotifyListeners = new HashMap<String,List<ChangeNotifyListener>>();
	private static Map<String,List<UpdateRequestListener>> updateRequestListeners = new HashMap<String,List<UpdateRequestListener>>();
	
	private StoredCredentialDataStoreFactory() {}
	
	@Override
	public <V extends Serializable> DataStore<V> getDataStore(final String id) throws IOException {
		return (DataStore<V>)storedCredentialsDataStore;
	}
	
	public static StoredCredentialDataStoreFactory getInstance() {
		if (singleton == null) singleton = new StoredCredentialDataStoreFactory();
		return singleton;
	}
	
	
	
	private void notifyChange(String id, StoredCredential value) {
		List<ChangeNotifyListener> list = changeNotifyListeners.get(id);
		if (list != null)
			for (ChangeNotifyListener changeNotifyListener : list) {
				changeNotifyListener.onChange(id, value);					
			}	
	}
	
	public abstract class ChangeNotifyListener {
		
		public ChangeNotifyListener(final String id) {
			List<ChangeNotifyListener> list = changeNotifyListeners.get(id);
			if (list == null){
				list = new ArrayList<ChangeNotifyListener>();
				changeNotifyListeners.put(id, list);
			}
			list.add(this);
		}
		
		public abstract <V extends Serializable> void onChange(String id, StoredCredential value);

	}

	private void requestUpdate(String id) {
		List<UpdateRequestListener> list = updateRequestListeners.get(id);
		if (list != null)
			for (UpdateRequestListener updateRequestListener : list) {
				StoredCredential sc = storedCredentialsDataStore.dataStoreMap.get(id);
				sc = updateRequestListener.onRequest(id, sc);
				if (sc != null)	storedCredentialsDataStore.dataStoreMap.put(id, sc);
				log.debug("fromDb: " + sc);
			}
	}
	
	public abstract class UpdateRequestListener {
		
		public UpdateRequestListener(final String id) {
			List<UpdateRequestListener> list = updateRequestListeners.get(id);
			if (list == null){
				list = new ArrayList<UpdateRequestListener>();
				updateRequestListeners.put(id, list);
			}
			list.add(this);
		}
		
		public abstract <V extends Serializable> StoredCredential onRequest(String id, StoredCredential value);
		
	}
	
	public void deleteStoredCredential(String id) {
		storedCredentialsDataStore.dataStoreMap.remove(id);
	}
	
	static class StoredCredentialDataStore implements DataStore<StoredCredential> {
		
		private Map<String, StoredCredential> dataStoreMap = new HashMap<String, StoredCredential>();
		
		@Override
		public DataStoreFactory getDataStoreFactory() {
			return singleton;
		}
		@Override
		public String getId() {
			return StoredCredential.class.getSimpleName();
		}
		@Override
		public int size() throws IOException {
			return dataStoreMap.size();
		}
		@Override
		public boolean isEmpty() throws IOException {
			return dataStoreMap.isEmpty();
		}
		@Override
		public boolean containsKey(String key) throws IOException {
			return dataStoreMap.containsKey(dataStoreMap);
		}
		@Override
		public boolean containsValue(StoredCredential value) throws IOException {
			return dataStoreMap.containsKey(value);
		}
		@Override
		public Set<String> keySet() throws IOException {
			return dataStoreMap.keySet();
		}
		@Override
		public Collection<StoredCredential> values() throws IOException {
			return dataStoreMap.values();
		}
		@Override
		public StoredCredential get(String key) throws IOException {
			singleton.requestUpdate(key);
			return dataStoreMap.get(key);
		}
		@Override
		public DataStore<StoredCredential> set(String key,	StoredCredential value) throws IOException {
			dataStoreMap.put(key, value);
			singleton.notifyChange(key, value);
			return this;
		}
		@Override
		public DataStore<StoredCredential> clear() throws IOException {
			throw new UnsupportedOperationException();
		}
		@Override
		public DataStore<StoredCredential> delete(String key) throws IOException {
			dataStoreMap.remove(key);
			singleton.notifyChange(key, null);
			return this;
		}
		
	}
}
