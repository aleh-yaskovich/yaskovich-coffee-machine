package com.epam.brest.model;

import java.util.Objects;

public class Beverage {

    private int beverageId;
    private String beverageTitle;
    private int beverageIngCoffee;
    private int beverageIngMilk;
    private int beverageIngChocolate;
    private int beverageIngWater;
    private boolean beverageIngSugar;
    private boolean beverageIngSyrup;
    private boolean beverageIngCinnamon;
    private double beveragePrice;
    private int beverageQuantity;
    private boolean beverageHidden;

    public Beverage() {
    }

    public Beverage(int beverageId, String beverageTitle, int beverageIngCoffee, int beverageIngMilk,
                    int beverageIngChocolate, int beverageIngWater, boolean beverageIngSugar, boolean beverageIngSyrup,
                    boolean beverageIngCinnamon, double beveragePrice, int beverageQuantity, boolean beverageHidden)
    {
        this.beverageId = beverageId;
        this.beverageTitle = beverageTitle;
        this.beverageIngCoffee = beverageIngCoffee;
        this.beverageIngMilk = beverageIngMilk;
        this.beverageIngChocolate = beverageIngChocolate;
        this.beverageIngWater = beverageIngWater;
        this.beverageIngSugar = beverageIngSugar;
        this.beverageIngSyrup = beverageIngSyrup;
        this.beverageIngCinnamon = beverageIngCinnamon;
        this.beveragePrice = beveragePrice;
        this.beverageQuantity = beverageQuantity;
        this.beverageHidden = beverageHidden;
    }

    public int getBeverageId() {
        return beverageId;
    }

    public void setBeverageId(int beverageId) {
        this.beverageId = beverageId;
    }

    public String getBeverageTitle() {
        return beverageTitle;
    }

    public void setBeverageTitle(String beverageTitle) {
        this.beverageTitle = beverageTitle;
    }

    public int getBeverageIngCoffee() {
        return beverageIngCoffee;
    }

    public void setBeverageIngCoffee(int beverageIngCoffee) {
        this.beverageIngCoffee = beverageIngCoffee;
    }

    public int getBeverageIngMilk() {
        return beverageIngMilk;
    }

    public void setBeverageIngMilk(int beverageIngMilk) {
        this.beverageIngMilk = beverageIngMilk;
    }

    public int getBeverageIngChocolate() {
        return beverageIngChocolate;
    }

    public void setBeverageIngChocolate(int beverageIngChocolate) {
        this.beverageIngChocolate = beverageIngChocolate;
    }

    public int getBeverageIngWater() {
        return beverageIngWater;
    }

    public void setBeverageIngWater(int beverageIngWater) {
        this.beverageIngWater = beverageIngWater;
    }

    public boolean isBeverageIngSugar() {
        return beverageIngSugar;
    }

    public void setBeverageIngSugar(boolean beverageIngSugar) {
        this.beverageIngSugar = beverageIngSugar;
    }

    public boolean isBeverageIngSyrup() {
        return beverageIngSyrup;
    }

    public void setBeverageIngSyrup(boolean beverageIngSyrup) {
        this.beverageIngSyrup = beverageIngSyrup;
    }

    public boolean isBeverageIngCinnamon() {
        return beverageIngCinnamon;
    }

    public void setBeverageIngCinnamon(boolean beverageIngCinnamon) {
        this.beverageIngCinnamon = beverageIngCinnamon;
    }

    public double getBeveragePrice() {
        return beveragePrice;
    }

    public void setBeveragePrice(double beveragePrice) {
        this.beveragePrice = beveragePrice;
    }

    public int getBeverageQuantity() {
        return beverageQuantity;
    }

    public void setBeverageQuantity(int beverageQuantity) {
        this.beverageQuantity = beverageQuantity;
    }

    public boolean isBeverageHidden() {
        return beverageHidden;
    }

    public void setBeverageHidden(boolean beverageHidden) {
        this.beverageHidden = beverageHidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beverage beverage = (Beverage) o;
        return beverageId == beverage.beverageId && beverageIngCoffee == beverage.beverageIngCoffee &&
                beverageIngMilk == beverage.beverageIngMilk && beverageIngChocolate == beverage.beverageIngChocolate &&
                beverageIngWater == beverage.beverageIngWater && beverageIngSugar == beverage.beverageIngSugar &&
                beverageIngSyrup == beverage.beverageIngSyrup && beverageIngCinnamon == beverage.beverageIngCinnamon &&
                Double.compare(beverage.beveragePrice, beveragePrice) == 0 &&
                beverageQuantity == beverage.beverageQuantity && beverageHidden == beverage.beverageHidden &&
                Objects.equals(beverageTitle, beverage.beverageTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beverageId, beverageTitle, beverageIngCoffee, beverageIngMilk, beverageIngChocolate,
                beverageIngWater, beverageIngSugar, beverageIngSyrup, beverageIngCinnamon, beveragePrice,
                beverageQuantity, beverageHidden);
    }

    @Override
    public String toString() {
        return "Beverage{" +
                "beverageId=" + beverageId +
                ", beverageTitle='" + beverageTitle + '\'' +
                ", beverageIngCoffee=" + beverageIngCoffee +
                ", beverageIngMilk=" + beverageIngMilk +
                ", beverageIngChocolate=" + beverageIngChocolate +
                ", beverageIngWater=" + beverageIngWater +
                ", beverageIngSugar=" + beverageIngSugar +
                ", beverageIngSyrup=" + beverageIngSyrup +
                ", beverageIngCinnamon=" + beverageIngCinnamon +
                ", beveragePrice=" + beveragePrice +
                ", beverageQuantity=" + beverageQuantity +
                ", beverageHidden=" + beverageHidden +
                '}';
    }
}
