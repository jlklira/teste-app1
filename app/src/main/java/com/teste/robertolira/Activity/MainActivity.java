package com.teste.robertolira.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.teste.robertolira.Adapter.AdapterListView1;
import com.teste.robertolira.Classe.ClassItem;
import com.teste.robertolira.Intel.DataSource;
import com.teste.robertolira.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearch;
    ListView listViewMain;

    public static ArrayList<ClassItem> dataClassItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearch = (EditText)findViewById(R.id.txtSearch);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        listViewMain = (ListView)findViewById(R.id.listaResultado);

        dataClassItem = new ArrayList<ClassItem>();

        GetAllContent("?search=all");



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtContent = edtSearch.getText().toString();
                Toast.makeText(getApplicationContext(), txtContent, Toast.LENGTH_LONG);

                if (txtContent.trim().isEmpty()) {
                    GetAllContent("?search=all");
                } else{
                    GetAllContent("?tfvitem=" + txtContent.trim());
                }



            }
        });




        listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataClassItem.get(position).getImageURL();


                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                Bundle params = new Bundle();
                params.putInt("position", position);
                intent.putExtras(params);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);


            }
        });



    }





    void GetAllContent(String paramUrl){


        AsyncTask<String, String, JSONArray> teste = new JSONTask().execute( paramUrl );

        String strTXT = "";

        dataClassItem = new ArrayList<ClassItem>();

        try {

            for (int l=0; l<teste.get().length(); l++){

                String linha = teste.get().getString(l);
                // Log.d("Resultado",linha);

                JSONObject jsonObject = new JSONObject(linha);  // NOME DA CHAVE COM BASE NO INDEX l
                // String node1 = jsonObject.names().get(1).toString();
                // Log.d("Resultado", node1);

                String valorChave = jsonObject.get("tfvname").toString();   // VALOR DA CHAVE ESPECIFICADA, NO CASO: "tfvname"
                Log.d("Resultado2", "tfvname: " +  valorChave);

                ClassItem recorItem = new ClassItem();
                recorItem.setTfvname( jsonObject.get("tfvname").toString() );
                recorItem.setBotname( jsonObject.get("botname").toString() );
                recorItem.setOthname( jsonObject.get("othname").toString() );
                recorItem.setImageURL( jsonObject.get("imageurl").toString() );

                dataClassItem.add( recorItem );



                strTXT += valorChave + "\n";

                // JSONArray jsonArray = jsonObject.getJSONArray("tfvname");

                // Log.d("Resultado","tfvname: " + jsonArray.get(0).toString());

            }
            Log.d("Resultado", "FOI");


            ScreenRefresh();



        } catch (Exception e) {

        }



    }



    void ScreenRefresh() {

        String strTXT = "";

        for (int i=0; i<dataClassItem.size(); i++) {
            strTXT += dataClassItem.get(i).getTfvname() + " " + dataClassItem.get(i).getOthname() + "\n";
        }

        // txtView.setText(strTXT);


        // listaResultado = new ListView(getApplicationContext());
        AdapterListView1 adapter = new AdapterListView1(getApplicationContext(), dataClassItem);
        listViewMain.setAdapter(adapter);

    }


    public class JSONTask extends AsyncTask<String, String, JSONArray> {

        @SuppressLint("WrongThread")
        @Override
        protected JSONArray doInBackground(String... strings) {

            JSONArray resultado = null;

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                String testes = strings[0];

                String urlTeste = DataSource.URL_SITE + strings[0];  //  "http://tropicalfruitandveg.com/api/tfvjsonapi.php?search=all";

                URL url = new URL(urlTeste);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream stream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }


                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");

                resultado = parentArray;

                Log.d("Resultado","Linhas retornadas " + parentArray.length());



            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Resultado", "FALHA EM 127");
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
        }
    }


}
