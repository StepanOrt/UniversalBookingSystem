package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.PriceChangeDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.PriceChange;

@Repository
public class HbnPriceChangeDao extends AbstractHbnDao<PriceChange> implements PriceChangeDao {

}
