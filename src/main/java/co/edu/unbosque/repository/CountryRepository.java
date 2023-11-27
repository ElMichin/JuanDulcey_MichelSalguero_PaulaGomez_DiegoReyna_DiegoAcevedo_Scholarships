package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	public Optional<Country> findByName(String name);

	public void deleteByName(String name);
}
