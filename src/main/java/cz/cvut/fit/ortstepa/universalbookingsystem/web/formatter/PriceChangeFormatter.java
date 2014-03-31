package cz.cvut.fit.ortstepa.universalbookingsystem.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.PriceChange;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.PriceChangeService;

@Component
public class PriceChangeFormatter implements Formatter<PriceChange> {

	@Autowired
	private PriceChangeService priceChangeService;
	
	@Override
	public String print(PriceChange priceChange, Locale locale) {
		return priceChange.getName();
	}

	@Override
	public PriceChange parse(String formValue, Locale locale) throws ParseException {
		Long id = Long.parseLong(formValue);
		return priceChangeService.get(id);
	}

}
