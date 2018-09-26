package br.com.senai.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 16255204 on 17/05/2018.
 */

public class MainActivity extends AppCompatActivity {


    LinearLayout linearLayout_cursos , linearLayout_vagas;
    ListView list_noticias;
    ArrayAdapter<Noticia> adapterNoticia;
    String API_URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        API_URL = getString(R.string.api_key);


        linearLayout_cursos = findViewById(R.id.main_layout_cursos);
        linearLayout_vagas = findViewById(R.id.main_layout_vagas);
        list_noticias = findViewById(R.id.main_list_noticias);

        //Popula a list_view
        adapterNoticia = new NoticiaAdapter( this , new ArrayList<Noticia>() , API_URL );
        list_noticias.setAdapter( adapterNoticia );
        new popularNoticias().execute();
        //Fim popular lista

    }

    public class popularNoticias extends AsyncTask<Void , Void , Void >{

        String retornoApi;
        ArrayList<Noticia> noticias = new ArrayList<>();
        @Override
        protected Void doInBackground(Void... voids) {

            retornoApi = Http.get( API_URL + "/hackathon/api/noticias.php");


            try{


                JSONArray array_noticias = new JSONArray( retornoApi );

                for( int i = 0 ; i < array_noticias.length() ; i++ ){

                    JSONObject obj = array_noticias.getJSONObject( i );

                    Noticia noticia  = new Noticia();

                    noticia.setTitulo( obj.getString("titulo") );
                    noticia.setDescricao( obj.getString("descricao"));
                    noticia.setDtFim( obj.getString("dtFim"));
                    noticia.setUrlImagem( obj.getString("imagen"));

                    noticias.add(noticia);
                }


            }catch (Exception e){}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapterNoticia.addAll(noticias);
        }

    }

    public void cursos(View v ){
        startActivity( new Intent( this , CursosActivity.class) );
    }

    public void vagas( View v ){
        startActivity( new Intent( this , VagasActivity.class) );
    }

}
