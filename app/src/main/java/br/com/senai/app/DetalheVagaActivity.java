package br.com.senai.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 16255204 on 17/05/2018.
 */

public class DetalheVagaActivity  extends AppCompatActivity{

    String API_URL;

    int idVaga;

    TextView data , empresa , cargo , local, horario , salario, requisitos , perfil;
    CheckBox valeTransporte , valeAlimentacao , cestaBasica , seguroVida, assistMedica;
    TextView telefone , email , endereco , obs;

    LinearLayout parteBeneficios;
    TextView beneficiosExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vaga_detalhe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão voltar
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão voltar
        getSupportActionBar().setTitle("Vaga de Emprego");

        API_URL = getString(R.string.api_key);

        idVaga = getIntent().getIntExtra("idVaga" , 0);

        data = findViewById(R.id.vaga_detalhe_data);
        empresa = findViewById(R.id.vaga_detalhe_empresa);
        cargo = findViewById(R.id.vaga_detalhe_cargo);
        local = findViewById(R.id.vaga_detalhe_local);
        horario = findViewById(R.id.vaga_detalhe_horario);
        salario = findViewById(R.id.vaga_detalhe_salario);
        requisitos = findViewById(R.id.vaga_detalhe_requisitos);
        perfil = findViewById(R.id.vaga_detalhe_perfil);

        valeTransporte = findViewById(R.id.vaga_detalhe_valeTransporte);
        valeAlimentacao = findViewById(R.id.vaga_detalhe_valeAlimentacao);
        cestaBasica = findViewById(R.id.vaga_detalhe_cestaBasica);
        seguroVida = findViewById(R.id.vaga_detalhe_seguroVida);
        assistMedica = findViewById(R.id.vaga_detalhe_assistMedica);

        telefone = findViewById(R.id.vaga_detalhe_telefone);
        email = findViewById(R.id.vaga_detalhe_email);
        endereco = findViewById(R.id.vaga_detalhe_endereco);
        obs = findViewById(R.id.vaga_detalhe_observacoes);

        parteBeneficios = findViewById(R.id.vaga_detalhe_layoutBeneficio);
        beneficiosExtra = findViewById(R.id.vaga_detalhe_beneficiosExtras);

        new populaVaga().execute();
        new populaBeneficio().execute();

    }

    public class populaVaga extends AsyncTask<Void , Void, Void>{

        String retornoApi;
        Vaga vaga = new Vaga();

        @Override
        protected Void doInBackground(Void... voids) {

            retornoApi = Http.get( API_URL + "/api/vaga.php?idVaga=" + idVaga );

            try{

                JSONArray array_vaga = new JSONArray( retornoApi );

                for( int i = 0 ; i < array_vaga.length() ; i++){

                    JSONObject obj = array_vaga.getJSONObject( i );



                    vaga.setData( obj.getString("dtFormatada") );
                    vaga.setEmpresa( obj.getString("empresa") );
                    vaga.setCargo( obj.getString("cargo"));
                    vaga.setLocalTrabalho( obj.getString("LocalTrabalho") );
                    vaga.setHorario( obj.getString("horario") );
                    vaga.setSalario( obj.getString("salario") );
                    vaga.setPreRequisitos( obj.getString("preRequisitos") );
                    vaga.setPerfilCargo( obj.getString("perfilCargo") );

                    vaga.setValeTransporte( obj.getString("valeTransporte") ) ;
                    vaga.setValeAlimentacao( obj.getString("valeAlimentacao") );
                    vaga.setCestaBasica( obj.getString("cestaBasica") );
                    vaga.setSeguroVida( obj.getString("seguroVida") );
                    vaga.setAssistenciaMedica( obj.getString("assistenciaMedica") );
                    vaga.setTelefone( obj.getString("telefone") );
                    vaga.setEmail( obj.getString("email") );
                    vaga.setEndereco( obj.getString("endereco") );
                    vaga.setObs( obj.getString("obs"));

                }

            }catch( Exception e ){}


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            preencher( vaga );
        }
    }

    public class populaBeneficio extends AsyncTask<Void , Void, Void>{

        String retornoApi;
        ArrayList<String> beneficios = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {

            retornoApi = Http.get( API_URL + "/api/vagaBeneficios.php?idVaga=" + idVaga );

            try{

                JSONArray array_vaga = new JSONArray( retornoApi );

                for( int i = 0 ; i < array_vaga.length() ; i++){

                    JSONObject obj = array_vaga.getJSONObject( i );

                    beneficios.add( obj.getString("nome") );

                }

            }catch( Exception e ){}


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            beneficiosExtras( beneficios );
        }
    }

    public void preencher(Vaga vaga){

        data.setText( vaga.getData() );
        empresa.setText( vaga.getEmpresa() );
        cargo.setText( vaga.getCargo() );
        local.setText( vaga.getLocalTrabalho() );
        horario.setText( vaga.getHorario() );
        salario.setText( vaga.getSalario() );
        requisitos.setText( vaga.getPreRequisitos() );
        perfil.setText( vaga.getPerfilCargo() );

        if( vaga.getCestaBasica().equals("1") )
            cestaBasica.setChecked( true );
        if( vaga.getValeTransporte().equals("1"))
            valeTransporte.setChecked( true );
        if( vaga.getValeAlimentacao().equals("1"))
            valeAlimentacao.setChecked( true );
        if( vaga.getSeguroVida().equals("1") )
            seguroVida.setChecked( true );
        if( vaga.getAssistenciaMedica().equals("1") )
            assistMedica.setChecked( true );

        telefone.setText( vaga.getTelefone() );
        email.setText( vaga.getEmail() );
        endereco.setText( vaga.getEndereco() );
        obs.setText( vaga.getObs() );

    }

    public void beneficiosExtras(ArrayList<String> beneficios ){

        String texto = "";

        if( beneficios.size() > 0 ){

            for( int i = 0 ; i < beneficios.size() ; i++ ){

                if( beneficios.size() - 1 == i ){
                    texto += beneficios.get( i ) ;
                }else{
                    texto += beneficios.get( i ) + "\n";
                }


            }

            beneficiosExtra.setText( texto  );

        }else{

            parteBeneficios.setVisibility(View.GONE);

        }
    }

    public void voltar(View v){
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //btn voltar
                startActivity(new Intent(this, VagasActivity.class));
                finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

}
