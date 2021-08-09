package com.epam.brest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {

    /**
     * List saves beverages, that Client selected
     */
    private List<Beverage> selectedBeverages;

    /**
     * Price for all selected beverages
     */
    private double totalPrice;

    /**
     * Count of money, that Client has
     */
    private double clientCredit;

    public Client() {
        this.selectedBeverages = new ArrayList<>();
        this.totalPrice = 0.0;
        this.clientCredit = 0.0;
    }

    public List<Beverage> getSelectedBeverages() {
        return selectedBeverages;
    }

    public void setSelectedBeverages(List<Beverage> selectedBeverages) {
        this.selectedBeverages = selectedBeverages;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getClientCredit() {
        return clientCredit;
    }

    public void setClientCredit(double clientCredit) {
        this.clientCredit = clientCredit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Double.compare(client.totalPrice, totalPrice) == 0 && Double.compare(client.clientCredit, clientCredit) == 0 && Objects.equals(selectedBeverages, client.selectedBeverages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectedBeverages, totalPrice, clientCredit);
    }

    @Override
    public String toString() {
        return "Client{" +
                "selectedBeverages=" + selectedBeverages +
                ", totalPrice=" + totalPrice +
                ", clientCredit=" + clientCredit +
                '}';
    }
}
