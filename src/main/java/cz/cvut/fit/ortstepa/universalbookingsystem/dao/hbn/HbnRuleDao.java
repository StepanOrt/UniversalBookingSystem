package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.RuleDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Rule;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Action;

@Repository
public class HbnRuleDao extends AbstractHbnDao<Rule> implements RuleDao {

	@Override
	public List<Rule> getAllForAction(Action action) {
		Criteria crt = getSession().createCriteria(Rule.class);
		crt.add(Restrictions.eq("action", action));
		return crt.list();
	}

}
