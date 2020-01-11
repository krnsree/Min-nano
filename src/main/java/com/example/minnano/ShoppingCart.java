package com.example.minnano;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class ShoppingCart extends Fragment {

    ListView cartList;

    public ShoppingCart() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        cartList=getView().findViewById(R.id.cartView);
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

}
