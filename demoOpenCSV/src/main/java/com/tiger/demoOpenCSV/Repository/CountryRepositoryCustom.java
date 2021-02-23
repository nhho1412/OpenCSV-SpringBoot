package com.tiger.demoOpenCSV.Repository;

public interface CountryRepositoryCustom {
	public Integer getMaxId();
	public long updateCountry(Integer id, String code, String name);
}
