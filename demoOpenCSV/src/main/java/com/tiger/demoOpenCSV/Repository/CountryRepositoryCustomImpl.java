package com.tiger.demoOpenCSV.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiger.demoOpenCSV.model.Country;


@Repository
public class CountryRepositoryCustomImpl implements CountryRepositoryCustom{
    @Autowired
    EntityManager entityManager;
    
	@Override
	public Integer getMaxId() {
		try {
			String sql ="SELECT coalesce(max(e.id), 0) FROM COUNTRY e";
			Query query = entityManager.createQuery(sql);
			return (Integer) query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	
	@Override
	public long updateCountry(Integer id, String code, String name) {
    	Country e = entityManager.find(Country.class, id);
        if (e == null) {
            return 0;
        }
        e.setCode(code);
        e.setName(name);
        entityManager.flush();
        return 1;
	}
}
