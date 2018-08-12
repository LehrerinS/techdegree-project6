package com.teamtreehouse.publicdata.controller;

import com.teamtreehouse.publicdata.dao.CountryDao;
import com.teamtreehouse.publicdata.dao.CountryDaoImpl;
import com.teamtreehouse.publicdata.model.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PrompterLogic {

    private CountryDao countryDao;
    private List<Country> allCountries;
    private List<Country> allNonEmptyCountries;
    private CorrelationCoeficient correlationCoeficient;
    private BufferedReader bufferedReader;

    public PrompterLogic(){
        countryDao = new CountryDaoImpl();
        allCountries = countryDao.fetchAllCountries();
        allNonEmptyCountries = allCountriesWithoutEmptyFields();
        correlationCoeficient = new CorrelationCoeficient();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }


    public void fetchAllCountries(){
        List<Country> nonSortedCountries = allCountries;
//                .stream().sorted((o1, o2) -> o1.getLiteracy().compareTo(o2.getLiteracy())).collect(Collectors.toList());
        System.out.print(String.format("%5s %35s %20s %20s\n","code","Country","Internet Users","Literacy"));
        System.out.print("-----------------------------------------------------------------------------------\n");
        nonSortedCountries.forEach(System.out::println);
    }

    public List<Country> allCountriesWithoutEmptyFields(){
        System.out.println("PRINT ALL COUNTRIES WHERE THERE IS NO EMPTY FIELDS");
        List<Country> allNonEmptyList = allCountries
                .stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .filter(country -> country.getInternetUsers() != null)
                .collect(Collectors.toList());
        allNonEmptyList.forEach(System.out::println);
//        System.out.println("this is the size " + allNonEmptyList.size());
        return allNonEmptyList;
    }

    public void sortCountriesByLiteracy(){
        System.out.println("SORTED BY LITERACY");
        List<Country> sortedByLiteracy = allCountries
                .stream()
                .filter(country -> country.getAdultLiteracyRate() !=null)
                .sorted((o1, o2) -> o1.getAdultLiteracyRate().compareTo(o2.getAdultLiteracyRate()))
                .collect(Collectors.toList());
        sortedByLiteracy.forEach(System.out::println);
    }

    public void sortCountriesByInternetUsers(){
        System.out.println("SORTED BY INTERNET USERS");
        List<Country> sortedByInternetUsers = allCountries
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .sorted((o1, o2) -> o1.getInternetUsers().compareTo(o2.getInternetUsers()))
                .collect(Collectors.toList());
        sortedByInternetUsers.forEach(System.out::println);
    }

    public void getCountryWithMaxLiteracy(){
        Country maxLiteracy = allCountries
                .stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .max((o1, o2) -> o1.getAdultLiteracyRate().compareTo(o2.getAdultLiteracyRate()))
                .get();
        System.out.println(maxLiteracy);
    }

    public void getCountryWithMinLiteracy(){
        Country minLiteracy = allCountries
                .stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .min((o1, o2) -> o1.getAdultLiteracyRate().compareTo(o2.getAdultLiteracyRate()))
                .get();
        System.out.println(minLiteracy);
    }

    public void getCountryWithMaxInternetUsers(){
        Country maxInternetUsers = allCountries
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .max((o1, o2) -> o1.getInternetUsers().compareTo(o2.getInternetUsers()))
                .get();
        System.out.println(maxInternetUsers);
    }

    public void getCountryWithMinInternetUsers(){
        Country minInternetUsers = allCountries
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .min((o1, o2) -> o1.getInternetUsers().compareTo(o2.getInternetUsers()))
                .get();
        System.out.println(minInternetUsers);
    }

    public void calculateCorrelationCoeficient(){
//        System.out.println("now we print BigDecimal");
        correlationCoeficient.correlationCoefficient(allCountries, allNonEmptyCountries);
        correlationCoeficient.formulaForSummOfX(allCountries, allNonEmptyCountries);
        correlationCoeficient.formulaForSummOfY(allCountries, allNonEmptyCountries);
        correlationCoeficient.formulaForSummOfXY(allCountries, allNonEmptyCountries);
        correlationCoeficient.formulaForSummX2(allCountries, allNonEmptyCountries);
        correlationCoeficient.formulaForSummY2(allCountries, allNonEmptyCountries);

        System.out.println("Here is correlation coefficient result");
        correlationCoeficient.nominator(allCountries, allNonEmptyCountries);
        correlationCoeficient.denominator(allCountries, allNonEmptyCountries);
        System.out.println(correlationCoeficient.finalFormula(allCountries, allNonEmptyCountries));
    }

    public void addCountry() throws IOException {
        String code = "";
        System.out.println("Please add country name");
        String name = bufferedReader.readLine().trim();
        if (countryDao.findCountryByName(name)!=null){
            System.out.println("Country Name already exist");
        } else {
            System.out.println("Please add country code with max three characters all upper cases");
            code = bufferedReader.readLine().trim().toUpperCase();
            while (countryDao.findCountryByCode(code)!=null){
                System.out.println("The country code is longer than three characters or country code already exist, please check.");
                code = bufferedReader.readLine().trim().toUpperCase();
            }
            while (code.length()!=3){
                System.out.println("The country code is longer than three characters or country code already exist, please check.");
                code = bufferedReader.readLine().trim().toUpperCase();
            }
            System.out.println("Please enter the value for percentage of internet users or just write - to have no value");
            String percentageOfInternetUsers = bufferedReader.readLine().trim();
//            if ((percentageOfInternetUsers = bufferedReader.readLine()) != null) {
//                percentageOfInternetUsers = bufferedReader.readLine().trim();
//            }
            System.out.println("Please enter the value for percentage of literacy rate or just write - to have no value");
            String percentageOfLiteracyRate = bufferedReader.readLine().trim();

            Country.CountryBuilder countryBuilder = new Country.CountryBuilder(code,name);
            if(!percentageOfInternetUsers.equals("-")){
                countryBuilder.withInternetUsers(new BigDecimal(percentageOfInternetUsers));
            }
            if(!percentageOfLiteracyRate.equals("-")){
                countryBuilder.withLiteracy(new BigDecimal(percentageOfLiteracyRate));
            }
            countryBuilder.build();
            Country country = new Country(countryBuilder);
            countryDao.save(country);
            allCountries = countryDao.fetchAllCountries();
    }
    }

    public void editCountry() throws IOException {
        System.out.println("Please enter the country code you want to edit");
        String code = bufferedReader.readLine().trim();
        Country country = countryDao.findCountryByCode(code);
        if (country==null){
            System.out.println("Country with a given code doesn't exist");
        } else {
            System.out.println("Please provide new country name or type NO if you don't want to edit the name");
            String name = bufferedReader.readLine().trim();
            if (!name.toUpperCase().equals("NO")){
                country.setName(name);
            }
            System.out.println("Please provide percentage of internet users in the country or type NO if you don't want to edit it");
            String percentageOfInternetUsers = bufferedReader.readLine().trim();
            if (!percentageOfInternetUsers.toUpperCase().equals("NO")){
                country.setInternetUsers(new BigDecimal(percentageOfInternetUsers));
            }
            System.out.println("Please provide percentage of literacy in the country or type NO if you don't want to edit it");
            String percentageOfLiteracy = bufferedReader.readLine().trim();
            if (!percentageOfLiteracy.toUpperCase().equals("NO")){
                country.setAdultLiteracyRate(new BigDecimal(percentageOfLiteracy));
            }
            countryDao.update(country);
            allCountries = countryDao.fetchAllCountries();
        }
    }

    public void deleteCountry() throws IOException {
        System.out.println("Please enter the country code you want to delete");
        String code = bufferedReader.readLine().trim();
        Country country = countryDao.findCountryByCode(code);
        if (country==null){
            System.out.println("Country with a given code doesn't exist");
        } else {
            System.out.println("Are you sure you want to delete specified country from the DB. If yes type YES");
            String answer = bufferedReader.readLine().trim().toUpperCase();
            if(answer.equals("YES")){
                countryDao.delete(country);
                allCountries = countryDao.fetchAllCountries();
            }
        }
    }


}
