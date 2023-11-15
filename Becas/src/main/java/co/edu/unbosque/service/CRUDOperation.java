package co.edu.unbosque.service;

import java.util.List;

public interface CRUDOperation<T> {
	
	public int create(T data);
	
	public List<T> getAll();
	
	public int deleteByID(Long id);
	
	public int updateByID(Long id, T newData);
	
	public long count();
	
	public boolean exist(Long id);
	

}
