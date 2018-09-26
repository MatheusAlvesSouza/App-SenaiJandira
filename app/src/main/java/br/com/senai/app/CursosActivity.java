package br.com.senai.app;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;

public class CursosActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     *
     *
     */

    static  LinearLayout menu_cai , menu_fic ;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static ArrayAdapter<CursoRegular> adapterCai;
    static ArrayAdapter<CursoRegular> adapterTec;
    static ArrayAdapter<CursoFic> adapterFic;
    static String API_URL;
    TextView titulo;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão voltar
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão voltar
        getSupportActionBar().setTitle("Cursos");

        API_URL = getString(R.string.api_key);
        menu_cai = findViewById(R.id.cursos_cai);
        menu_fic = findViewById(R.id.cursos_fic);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            /*if( sectionNumber == 1 ){
                menu_cai.setBackgroundColor( -13615201);
                menu_fic.setBackgroundColor( -12627531 );
                menu_tec.setBackgroundColor( -12627531 );
            }else if( sectionNumber == 2 ){
                menu_cai.setBackgroundColor(-12627531);
                menu_fic.setBackgroundColor( -13615201 );
                menu_tec.setBackgroundColor( -12627531 );
            }else if( sectionNumber == 3){
                menu_cai.setBackgroundColor(-12627531);
                menu_fic.setBackgroundColor( -12627531 );
                menu_tec.setBackgroundColor( -13615201 );

            }*/

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_cursos, container, false);



            ListView lista = rootView.findViewById(R.id.frag_list);
            TextView titulo = rootView.findViewById(R.id.suport);

            /*
                pagina 1 = CAI
                pagina 2 = Tecnico
                pagina 3 = FIC
             */



            int pagina = getArguments().getInt(ARG_SECTION_NUMBER); //Pega qual a pagina
            if( pagina == 1 ){

                titulo.setText("CAI");

                adapterCai = new CursoRegularAdapter( getContext() , new ArrayList<CursoRegular>() ,API_URL );

                lista.setAdapter( adapterCai );

                new populaCai().execute();

                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        CursoRegular curso = adapterCai.getItem( i );

                        if( curso.getProcessoSeletivo().equals("0") || curso.getPostar().equals("0") ){
                            Toast.makeText( getContext() , "O curso de " + curso.getNome() + " não possui vagas em aberto !", Toast.LENGTH_LONG ).show();
                        }else{
                            Intent site = new Intent(Intent.ACTION_VIEW);
                            site.setData(Uri.parse( curso.getLink() ));
                            startActivity(site);
                        }

                    }
                });

            }else if( pagina == 2 ){

                titulo.setText("Técnico");

                adapterTec = new CursoRegularAdapter( getContext() , new ArrayList<CursoRegular>() , API_URL );

                lista.setAdapter( adapterTec );

                new populaTecnico().execute();

                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        CursoRegular curso = adapterTec.getItem( i );

                        if( curso.getProcessoSeletivo().equals("0")  || curso.getPostar().equals("0") ){
                            Toast.makeText( getContext() , "O curso de " + curso.getNome() + " não possui vagas em aberto !", Toast.LENGTH_LONG ).show();
                        }else{
                            Intent site = new Intent(Intent.ACTION_VIEW);
                            site.setData(Uri.parse( curso.getLink() ));
                            startActivity(site);
                        }

                    }
                });

            }else if( pagina == 3 ){

                titulo.setText("FIC");

                adapterFic = new CursoFicAdapter( getContext() , new ArrayList<CursoFic>() );

                lista.setAdapter( adapterFic );

                new populaFic().execute();

                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        CursoFic curso = adapterFic.getItem( i );

                        Intent site = new Intent(Intent.ACTION_VIEW);
                        site.setData(Uri.parse( curso.getLink() ));
                        startActivity(site);
                    }
                });

            }

            return rootView;
        }

    }

    public static class populaCai extends AsyncTask<Void , Void , Void>{

        String retornoApi;
        ArrayList<CursoRegular> cursos = new ArrayList<>();
        @Override
        protected Void doInBackground(Void... voids) {

            retornoApi = Http.get( API_URL + "/hackathon/api/cursosCai.php");

            try{

                JSONArray array_cursos = new JSONArray( retornoApi );

                for( int i = 0 ; i < array_cursos.length(); i++ ){

                    JSONObject obj = array_cursos.getJSONObject( i );

                    CursoCai curso = new CursoCai();

                    curso.setNome( obj.getString("nome") );
                    curso.setDescricao( obj.getString("descricao") );
                    curso.setDtFim( obj.getString("dtFormFim") );
                    curso.setHrFim( obj.getString("horaFim") );
                    curso.setLink( obj.getString("link") );
                    curso.setUrlImagem( obj.getString("imagem") );
                    curso.setProcessoSeletivo( obj.getString("processoSeletivo") );
                    curso.setPostar( obj.getString("postar") );

                    cursos.add(curso);

                }

            }catch (Exception e ){}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapterCai.addAll( cursos );
        }
    }

    public static class populaTecnico extends AsyncTask<Void , Void , Void>{

        String retornoApi;
        ArrayList<CursoRegular> cursos = new ArrayList<>();
        @Override
        protected Void doInBackground(Void... voids) {

            retornoApi = Http.get( API_URL + "/hackathon/api/cursosTecnicos.php");

            try{

                JSONArray array_cursos = new JSONArray( retornoApi );

                for( int i = 0 ; i < array_cursos.length(); i++ ){

                    JSONObject obj = array_cursos.getJSONObject( i );

                    CursoCai curso = new CursoCai();

                    curso.setNome( obj.getString("nome") );
                    curso.setDescricao( obj.getString("descricao") );
                    curso.setDtFim( obj.getString("dtFormFim") );
                    curso.setHrFim( obj.getString("horaFim") );
                    curso.setLink( obj.getString("link") );
                    curso.setUrlImagem( obj.getString("imagem") );
                    curso.setProcessoSeletivo( obj.getString("processoSeletivo") );
                    curso.setPostar( obj.getString("postar") );

                    cursos.add(curso);

                }

            }catch (Exception e ){}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapterTec.addAll( cursos );
        }
    }


    public static class populaFic extends AsyncTask<Void , Void , Void>{

        String retornoApi;
        ArrayList<CursoFic> cursos = new ArrayList<>();
        @Override
        protected Void doInBackground(Void... voids) {

            retornoApi = Http.get( API_URL + "/hackathon/api/cursosFic.php");

            try{

                JSONArray array_cursos = new JSONArray( retornoApi );

                for( int i = 0 ; i < array_cursos.length(); i++ ){

                    JSONObject obj = array_cursos.getJSONObject( i );

                    CursoFic curso = new CursoFic();

                    curso.setNome( obj.getString("nome") );
                    curso.setDucacao( obj.getString("duracao") );
                    curso.setDtInicio( obj.getString("dtFormInicio") );
                    curso.setDtFim( obj.getString("dtFormFim") );
                    curso.setPeriodo( obj.getString("periodo") );
                    curso.setValor( obj.getDouble("valor") );
                    curso.setParcelas( obj.getString("parcelas") );
                    curso.setLink( obj.getString("link") );

                    cursos.add(curso);

                }

            }catch (Exception e ){}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapterFic.addAll( cursos );
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }


}
