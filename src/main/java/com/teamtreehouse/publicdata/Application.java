package com.teamtreehouse.publicdata;

import com.teamtreehouse.publicdata.model.Country;
import com.teamtreehouse.publicdata.view.Prompter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args){
        Prompter prompter = new Prompter();
        prompter.run();
    }

}
