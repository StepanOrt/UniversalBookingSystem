package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.google.api.client.util.Base64;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.AccountDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;

public class GoogleDataStore<V extends Serializable> extends AbstractDataStore<V> {
	
	
	private Map<String, V> map;
	private AccountDao accountDao;

	public GoogleDataStore(GoogleDataStoreFactory factory, String id, AccountDao accountDao) {
		super(factory, id);
		Preconditions.checkNotNull(accountDao);
		this.accountDao = accountDao;
		map = new HashMap<String, V>();
		save();
	}

	@Override
	public DataStore<V> clear() throws IOException {
		map.clear();
		save();
		return this;
	}

	@Override
	public DataStore<V> delete(String key) throws IOException {
		map.remove(key);
		save();
		return null;
	}

	@Override
	public V get(String key) throws IOException {
		load();
		return map.get(key);
	}

	@Override
	public Set<String> keySet() throws IOException {
		load();
		return map.keySet();
	}

	@Override
	public DataStore<V> set(String key, V value) throws IOException {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(value);
		map.put(key, value);
		save();
		return this;
	}

	@Override
	public Collection<V> values() throws IOException {
		load();
		return map.values();
	}
	
	private void load() {
		Account account = accountDao.findByEmail(authorizedAccountEmail());
		String json = account.getGoogleCredentials();
		Map<String, Map<String, String>> mapOfMaps = tryParse(json);
		if (mapOfMaps != null && mapOfMaps.containsKey(getId())) {
			Map<String, String> map = mapOfMaps.get(getId());
			this.map.clear();
			for (Entry<String, String> entry : map.entrySet()) {
				
				try {
					byte[] bytes = Base64.decodeBase64(entry.getValue());		
					Serializable s = IOUtils.deserialize(bytes);
					this.map.put(entry.getKey(), (V)s);
				} catch (IOException e) {}
			}
		}
	}

	private Map<String, Map<String, String>> tryParse(String json) {
		Map<String, Map<String, String>> mapOfMaps = null;
		try {
			if (json == null) return null;
			mapOfMaps = JsonReader.jsonToMaps(json);
		} catch (IOException e) {}
		return mapOfMaps;
	}

	private void save() {
		Account account = accountDao.findByEmail(authorizedAccountEmail());
		String json = account.getGoogleCredentials();
		Map<String, Map<String, String>> mapOfMaps = tryParse(json);
		if (mapOfMaps == null) {
			mapOfMaps = new HashMap<String, Map<String,String>>();
		}
		Map<String, String> map = null;
		if (mapOfMaps.containsKey(getId()))
			map = mapOfMaps.get(getId());
		else
			map = new HashMap<String, String>();
		map.clear();
		for (Entry<String, V> entry : this.map.entrySet()) {
			try {
				byte[] bytes = IOUtils.serialize(entry.getValue());
				map.put(entry.getKey(), Base64.encodeBase64String(bytes));
			} catch (IOException e) {}
		}
		mapOfMaps.put(getId(), map);
		try {
			json = JsonWriter.objectToJson(mapOfMaps);
			account.setGoogleCredentials(json);
			accountDao.update(account);
		} catch (IOException e) {}
		
					
	}

	
	private String authorizedAccountEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}