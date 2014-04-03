package cz.cvut.fit.ortstepa.universalbookingsystem.helper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import cz.cvut.fit.ortstepa.universalbookingsystem.service.StoredCredentialDataStoreFactory;

/**
 * A helper class for Google's OAuth2 authentication API.
 * @version 20130224
 * @author Matyas Danter, Štěpán Ort
 */
public final class GoogleAuthHelper {

	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final String GOOGLEAPISAUTH_URL = "https://www.googleapis.com/auth/";
	private String stateToken;
	private List<String> scopes = null;
	private GoogleClientSecrets clientSecrets = null;
	private DataStoreFactory dataStoreFactory;
	
	public GoogleAuthHelper() throws IOException {
		this("/client_secrets.json");
	}
		
	public GoogleAuthHelper(String clientSecretsJsonLocation) throws IOException {
		defaultScopes();
		dataStoreFactory = StoredCredentialDataStoreFactory.getInstance();
		clientSecrets  = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(GoogleAuthHelper.class.getResourceAsStream(clientSecretsJsonLocation)));			
	}

	private void defaultScopes() {
		scopes = new ArrayList<String>(
				Arrays.asList(
						"https://www.googleapis.com/auth/userinfo.profile",
						"https://www.googleapis.com/auth/userinfo.email"
						)
						);
	}

	public GoogleAuthHelper(String clientId, String clientSecret, String callbackUri) {
		defaultScopes();
		clientSecrets = new GoogleClientSecrets();
		clientSecrets.getDetails().setClientId(clientId);
		clientSecrets.getDetails().setClientSecret(clientSecret);
		clientSecrets.getDetails().setRedirectUris(Arrays.asList(new String[] { callbackUri }));
	}
	
	public List<String> getScopes() {
		return scopes;
	}
	
	public void setScopes(List<String> additional) {
		for (String scope : additional) {
			if (!scope.startsWith(GOOGLEAPISAUTH_URL)) 
				scope = GOOGLEAPISAUTH_URL + scope;
			if (!scopes.contains(scope)) scopes.add(scope);
		}
	}
	
	private GoogleAuthorizationCodeFlow getFlow() {
		try {
			return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,	JSON_FACTORY, clientSecrets, scopes).setAccessType("offline").setDataStoreFactory(dataStoreFactory).build();
		} catch (IOException e) {}
		return null;
	}
	
	private String getRedirectUri() {
		return clientSecrets.getDetails().getRedirectUris().get(0);
	}

	public String buildLoginUrl() {
		generateStateToken();		
		final GoogleAuthorizationCodeRequestUrl url = getFlow().newAuthorizationUrl();
		return url.setRedirectUri(getRedirectUri()).setState(stateToken).build();
	}

	private void generateStateToken(){
		SecureRandom sr = new SecureRandom();
		stateToken = "google;"+sr.nextInt();
	}
	
	public String getStateToken(){
		return stateToken;
	}
	
	public Userinfoplus getUserinfo(String email) {
		try {
			Credential credential= getFlow().loadCredential(email);
			Oauth2 oauth2 = new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("Google-OAuth2Sample/1.0").setHttpRequestInitializer(credential).build();
			return oauth2.userinfo().get().execute();
		} catch (Exception e) {}
		return null;
	}
	
	public void authorize(String authCode, String email) {
		try {
			getFlow().createAndStoreCredential(getFlow().newTokenRequest(authCode).setRedirectUri(getRedirectUri()).execute(), email);
		} catch (Exception e) {}
	}
}