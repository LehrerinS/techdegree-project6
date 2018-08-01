package com.teamtreehouse.publicdata.view;

import com.teamtreehouse.publicdata.model.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Prompter {

    private BufferedReader bufferedReader;
    private Map<String, String> menu;
    private Country country;


    public Prompter(){
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        country = new Country();
        menu = new HashMap<String, String>();
        menu.put("1", "List All Countries");
        menu.put("2", "Sort Countries by Literacy");
        menu.put("3", "Sort Countries by Internet Users");
        menu.put("4", "Delete Country");
        menu.put("5", "Add Country");
        menu.put("6", "Update Country");
        menu.put("7", "Exit menu");
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
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "5":
                        break;
                    case "6":
                        System.out.println("You have chosen to exit program, thanks for playing.");
                        break;
                    default:
                        System.out.printf("Unknown choice: '%s'. Try again %n%n%n", choice);
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input");
                ioe.printStackTrace();
            }
        } while (!choice.equals("7"));
    }



}
