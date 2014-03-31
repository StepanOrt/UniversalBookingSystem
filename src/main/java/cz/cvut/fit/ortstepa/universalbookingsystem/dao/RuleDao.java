package cz.cvut.fit.ortstepa.universalbookingsystem.dao;

import java.util.List;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Rule;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Action;

public interface RuleDao extends Dao<Rule> {

	List<Rule> getAllForAction(Action action);

}
