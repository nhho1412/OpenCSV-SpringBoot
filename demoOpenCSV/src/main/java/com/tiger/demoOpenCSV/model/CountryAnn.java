package com.tiger.demoOpenCSV.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByPosition;

@Entity
@Table(name="COUNTRY_ANN")
public class CountryAnn {
	@CsvBindByPosition(position = 0)
	@Id
	private int id;

	@CsvBindByPosition(position = 1)
	@Column(name="Code", length=10, nullable = false)
	private String code;

	@CsvBindByPosition(position = 2)
	@Column(name="Name", length=10, nullable = false)
	private String name;

	public CountryAnn() {
		super();
	}

	public CountryAnn(int id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", code=" + code + ", name=" + name + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
