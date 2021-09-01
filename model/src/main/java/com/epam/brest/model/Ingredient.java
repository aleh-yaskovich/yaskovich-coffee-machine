package com.epam.brest.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Date;
import java.util.Objects;

public class Ingredient {

    @Schema(description = "Ingredient ID")
    private int ingredientId;
    @Schema(description = "Ingredient title")
    private String ingredientTitle;
    @Schema(description = "Quantity of ingredient in coffee-machine")
    private int ingredientQuantity;
    @Schema(description = "Expiration date of ingredient")
    private Date ingredientExpirationDate;
    @Schema(description = "Ingredients price per 1000 (grams, milliliters, pieces, etc.)")
    private Double ingredientPrice;
    @Schema(description = "The ingredient is always added or at the wish of the client")
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
