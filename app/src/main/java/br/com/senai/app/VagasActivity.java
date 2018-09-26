package br.com.senai.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 16255204 on 17/05/2018.
 */

public class VagasActivity extends AppCompatActivity {

    EditText palavra_chave;
    ListView list_vagas;
    ArrayAdapter<Vaga> adapterVagas;
    String API_URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão voltar
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão voltar
        getSupportActionBar().setTitle("Vagas de Emprego");

        setContentView(R.layout.activity_vagas);

        API_URL = getString(R.string.api_key);

        palavra_chave = findViewById(R.id.vagas_palavrachave);
        list_vagas = findViewById(R.id.vagas_list);


        //Popula list_view
        adapterVagas = new VagaAdapter( this , new ArrayList<Vaga>());
        list_vagas.setAdapter( adapterVagas );

        new popularVagas().execute();
        //Listener para a palavra chave
        palavra_chave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                new buscarVagas().execute();            }
        });

        //listener da lista
        list_vagas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Vaga vaga = (Vaga) adapterView.getItemAtPosition( i );

            Intent intent = new Intent( getApplicationContext() , DetalheVagaActivity.class);
            intent.putExtra("idVaga" , vaga.getIdVaga() );
            startActivity( intent );

            }
        });

    }

    public class buscarVagas extends AsyncTask<Void , Void , Void > {

        String retornoApi;
        ArrayList<Vaga> vagas = new ArrayList<>();
        String palavra = palavra_chave.getText().toString();
        @Override
        protected Void doInBackground(Void... voids) {

            retornoApi = Http.get( API_URL + "/hackathon/api/vagas.php?buscar=" + palavra);


            try{


                JSONArray array_vagas = new JSONArray( retornoApi );

                for( int i = 0 ; i < array_vagas.length() ; i++ ){

                    JSONObject obj = array_vagas.getJSONObject( i );

                    Vaga vaga = new Vaga();

                    vaga.setEmpresa( obj.getString("empresa") );
                    vaga.setCargo( obj.getString("cargo"));
                    vaga.setLocalTrabalho(obj.getString("LocalTrabalho"));
                    vaga.setIdVaga( obj.getInt("idVaga") );

                    vagas.add( vaga );
                }


            }catch (Exception e){}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterVagas.clear();
            adapterVagas.addAll(vagas);
        }
    }

    public class popularVagas extends AsyncTask<Void , Void , Void > {

        String retornoApi;
        ArrayList<Vaga> vagas = new ArrayList<>();
        @Override
        protected Void doInBackground(Void... voids) {

            retornoApi = Http.get( API_URL + "/hackathon/api/vagas.php");


            try{


                JSONArray array_vagas = new JSONArray( retornoApi );

                for( int i = 0 ; i < array_vagas.length() ; i++ ){

                    JSONObject obj = array_vagas.getJSONObject( i );

                    Vaga vaga = new Vaga();

                    vaga.setEmpresa( obj.getString("empresa") );
                    vaga.setCargo( obj.getString("cargo"));
                    vaga.setLocalTrabalho(obj.getString("LocalTrabalho"));
                    vaga.setIdVaga( obj.getInt("idVaga") );

                    vagas.add( vaga );
                }


            }catch (Exception e){}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapterVagas.addAll(vagas);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //btn voltar
                startActivity(new Intent(this, MainActivity.class));
                finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

}
