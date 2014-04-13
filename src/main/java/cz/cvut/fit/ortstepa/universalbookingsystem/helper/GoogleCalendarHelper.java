package cz.cvut.fit.ortstepa.universalbookingsystem.helper;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Required;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Reservation;

public class GoogleCalendarHelper {
	
	private static final String RESERVATION_CALENDAR_SUMMARY = "Universal reservation system";
	private com.google.api.services.calendar.Calendar client;
	
	private GoogleAuthHelper googleAuthHelper;
	
	@Required	
	public void setGoogleAuthHelper(GoogleAuthHelper googleAuthHelper) {
		googleAuthHelper.addScope(CalendarScopes.CALENDAR);
		this.googleAuthHelper = googleAuthHelper;
	}

	public Reservation createReservationEvent(Reservation reservation) {
		try {
			client = getClient(reservation.getAccount().getEmail());
			Calendar calendar = getCalendar(RESERVATION_CALENDAR_SUMMARY);
			String summary = reservation.getSchedule().getResource().toString();
			Event event = newEvent(summary, reservation.getSchedule().getStart(), reservation.getSchedule().getEnd());
			Event eventResult = client.events().insert(calendar.getId(), event).execute();
			reservation.setCalendarEventId(eventResult.getId());
		} catch (Exception e) {}
		return reservation;
	}
	
	private Event newEvent(String summary, Date start, Date end) {
	    Event event = new Event();
	    event.setSummary(summary);
	    Date startDate = start;
	    Date endDate = end;
	    DateTime startDateTime = new DateTime(startDate, TimeZone.getDefault());
	    event.setStart(new EventDateTime().setDateTime(startDateTime));
	    DateTime endDateTime = new DateTime(endDate, TimeZone.getDefault());
	    event.setEnd(new EventDateTime().setDateTime(endDateTime));
	    return event;
	  }

	
	public Reservation removeReservationEvent(Reservation reservation) {
		try {
			client = getClient(reservation.getAccount().getEmail());
			Calendar calendar = getCalendar(RESERVATION_CALENDAR_SUMMARY);
			client.events().delete(calendar.getId(), reservation.getCalendarEventId()).execute();
		} catch (Exception e) {}
		return reservation;
	}

	private Calendar getCalendar(String reservationCalendarSummary) throws IOException {
		CalendarList calendarList = client.calendarList().list().execute();
		for (CalendarListEntry entry : calendarList.getItems()) {
			if (entry.getSummary().equals(reservationCalendarSummary))
				return client.calendars().get(entry.getId()).execute();
		}
		return client.calendars().insert(new com.google.api.services.calendar.model.Calendar().setSummary(reservationCalendarSummary)).execute();
	}

	private com.google.api.services.calendar.Calendar getClient(String email) {
		Credential credential = googleAuthHelper.getCredential(email);
		return new com.google.api.services.calendar.Calendar.Builder(credential.getTransport(), credential.getJsonFactory(), credential).build();
	}
	
	
}
