package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.DbConnectionData;

//@Repository
public class HbnConnectionRepository implements ConnectionRepository {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected String getDomainClassName() {
		return DbConnectionData.class.getName();
	}
	
	
	@Override
	public void addConnection(Connection<?> connection) {
		getSession().save((DbConnectionData)connection.createData());
	}

	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		MultiValueMap<String, Connection<?>> multiValueMap = new LinkedMultiValueMap<String, Connection<?>>();
		List<Connection<?>> allConnections = getConnectionList((List<DbConnectionData>)queryAll().list());
		for (Connection<?> connection : allConnections) {
			String providerId = getProviderId(connection.getApi().getClass());
			multiValueMap.add(providerId, connection);
		}
		return multiValueMap;
	}


	private Query queryAll() {
		return getSession().createQuery("from " + getDomainClassName());
	}
	
	@Override
	public List<Connection<?>> findConnections(String providerId) {
		return getConnectionList((List<DbConnectionData>)byProviderQuery(providerId, false).list());
	}

	private Query byProviderQuery(String providerId, boolean delete) {
		String queryString = "";
		if (delete) queryString += "delete ";
		queryString += "from Connection where providerId = :providerId";
		return getSession()
				.createQuery(queryString)
				.setParameter("providerId", providerId);
	}
	
	private <A> Query byProviderQuery(Class<A> apiType) {
		return byProviderQuery(getProviderId(apiType), false);
	}
	

	@Override
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		return getConnectionList((List<DbConnectionData>)byProviderQuery(apiType).list(), apiType);
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
		MultiValueMap<String, Connection<?>> multiValueMap = new LinkedMultiValueMap<String, Connection<?>>();
		for (Entry<String, List<String>> entry : providerUserIds.entrySet()) {
			String providerId = entry.getKey();
			List<String> userIdList = entry.getValue();
			for (String userId : userIdList) {
				Connection<?> connection = getConnection((DbConnectionData)byProviderAndUserQuery(providerId, userId, false).uniqueResult());				
				multiValueMap.add(providerId, connection);
			}
		}
		return multiValueMap;
	}

	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		return getConnection((DbConnectionData)byProviderQuery(apiType).uniqueResult(), apiType);
	}

	@Override
	public Connection<?> getConnection(ConnectionKey connectionKey) {
		return getConnection((DbConnectionData)byProviderAndUserQuery(connectionKey.getProviderId(), connectionKey.getProviderUserId(), false).uniqueResult());
	}

	private Query byProviderAndUserQuery(String providerId,	String providerUserId, boolean delete) {
		String queryString = "";
		if (delete) queryString += "delete ";
		queryString += "from " + getDomainClassName() + " where providerId = :providerId and providerUserId = :providerUserId";
		return getSession().createQuery(queryString)
				.setParameter("providerId", providerId)
				.setParameter("providerUserId", providerUserId);
	}
	
	private <A> Query byProviderAndUserQuery(Class<A> apiType,	String providerUserId, boolean delete) {
		return byProviderAndUserQuery(getProviderId(apiType), providerUserId, delete);
	}

	@Override
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		return getConnection((DbConnectionData)byProviderAndUserQuery(apiType, providerUserId, false).uniqueResult(), apiType);
	}

	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		return getConnection((DbConnectionData)byProviderQuery(apiType).uniqueResult(), apiType);
	}

	@Override
	public void removeConnection(ConnectionKey connectionKey) {
		byProviderAndUserQuery(connectionKey.getProviderId(), connectionKey.getProviderUserId(), true).executeUpdate();

	}

	@Override
	public void removeConnections(String providerId) {
		byProviderQuery(providerId, true).executeUpdate();
	}

	@Override
	public void updateConnection(Connection<?> connection) {
		getSession().update((DbConnectionData)connection.createData());
	}
	
	private Connection<?> getConnection(DbConnectionData connectionData) {
		return getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
	}
	
	private <A> Connection<A> getConnection(DbConnectionData connectionData, Class<A> apiType) {
		return (Connection<A>)getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
	}

	private List<Connection<?>> getConnectionList(List<DbConnectionData> list) {
		List<Connection<?>> connectionList = new LinkedList<Connection<?>>();
		for (DbConnectionData dbConnectionData : list) {
			connectionList.add((Connection<?>)getConnection(dbConnectionData));
		}
		return connectionList;
	}
	
	private <A> List<Connection<A>> getConnectionList(List<DbConnectionData> list, Class<A> apiType) {
		List<Connection<A>> connectionList = new LinkedList<Connection<A>>();
		for (DbConnectionData dbConnectionData : list) {
			connectionList.add((Connection<A>)getConnection(dbConnectionData));
		}
		return connectionList;
	}
	
	private <A> String getProviderId(Class<A> apiType) {
		return getConnectionFactory(apiType).getProviderId();
	}
	
	private ConnectionFactory<?> getConnectionFactory(String providerId) {
		return connectionFactoryLocator.getConnectionFactory(providerId);
	}
	
	private <A> ConnectionFactory<A> getConnectionFactory(Class<A> apiType) {
		return connectionFactoryLocator.getConnectionFactory(apiType);
	}

}
