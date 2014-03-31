package cz.cvut.fit.ortstepa.universalbookingsystem.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.PriceChange;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Resource;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourceProperty;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourcePropertyValue;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Rule;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Schedule;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.UserDetailsAdapter;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Action;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.PropertyType;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.AccountService;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ResourcePropertyService;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.RuleService;

@Component
public class PriceEngine {
	
	@Autowired
	private RuleService ruleService;
	@Autowired
	private ResourcePropertyService resourcePropertyService;
	@Autowired
	private AccountService accountService;

	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("MMMMM d, yyyy HH:mm:ss", Locale.US);

	public Double calculatePrice(Schedule schedule, Action action) {
		Map<String, String> variableMap = variableValues(schedule);
		List<Rule> rules = ruleService.getAllForAction(action);
		double price = schedule.getResource().getPrice();
		if (action.equals(Action.CANCEL)) price *= -1;
		for (Rule rule : rules) {
			try {
				if(LogicEngine.eval(rule, variableMap)) {
					price = calculatePrice(price, rule.getPriceChange());
				}
			} catch (Exception e) {
				return null;
			}			
		}
		return price;
	}
	
	private Double calculatePrice(Double price, PriceChange priceChange) {
		Double newPrice = price;
		switch (priceChange.getType()) {
		case ABSOLUTE:
			newPrice = priceChange.getValue();
			break;
		case MULTIPLICATION:
			newPrice = price * priceChange.getValue();
			break;
		case RELATIVE:
			newPrice = price + priceChange.getValue();
			break;
		default:
			throw new RuntimeException();
		}
		
		return newPrice;
	}

	private Map<String, String> variableValues(Schedule schedule) {
		Map<String, String> variableMap = new HashMap<String, String>();
		variableMap.put("capacity", ""+schedule.getCapacity());
		variableMap.put("available", ""+schedule.getCapacityAvailable());
		variableMap.put("duration", ""+schedule.getDuration());
		variableMap.put("start", DATE_FORMAT.format(schedule.getStart()));
		variableMap.put("end", DATE_FORMAT.format(schedule.getEnd()));
		Resource resource = schedule.getResource();
		variableMap.put("original_price", ""+resource.getPrice());
		for (ResourcePropertyValue resourcePropertyValue : resource.getPropertyValues()) {
			if (resourcePropertyValue.getProperty().getType().equals(PropertyType.INTERNAL))
				variableMap.put("resource_" + resourcePropertyValue.getProperty().getName(), resourcePropertyValue.getValue());
		}
		String accountGroup = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated() && auth.getName() != "anonymousUser") {
			String email = ((UserDetailsAdapter)auth.getPrincipal()).getEmail();
			Account account = accountService.getAccountByEmail(email);
			if (account.getGroup() != null)	accountGroup = account.getGroup().getName();
		}
		variableMap.put("account_group", accountGroup);
		return variableMap;
	}
	
	public Set<String> exposedVariables() {
		Set<String> variables = new HashSet<String>();
		String[] vars = new String[] { "capacity", "available", "duration", "start", "end", "original_price", "accout_group" };
		for (String v : vars) {
			variables.add(v);
		}
		List<ResourceProperty> properties = resourcePropertyService.getAll();
		for (ResourceProperty resourceProperty : properties) {
			if (resourceProperty.getType().equals(PropertyType.INTERNAL))
				variables.add("resource_" + resourceProperty.getName());
		}
		return variables;
	}
	

	public boolean canReserve(Account account, Schedule schedule) {
		double price = calculatePrice(schedule, Action.CREATE);
		return ((account.getCredit() - price) >= 0);
	}

}
