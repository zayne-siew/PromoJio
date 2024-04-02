package com.example.promojio.view.promocode;

public class promocode_model {
    //the offer: free,off, off second pair
    //the name of the product on promotion
    //the amt of promotion and discount
    int brand;
    String brandname;
    String expirydate;
    String discountvalue;

    String discountdescription;


    public promocode_model(int brand, String brandname, String expirydate, String discountdescription, String discountvalue) {
        this.brand=brand;
        this.discountvalue = discountvalue;
        this.expirydate = expirydate;
        this.discountdescription = discountdescription;
        this.brandname = brandname;
    }


    public int getBrand() {
        return brand;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public String getDiscountvalue() {
        return discountvalue;
    }

    public String getDiscountdescription() {
        return discountdescription;
    }

    public String getBrandname() {
        return brandname;
    }



}
