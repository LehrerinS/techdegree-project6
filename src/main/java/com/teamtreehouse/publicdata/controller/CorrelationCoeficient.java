package com.teamtreehouse.publicdata.controller;

import com.teamtreehouse.publicdata.model.Country;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CorrelationCoeficient {

    public BigDecimal finalFormula(List<Country> allCountries, List<Country> nonEmptyCountries){
        BigDecimal fraction;
        fraction = nominator(allCountries, nonEmptyCountries).divide(denominator(allCountries, nonEmptyCountries), 2, RoundingMode.HALF_UP);
        return fraction;
    }

    public BigDecimal denominator(List<Country> allCountries, List<Country> nonEmptyCountries){
        BigDecimal denominatorBeforeSquareRoot1;
        BigDecimal denominatorBeforeSquareRoot2;
        BigDecimal denominatorBeforeSquareRoot3;
        BigDecimal denominator;
        BigDecimal denominatorFirstPart;
        BigDecimal denominatorSecondPart;
        BigDecimal denominatorThirdPart;
        BigDecimal denominatorFourthPart;
        denominatorFirstPart = formulaForSummX2(allCountries, nonEmptyCountries).multiply(BigDecimal.valueOf(nonEmptyCountries.size()));
        denominatorSecondPart = formulaForSummOfX(allCountries, nonEmptyCountries).multiply(formulaForSummOfX(allCountries, nonEmptyCountries));
        denominatorThirdPart = formulaForSummY2(allCountries, nonEmptyCountries).multiply(BigDecimal.valueOf(nonEmptyCountries.size()));
        denominatorFourthPart = formulaForSummOfY(allCountries, nonEmptyCountries).multiply(formulaForSummOfY(allCountries, nonEmptyCountries));
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

    public BigDecimal nominator(List<Country> allCountries, List<Country> nonEmptyCountries){
        BigDecimal nominator;
        BigDecimal nominatorFirstPart;
        BigDecimal nominatorSecondPart;
        nominatorFirstPart = formulaForSummOfXY(allCountries, nonEmptyCountries).multiply(BigDecimal.valueOf(nonEmptyCountries.size()));
        nominatorSecondPart = formulaForSummOfX(allCountries, nonEmptyCountries).multiply(formulaForSummOfY(allCountries, nonEmptyCountries));
        nominator = nominatorFirstPart.subtract(nominatorSecondPart);
        return nominator;
    }

    public BigDecimal formulaForSummOfX(List<Country> allCountries, List<Country> nonEmptyCountries){
        BigDecimal result = new BigDecimal(0);
        for (Country country : allCountries){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getInternetUsers());
            }
        }
        return result;
    }

    public BigDecimal formulaForSummOfY(List<Country> allCountries, List<Country> nonEmptyCountries){
        BigDecimal result = new BigDecimal(0);
        for (Country country : allCountries){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getAdultLiteracyRate());
            }
        }
        return result;
    }

    public BigDecimal formulaForSummOfXY(List<Country> allCountries, List<Country> nonEmptyCountries){
        BigDecimal result = new BigDecimal(0);
        for (Country country : allCountries){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getInternetUsers().multiply(country.getAdultLiteracyRate()));
            }
        }
        return result;
    }

    public BigDecimal formulaForSummX2(List<Country> allCountries, List<Country> nonEmptyCountries){
        BigDecimal result = new BigDecimal(0);
        for (Country country : allCountries){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getInternetUsers().multiply(country.getInternetUsers()));
            }
        }
        return result;
    }

    public BigDecimal formulaForSummY2(List<Country> allCountries, List<Country> nonEmptyCountries){
        BigDecimal result = new BigDecimal(0);
        for (Country country : allCountries){
            if(country.getAdultLiteracyRate()!=null && country.getInternetUsers()!=null){
                result = result.add(country.getAdultLiteracyRate().multiply(country.getAdultLiteracyRate()));
            }
        }
        return result;
    }

    public BigDecimal correlationCoefficient(List<Country> allCountries, List<Country> nonEmptyCountries){
        BigDecimal result = new BigDecimal(0);
        BigDecimal sumInternetUsers = new BigDecimal(0);
        BigDecimal sumLiteracy = new BigDecimal(0);
        for (int i=0; i<allCountries.size(); i++){
            if(allCountries.get(i).getInternetUsers()!=null){
                sumInternetUsers = sumInternetUsers.add(allCountries.get(i).getInternetUsers());
                result = result.add(allCountries.get(i).getInternetUsers());
            }
            if(allCountries.get(i).getAdultLiteracyRate()!=null){
                result = result.add(allCountries.get(i).getAdultLiteracyRate());
            }
        };
//        System.out.println("Here is the result of correlation coefficient");
//        System.out.println(result);
        result = result.multiply(BigDecimal.valueOf(allCountries.size()));
        return result;
    }
}
