package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.Admin;
import co.edu.unbosque.model.User;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	public Optional<Admin> findByUsername(String username);

	public void deleteByUsername(String username);
}
