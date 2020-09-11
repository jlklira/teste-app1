package com.teste.robertolira.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teste.robertolira.R;

public class DetailActivity extends AppCompatActivity {

    TextView txt_tfvname;
    TextView txt_othname;
    TextView txt_botname;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);




        txt_tfvname = (TextView)findViewById(R.id.detail_tfvname);
        txt_othname = (TextView)findViewById(R.id.detail_othname);
        txt_botname = (TextView)findViewById(R.id.detail_botname);
        imageView = (ImageView)findViewById(R.id.detail_imageurl);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        int position  = params.getInt("position");


        txt_tfvname.setText( MainActivity.dataClassItem.get(position).getTfvname() );
        txt_othname.setText( MainActivity.dataClassItem.get(position).getOthname() );
        txt_botname.setText( MainActivity.dataClassItem.get(position).getBotname() );

        Picasso.with(getApplicationContext()).load(MainActivity.dataClassItem.get(position).getImageURL()).into(imageView);


    }
}
