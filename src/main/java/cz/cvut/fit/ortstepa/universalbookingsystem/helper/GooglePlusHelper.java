package cz.cvut.fit.ortstepa.universalbookingsystem.helper;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.util.DateTime;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.ItemScope;
import com.google.api.services.plus.model.Moment;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Reservation;

public class GooglePlusHelper {
	
	private static final String RESERVATION_SCHEMA = "http://schemas.google.com/Reservation";
	private static final String RESERVE_ACTIVITY_SCHEMA = "http://schemas.google.com/ReserveActivity";
	
	private static final Logger log = LoggerFactory.getLogger(GooglePlusHelper.class);
	
	private GoogleAuthHelper googleAuthHelper;
	
	@Required
	public void setGoogleAuthHelper(GoogleAuthHelper googleAuthHelper) {
		googleAuthHelper.addScope(PlusScopes.PLUS_LOGIN);
		googleAuthHelper.addRequestVisibleAction("http://schemas.google.com/ReserveActivity");
		this.googleAuthHelper = googleAuthHelper;
	}

	public Reservation createMoment(Reservation reservation) {
		try {
			Plus client = getClient(reservation.getAccount().getEmail());
			Moment moment = new Moment();
			moment.setType(RESERVE_ACTIVITY_SCHEMA);
			ItemScope targetItemScope = new ItemScope();
			ItemScope resultItemScope = new ItemScope();
			resultItemScope.setType(RESERVATION_SCHEMA);
			String reservationMomentUrl = googleAuthHelper.getRedirectUri().replace("/account/oauth2callback", "/resource/" + reservation.getSchedule().getResource().getId().toString());
			targetItemScope.setUrl(reservationMomentUrl);
			resultItemScope.setStartDate(new DateTime(reservation.getSchedule().getStart(), TimeZone.getDefault()).toString());
			moment.setTarget(targetItemScope);
			moment.setResult(resultItemScope);
			moment = client.moments().insert("me", "vault", moment).execute();
			reservation.setGooglePlusMomentId(moment.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reservation;
	}
	
	public Reservation removeMoment(Reservation reservation) {
		try {
			Plus client = getClient(reservation.getAccount().getEmail());
			client.moments().remove(reservation.getGooglePlusMomentId());
		} catch (Exception e) {}
		return reservation;
	}

	private Plus getClient(String email) {
		Credential credential = googleAuthHelper.getCredential(email);
		Plus plus = new Plus.Builder(credential.getTransport(), credential.getJsonFactory(), credential).setApplicationName("universal-booking-system").setHttpRequestInitializer(credential).build();
		AbstractGoogleClient client = (AbstractGoogleClient)plus;
		log.debug(client.getApplicationName());
		return plus;
	}
}
