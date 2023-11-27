package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Admin;
import co.edu.unbosque.model.Country;
import co.edu.unbosque.repository.CountryRepository;

@Service
public class CountryService implements CRUDOperation<Country> {

	@Autowired
	private CountryRepository countryRep;

	public CountryService() {
	}

	@Override
	public int create(Country data) {
		if (findNameAlreadyTaken(data)) {
			return 1;
		} else {
			countryRep.save(data);
		}
		return 0;
	}

	@Override
	public List<Country> getAll() {
		return countryRep.findAll();
	}

	@Override
	public int deleteByID(Long id) {
		Optional<Country> found = countryRep.findById(id);
		if (found.isPresent()) {
			countryRep.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteByUsername(String username) {
		Optional<Country> found = countryRep.findByName(username);
		if (found.isPresent()) {
			countryRep.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateByID(Long id, Country newData) {
		Optional<Country> found = countryRep.findById(id);
		Optional<Country> newFound = countryRep.findByName(newData.getName());
		if (found.isPresent() && !newFound.isPresent()) {
			Country temp = found.get();

			countryRep.save(temp);
			return 0;
		} else if (found.isPresent() && newFound.isPresent())
			return 1;
		else if (!found.isPresent())
			return 2;
		return 3;
	}

	@Override
	public long count() {
		return countryRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return countryRep.existsById(id) ? true : false;
	}

	public boolean findNameAlreadyTaken(Country newUser) {
		Optional<Country> found = countryRep.findByName(newUser.getName());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
