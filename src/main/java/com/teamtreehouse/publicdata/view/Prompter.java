package com.teamtreehouse.publicdata.view;

import com.teamtreehouse.publicdata.controller.PrompterLogic;
import com.teamtreehouse.publicdata.dao.CountryDao;
import com.teamtreehouse.publicdata.dao.CountryDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Prompter {

    private BufferedReader bufferedReader;
    private Map<String, String> menu;
    private PrompterLogic prompterLogic;
    private CountryDao dao = new CountryDaoImpl();


    public Prompter(){
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        prompterLogic = new PrompterLogic();
        menu = new HashMap<String, String>();
        menu.put("1", "List All Countries");
        menu.put("2", "List All Non Empty Countries");
        menu.put("3", "Sort Countries By Literacy");
        menu.put("4", "Sort Countries By Internet Users");
//        menu.put("5", "Get Country With Max Literacy");
//        menu.put("6", "Get Country With Min Literacy");
//        menu.put("7", "Get Country With Max Internet Users");
//        menu.put("8", "Get Country With Min Internet Users");
        menu.put("5", "Calculate Correlation Coeficient");
        menu.put("6", "Add Country");
        menu.put("7", "Update Country");
        menu.put("8", "Delete Country");
        menu.put("9", "Exit menu");
    }

    private String promptAction() throws IOException {
        System.out.println("%nMenu: %n");
        for(Map.Entry<String, String> option  : menu.entrySet()){
            System.out.printf("%s - %s %n", option.getKey(), option.getValue());
        }
        String answer = bufferedReader.readLine();
        return answer.trim().toLowerCase();
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "1":
                        prompterLogic.fetchAllCountries();
                        break;
                    case "2":
                        prompterLogic.allCountriesWithoutEmptyFields();
                        break;
                    case "3":
                        prompterLogic.sortCountriesByLiteracy();
                        break;
                    case "4":
                        prompterLogic.sortCountriesByInternetUsers();
                        break;
//                    case "5":
//                        prompterLogic.getCountryWithMaxLiteracy();
//                        break;
//                    case "6":
//                        prompterLogic.getCountryWithMinLiteracy();
//                        break;
//                    case "7":
//                        prompterLogic.getCountryWithMaxInternetUsers();
//                        break;
//                    case "8":
//                        prompterLogic.getCountryWithMinInternetUsers();
//                        break;
                    case "5":
                        prompterLogic.calculateCorrelationCoeficient();
                        break;
                    case "6":
                        prompterLogic.addCountry();
                        break;
                    case "7":
                        prompterLogic.editCountry();
                        break;
                    case "8":
                        prompterLogic.deleteCountry();
                    case "9":
                        System.out.println("You have chosen to exit program, thanks for playing.");
                        break;
                    default:
                        System.out.printf("Unknown choice: '%s'. Try again %n%n%n", choice);
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input");
                ioe.printStackTrace();
            }
        } while (!choice.equals("9"));
    }



}
