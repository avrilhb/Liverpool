package com.example.liverpooltest.entity;

import java.util.ArrayList;

public class Record {
    public String productDisplayName;
    public double listPrice;
    public double promoPrice;
    public String smImage;
    public ArrayList<VariantsColor> variantsColor;

    public String getProductDisplayName() {
        return productDisplayName;
    }

    public void setProductDisplayName(String productDisplayName) {
        this.productDisplayName = productDisplayName;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public double getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(double promoPrice) {
        this.promoPrice = promoPrice;
    }

    public String getSmImage() {
        return smImage;
    }

    public void setSmImage(String smImage) {
        this.smImage = smImage;
    }

    public ArrayList<VariantsColor> getVariantsColor() {
        return variantsColor;
    }

    public void setVariantsColor(ArrayList<VariantsColor> variantsColor) {
        this.variantsColor = variantsColor;
    }
}
