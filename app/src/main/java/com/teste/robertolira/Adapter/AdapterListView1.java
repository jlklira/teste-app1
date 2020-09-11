package com.teste.robertolira.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.teste.robertolira.Classe.ClassItem;
import com.teste.robertolira.R;

import java.util.ArrayList;

public class AdapterListView1 extends ArrayAdapter<ClassItem> {

    private Context context;
    private ArrayList<ClassItem> model;
    private int textColor;

    public AdapterListView1(Context context, ArrayList<ClassItem> objects) {
        super(context, 0, objects);
        this.context = context;
        this.model = objects;
    }


    TextView txt_tfvname, txt_botname, txt_othname;
    ImageView img_imageURL;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_items, null);

        txt_tfvname = (TextView) convertView.findViewById(R.id.listview_items_tfvname);
        txt_botname = (TextView) convertView.findViewById(R.id.listview_items_botname);
        txt_othname = (TextView) convertView.findViewById(R.id.listview_items_othname);
        img_imageURL = (ImageView) convertView.findViewById(R.id.listview_items_imageurl);

        ClassItem model = this.model.get( position );

        txt_tfvname.setText(model.getTfvname());
        txt_botname.setText(model.getBotname());
        txt_othname.setText(model.getOthname());

        Picasso.with(context).load(model.getImageURL()).into(img_imageURL);

        return convertView;
    }
}
