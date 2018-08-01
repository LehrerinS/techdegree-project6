package com.teamtreehouse.publicdata.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String code;

    @Column
    private String name;
    @Column
    BigDecimal internetUsers;
    @Column
    BigDecimal adultLiteracyRate;

    public Country(){}

    public Country(CountryBuilder builder) {
        this.name = name;
        this.internetUsers = internetUsers;
        this.adultLiteracyRate = adultLiteracyRate;
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

    public BigDecimal getInternetUsers() {
        return internetUsers;
    }

    public void setInternetUsers(BigDecimal internetUsers) {
        this.internetUsers = internetUsers;
    }

    public BigDecimal getAdultLiteracyRate() {
        return adultLiteracyRate;
    }

    public void setAdultLiteracyRate(BigDecimal adultLiteracyRate) {
        this.adultLiteracyRate = adultLiteracyRate;
    }

    @Override
    public String toString() {
        String toReturn = "";
        if(internetUsers==null && adultLiteracyRate==null){
            toReturn = String.format("%5s %35s %20s %20s",code, name, "--", "--");
        } else if (internetUsers==null){
            toReturn = String.format("%5s %35s %20s %20.2f",code, name, "--", adultLiteracyRate);
        } else if (adultLiteracyRate==null){
            toReturn = String.format("%5s %35s %20.2f %20s",code, name, internetUsers, "--");
        } else {
            toReturn = String.format("%5s %35s %20.2f %20.2f",code, name, internetUsers, adultLiteracyRate);
        }
        return toReturn;
    }

    public static class CountryBuilder{
        private String name;
        private double internetUsers;
        private double adultLiteracyRate;

        public CountryBuilder(String name){
            this.name = name;
        }

        public CountryBuilder withInternetUsers(double internetUsers){
            this.internetUsers = internetUsers;
            return this;
        }

        public CountryBuilder withLiteracy(double adultLiteracyRate){
            this.adultLiteracyRate = adultLiteracyRate;
            return this;
        }

        public Country build(){
            return new Country(this);
        }
    }

}
