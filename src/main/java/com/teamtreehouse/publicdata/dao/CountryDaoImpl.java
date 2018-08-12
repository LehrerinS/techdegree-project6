package com.teamtreehouse.publicdata.dao;

import com.teamtreehouse.publicdata.model.Country;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class CountryDaoImpl implements CountryDao {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public Country findCountryByName(String name){
        Session session = sessionFactory.openSession();
        Country country = session.get(Country.class, name);
        session.close();
        return country;
    }

    @Override
    public Country findCountryByCode(String code){
        Session session = sessionFactory.openSession();
        Country country = session.get(Country.class, code);
        session.close();
        return country;
    }

    @Override
    public void delete(Country country){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(country);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Country country){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(country);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Country> fetchAllCountries(){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Country.class);
        List<Country> countries = criteria.list();
        session.close();
        return countries;
    }

    @Override
    public void save(Country country){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        System.out.println("Following object will be saved into the DB");
        System.out.println(country.toString());
        session.save(country);
        session.getTransaction().commit();
        session.close();
    }
}
