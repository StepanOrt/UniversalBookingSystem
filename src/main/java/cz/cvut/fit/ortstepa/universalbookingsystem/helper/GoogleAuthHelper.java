package cz.cvut.fit.ortstepa.universalbookingsystem.helper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.http.util.EncodingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.type.StandardClassMetadata;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.api.services.plus.PlusScopes;

import cz.cvut.fit.ortstepa.universalbookingsystem.service.StoredCredentialDataStoreFactory;

public class GoogleAuthHelper {
	
	private static final Logger log = LoggerFactory.getLogger(GoogleAuthHelper.class);

	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final String GOOGLEAPISAUTH_URL = "https://www.googleapis.com/auth/";
	private String stateToken;
	private List<String> scopes = null;
	private GoogleClientSecrets clientSecrets = null;
	private DataStoreFactory dataStoreFactory;

	private List<String> requestVisibleActions = new ArrayList<String>();
	
	public GoogleAuthHelper() throws IOException {
		this("/client_secrets.json");
	}
		
	public GoogleAuthHelper(String clientSecretsJsonLocation) throws IOException {
		defaultScopes();
		dataStoreFactory = StoredCredentialDataStoreFactory.getInstance();
		clientSecrets  = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(GoogleAuthHelper.class.getResourceAsStream(clientSecretsJsonLocation)));			
	}

	private void defaultScopes() {
		scopes = new ArrayList<String>();
		scopes.add(Oauth2Scopes.USERINFO_PROFILE);
	}

	public GoogleAuthHelper(String clientId, String clientSecret, String callbackUri) {
		defaultScopes();
		clientSecrets = new GoogleClientSecrets();
		clientSecrets.getDetails().setClientId(clientId);
		clientSecrets.getDetails().setClientSecret(clientSecret);
		clientSecrets.getDetails().setRedirectUris(Arrays.asList(new String[] { callbackUri }));
	}

	private GoogleAuthorizationCodeFlow getFlow() {
		try {
			return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,	JSON_FACTORY, clientSecrets, scopes).setAccessType("offline").setApprovalPrompt("force").setScopes(scopes).setDataStoreFactory(dataStoreFactory).build();
		} catch (IOException e) {}
		return null;
	}
	
	private String getRedirectUri() {
		return clientSecrets.getDetails().getRedirectUris().get(0);
	}

	public String buildLoginUrl() {
		generateStateToken();		
		GoogleAuthorizationCodeRequestUrl url = getFlow().newAuthorizationUrl();
		url = addRequestVisibleActions(url);
		url.put("include_granted_scopes", "false");
		return url.setRedirectUri(getRedirectUri()).setState(stateToken).build();
	}

	private GoogleAuthorizationCodeRequestUrl addRequestVisibleActions(GoogleAuthorizationCodeRequestUrl url) {
		if (requestVisibleActions.size() > 0)			
			url.put("request_visible_actions", Joiner.on(' ').join(requestVisibleActions.toArray(new String[] {})));
		return url;
	}
	
	public void addRequestVisibleAction(String requestVisibleAction) {
		requestVisibleActions.add(requestVisibleAction);
	}

	private void generateStateToken(){
		SecureRandom sr = new SecureRandom();
		stateToken = "google;"+sr.nextInt();
	}
	
	public String getStateToken(){
		return stateToken;
	}
	
	public Credential getCredential(String email) {
		try {
			return getFlow().loadCredential(email);
		} catch (Exception e) {}
		return null;
	}
	
	public Userinfoplus getUserinfo(String email) {
		try {
			Credential credential= getCredential(email);
			Oauth2 oauth2 = new Oauth2.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName("universal-booking-system").setHttpRequestInitializer(credential).build();
			return oauth2.userinfo().get().execute();
		} catch (Exception e) {}
		return null;
	}
	
	public void authorize(String authCode, String email) {
		try {
			getFlow().createAndStoreCredential(getFlow().newTokenRequest(authCode).setRedirectUri(getRedirectUri()).execute(), email);
		} catch (Exception e) {}
	}

	public void addScope(String scope) {
		if (!scope.startsWith(GOOGLEAPISAUTH_URL)) 
			scope = GOOGLEAPISAUTH_URL + scope;
		if (!scopes.contains(scope)) scopes.add(scope);
	}

	public void addScopes(Set<String> all) {
		for (String string : all) {
			addScope(string);
		}
	}
}