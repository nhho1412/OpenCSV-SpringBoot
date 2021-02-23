package com.tiger.demoOpenCSV.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tiger.demoOpenCSV.model.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer>{
	Country findByCode(String code);
	
    List<Country> findByNameLike(String name);
    
    @Query(value="SELECT coalesce(max(e.id), 0) FROM COUNTRY e", nativeQuery = true)
    Integer getMaxId();
}
