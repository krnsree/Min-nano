package com.example.minnano;

import android.net.Uri;


public class Item_Cell
{
    private String productTitle;
    private String productPrice;
    private Uri productImage;

    public Item_Cell() { }

    public String getProductTitle() { return productTitle; }

    public void setProductTitle(String productTitle) { this.productTitle = productTitle; }

    public String getProductPrice() { return productPrice; }

    public void setProductPrice(String productPrice) { this.productPrice = productPrice; }

    public Uri getProductImage() { return productImage; }

    public void setProductImage(Uri productImage) { this.productImage = productImage; }
}
