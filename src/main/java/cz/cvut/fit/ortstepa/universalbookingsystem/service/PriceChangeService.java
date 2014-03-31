package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.PriceChangeDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.PriceChange;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Rule;

@Service
@Transactional(readOnly = true)
public class PriceChangeService {

	@Autowired
	private PriceChangeDao priceChangeDao;
	
	public List<PriceChange> getAll() {
		return priceChangeDao.getAll();
	}

	public PriceChange createEmptyRule() {
		return new PriceChange();
	}

	public PriceChange get(Long id) {
		return priceChangeDao.get(id);
	}

	@Transactional(readOnly = false)
	public boolean delete(Long id) {
		if (priceChangeDao.exists(id)) {
			priceChangeDao.deleteById(id);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false)
	public void update(PriceChange priceChange, BindingResult result) {
		priceChangeDao.update(priceChange);
	}

	@Transactional(readOnly = false)
	public void add(PriceChange priceChange) {
		priceChangeDao.create(priceChange);
	}
	
}
