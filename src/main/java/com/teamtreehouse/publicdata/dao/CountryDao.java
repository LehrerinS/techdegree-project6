package com.teamtreehouse.publicdata.dao;

import com.teamtreehouse.publicdata.model.Country;

import java.util.List;

public interface CountryDao {
    Country findCountryByName(String name);
    Country findCountryByCode(String code);
    void delete(Country country);
    void update(Country country);
    void save(Country country);
    List<Country> fetchAllCountries();
}
