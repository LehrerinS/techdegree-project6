package com.teamtreehouse.publicdata;

import com.teamtreehouse.publicdata.model.Country;
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

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args){

        List<Country> nonSortedCountries = fetchAllCountries();
//                .stream().sorted((o1, o2) -> o1.getLiteracy().compareTo(o2.getLiteracy())).collect(Collectors.toList());
        System.out.print(String.format("%5s %35s %20s %20s\n","code","Country","Internet Users","Literacy"));
        System.out.print("-----------------------------------------------------------------------------------\n");
        nonSortedCountries.forEach(System.out::println);

        System.out.println("PRINT ALL COUNTRIES WHERE THERE IS NO EMPTY FIELDS");
        List<Country> allNonEmptyList = fetchAllCountries()
                .stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .filter(country -> country.getInternetUsers() != null)
                .collect(Collectors.toList());
        allNonEmptyList.forEach(System.out::println);
        System.out.println("this is the size " + allNonEmptyList.size());

        System.out.println("SORTED BY LITERACY");

        List<Country> sortedByLiteracy = fetchAllCountries()
                .stream()
                .filter(country -> country.getAdultLiteracyRate() !=null)
                .sorted((o1, o2) -> o1.getAdultLiteracyRate().compareTo(o2.getAdultLiteracyRate()))
                .collect(Collectors.toList());
        sortedByLiteracy.forEach(System.out::println);

        System.out.println("SORTED BY INTERNET USERS");
        List<Country> sortedByInternetUsers = fetchAllCountries()
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .sorted((o1, o2) -> o1.getInternetUsers().compareTo(o2.getInternetUsers()))
                .collect(Collectors.toList());
        sortedByInternetUsers.forEach(System.out::println);

        Country maxLiteracy =    fetchAllCountries()
                .stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .max((o1, o2) -> o1.getAdultLiteracyRate().compareTo(o2.getAdultLiteracyRate()))
                .get();
        System.out.println(maxLiteracy);

        Country minLiteracy = fetchAllCountries()
                .stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .min((o1, o2) -> o1.getAdultLiteracyRate().compareTo(o2.getAdultLiteracyRate()))
                .get();
        System.out.println(minLiteracy);

        Country maxInternetUsers = fetchAllCountries()
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .max((o1, o2) -> o1.getInternetUsers().compareTo(o2.getInternetUsers()))
                .get();
        System.out.println(maxInternetUsers);

        Country minInternetUsers = fetchAllCountries()
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .min((o1, o2) -> o1.getInternetUsers().compareTo(o2.getInternetUsers()))
                .get();
        System.out.println(minInternetUsers);

//        BigDecimal test = new BigDecimal(5);
//        System.out.println(sqrt(test));

        System.out.println("now we print BigDecimal");
        System.out.println(correlationCoefficient());
        System.out.println(formulaForSummOfX());
        System.out.println(formulaForSummOfY());
        System.out.println(formulaForSummOfXY());
        System.out.println(formulaForSummX2());
        System.out.println(formulaForSummY2());

        System.out.println("now calculated part");
        System.out.println(nominator());
        System.out.println(denominator());
        System.out.println(finalFormula());

    }

    private static List<Country> allNonEmptyCountries(){
        System.out.println("PRINT ALL COUNTRIES WHERE THERE IS NO EMPTY FIELDS");
        List<Country> allNonEmptyList = fetchAllCountries()
                .stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .filter(country -> country.getInternetUsers() != null)
                .collect(Collectors.toList());
        allNonEmptyList.forEach(System.out::println);
        System.out.println("this is the size " + allNonEmptyList.size());
        return allNonEmptyList;
    }

    private static BigDecimal finalFormula(){
        BigDecimal fraction;
        fraction = nominator().divide(denominator(), 2, RoundingMode.HALF_UP);
        return fraction;
    }

    private static BigDecimal denominator(){
        BigDecimal denominatorBeforeSquareRoot1;
        BigDecimal denominatorBeforeSquareRoot2;
        BigDecimal denominatorBeforeSquareRoot3;
        BigDecimal denominator;
        BigDecimal denominatorFirstPart;
        BigDecimal denominatorSecondPart;
        BigDecimal denominatorThirdPart;
        BigDecimal denominatorFourthPart;
        denominatorFirstPart = formulaForSummX2().multiply(BigDecimal.valueOf(allNonEmptyCountries().size()));
        denominatorSecondPart = formulaForSummOfX().multiply(formulaForSummOfX());
        denominatorThirdPart = formulaForSummY2().multiply(BigDecimal.valueOf(allNonEmptyCountries().size()));
        denominatorFourthPart = formulaForSummOfY().multiply(formulaForSummOfY());
        denominatorBeforeSquareRoot1 = denominatorFirstPart.subtract(denominatorSecondPart);
        denominatorBeforeSquareRoot2 = denominatorThirdPart.subtract(denominatorFourthPart);
        denominatorBeforeSquareRoot3 = denominatorBeforeSquareRoot1.multiply(denominatorBeforeSquareRoot2);
        denominator = sqrt(denominatorBeforeSquareRoot3);
        return denominator;
    }

    public static BigDecimal sqrt(BigDecimal value) {
        BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
//        return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0)));
        return x;
    }

    private static BigDecimal nominator(){
        BigDecimal nominator;
        BigDecimal nominatorFirstPart;
        BigDecimal nominatorSecondPart;
        nominatorFirstPart = formulaForSummOfXY().multiply(BigDecimal.valueOf(allNonEmptyCountries().size()));
        nominatorSecondPart = formulaForSummOfX().multiply(formulaForSummOfY());
        nominator = nominatorFirstPart.subtract(nominatorSecondPart);
        return nominator;
    }

    private static BigDecimal formulaForSummOfX(){
        BigDecimal result = new BigDecimal(0);
        for (Country country : fetchAllCountries()){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getInternetUsers());
            }
        }
        return result;
    }

    private static BigDecimal formulaForSummOfY(){
        BigDecimal result = new BigDecimal(0);
        for (Country country : fetchAllCountries()){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getAdultLiteracyRate());
            }
        }
        return result;
    }

    private static BigDecimal formulaForSummOfXY(){
        BigDecimal result = new BigDecimal(0);
        for (Country country : fetchAllCountries()){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getInternetUsers().multiply(country.getAdultLiteracyRate()));
            }
        }
        return result;
    }

    private static BigDecimal formulaForSummX2(){
        BigDecimal result = new BigDecimal(0);
        for (Country country : fetchAllCountries()){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getInternetUsers().multiply(country.getInternetUsers()));
            }
        }
        return result;
    }

    private static BigDecimal formulaForSummY2(){
        BigDecimal result = new BigDecimal(0);
        for (Country country : fetchAllCountries()){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getAdultLiteracyRate().multiply(country.getAdultLiteracyRate()));
            }
        }
        return result;
    }

    private static BigDecimal correlationCoefficient(){
        BigDecimal result = new BigDecimal(0);
        BigDecimal sumInternetUsers = new BigDecimal(0);
        BigDecimal sumLiteracy = new BigDecimal(0);
        for (int i=0; i<fetchAllCountries().size(); i++){
            if(fetchAllCountries().get(i).getInternetUsers()!=null){
                sumInternetUsers = sumInternetUsers.add(fetchAllCountries().get(i).getInternetUsers());
                result = result.add(fetchAllCountries().get(i).getInternetUsers());
//                BigDecimal valueOfInternetUsers = fetchAllCountries().get(i).getInternetUsers().doubleValue();
//                System.out.println("sanjaaaaaaaaaaaa");
//                System.out.println(fetchAllCountries().get(i).getInternetUsers().doubleValue());
//                System.out.println(fetchAllCountries().get(i).getInternetUsers());
//                System.out.println(fetchAllCountries().get(i));
//                result = result + valueOfInternetUsers;
            }
            if(fetchAllCountries().get(i).getAdultLiteracyRate()!=null){
                result = result.add(fetchAllCountries().get(i).getAdultLiteracyRate());
//                BigDecimal valueOfAdultLiteracyRate = fetchAllCountries().get(i).getAdultLiteracyRate().doubleValue();
//                result = result + valueOfAdultLiteracyRate;
            }
        };
        System.out.println("ovo je rezultat");
        System.out.println(result);
        result = result.multiply(BigDecimal.valueOf(fetchAllCountries().size()));
        return result;
    }

    private static Country findCountryById(int id){
        Session session = sessionFactory.openSession();
        Country country = session.get(Country.class, id);
        session.close();
        return country;
    }

    private static void delete(Country country){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(country);
        session.getTransaction().commit();
        session.close();
    }

    private static void update(Country country){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(country);
        session.getTransaction().commit();
        session.close();
    }

    private static List<Country> fetchAllCountries(){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Country.class);
        List<Country> countries = criteria.list();
        session.close();
        return countries;
    }

    private static int save(Country country){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int id = (int)session.save(country);
        session.getTransaction().commit();
        session.close();
        return id;
    }

}
