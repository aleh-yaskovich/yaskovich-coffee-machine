package com.epam.brest.model;

import java.sql.Date;
import java.util.Objects;

public class Ingredient {

    private int ingredientId;
    private String ingredientTitle;
    private int ingredientQuantity;
    private Date ingredientExpirationDate;
    private Double ingredientPrice;
    private boolean ingredientRequired;

    public Ingredient() {

    }

    public Ingredient(
            int ingredientId, String ingredientTitle, int ingredientQuantity,
            Date ingredientExpirationDate, Double ingredientPrice, boolean ingredientRequired
    ) {
        this.ingredientId = ingredientId;
        this.ingredientTitle = ingredientTitle;
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientExpirationDate = ingredientExpirationDate;
        this.ingredientPrice = ingredientPrice;
        this.ingredientRequired = ingredientRequired;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientTitle() {
        return ingredientTitle;
    }

    public void setIngredientTitle(String ingredientTitle) {
        this.ingredientTitle = ingredientTitle;
    }

    public int getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(int ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public Date getIngredientExpirationDate() {
        return ingredientExpirationDate;
    }

    public void setIngredientExpirationDate(Date ingredientExpirationDate) {
        this.ingredientExpirationDate = ingredientExpirationDate;
    }

    public Double getIngredientPrice() {
        return ingredientPrice;
    }

    public void setIngredientPrice(Double ingredientPrice) {
        this.ingredientPrice = ingredientPrice;
    }

    public boolean isIngredientRequired() {
        return ingredientRequired;
    }

    public void setIngredientRequired(boolean ingredientRequired) {
        this.ingredientRequired = ingredientRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return ingredientId == that.ingredientId && ingredientQuantity == that.ingredientQuantity && ingredientRequired == that.ingredientRequired && Objects.equals(ingredientTitle, that.ingredientTitle) && Objects.equals(ingredientExpirationDate, that.ingredientExpirationDate) && Objects.equals(ingredientPrice, that.ingredientPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, ingredientTitle, ingredientQuantity, ingredientExpirationDate, ingredientPrice, ingredientRequired);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", ingredientTitle='" + ingredientTitle + '\'' +
                ", ingredientQuantity=" + ingredientQuantity +
                ", ingredientExpirationDate=" + ingredientExpirationDate +
                ", ingredientPrice=" + ingredientPrice +
                ", ingredientRequired=" + ingredientRequired +
                '}';
    }
}
