package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.RuleDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Rule;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Action;

@Service
@Transactional(readOnly = true)
public class RuleService {

	@Autowired
	private RuleDao ruleDao;
	
	@Transactional(readOnly = false)
	public boolean delete(Long id) {
		if (ruleDao.exists(id)) {
			ruleDao.deleteById(id);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false)
	public void update(Rule rule, Errors error) {
		ruleDao.update(rule);
	}

	@Transactional(readOnly = false)
	public void add(Rule rule) {
		ruleDao.create(rule);
	}

	public Rule createEmptyRule() {
		return new Rule();
	}

	public Rule get(Long id) {
		return ruleDao.get(id);
	}

	public List<Rule> getAll() {
		return ruleDao.getAll();
	}

	public List<Rule> getAllForAction(Action action) {
		return ruleDao.getAllForAction(action);
	}

}
