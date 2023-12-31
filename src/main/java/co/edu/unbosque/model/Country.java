package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "countries")
public class Country {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@Column(unique = true)
	private String name;
	private ArrayList<String> cities;
	private long cost;

	public Country(Long id, String name, ArrayList<String> cities, long cost) {
		super();
		this.id = id;
		this.name = name;
		this.cities = cities;
		this.cost = cost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getCities() {
		return cities;
	}

	public void setCities(ArrayList<String> cities) {
		this.cities = cities;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cities, cost, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return Objects.equals(cities, other.cities) && cost == other.cost && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

}
