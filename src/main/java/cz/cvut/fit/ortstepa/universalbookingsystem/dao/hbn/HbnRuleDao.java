package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.RuleDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Rule;

@Repository
public class HbnRuleDao extends AbstractHbnDao<Rule> implements RuleDao {

}
