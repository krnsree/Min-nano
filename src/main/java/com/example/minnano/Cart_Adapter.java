package com.example.minnano;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cart_Adapter extends BaseAdapter {

    ArrayList<String> proNames;
    ArrayList<String> proPrice;
    ArrayList<String> proImage;
    LayoutInflater inflater;

    public Cart_Adapter(Context context, ArrayList<String> proNames, ArrayList<String> proPrice, ArrayList<String> proImage)
    {
        this.proNames=proNames;
        this.proPrice=proPrice;
        this.proImage=proImage;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return proNames.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image = convertView.findViewById(R.id.cartImage);
        TextView price = convertView.findViewById(R.id.cartPrice);
        TextView title = convertView.findViewById(R.id.cartTitle);
        image.setImageURI(Uri.parse(proImage.get(position)));
        price.setText(proPrice.get(position));
        title.setText(proImage.get(position));
        return convertView;
    }
}
