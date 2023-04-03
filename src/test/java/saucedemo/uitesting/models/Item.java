package saucedemo.uitesting.models;

/**
 * Item class represents an item in the shop, as they appear in the
 * Inventory Page, the Cart Page, and the Checkout Step Two Page
 */
public class Item {
    private String name;
    private String description;
    private String price;
    private String buttonText;
    private String quantity;

    private Item(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.buttonText = builder.buttonText;
        this.quantity = builder.quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    /**
     * Get the price as a Double.
     *
     * @return the price of the item as a Double.
     */
    public Double getPriceAsDouble() {
        if (price == null){
            return null;
        }
        return Double.parseDouble(price.replace("$", ""));
    }

    public String getQuantity() {
        return quantity;
    }

    public String getButtonText() {
        return buttonText;
    }

    public static class Builder {
        private String name;
        private String description;
        private String price;
        private String buttonText;
        private String quantity;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(String price) {
            this.price = price;
            return this;
        }

        public Builder buttonText(String buttonText) {
            this.buttonText = buttonText;
            return this;
        }

        public Builder quantity(String quantity) {
            this.quantity = quantity;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}
