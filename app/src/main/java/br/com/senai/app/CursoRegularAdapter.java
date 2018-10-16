package br.com.senai.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 16255204 on 17/05/2018.
 */

public class CursoRegularAdapter extends ArrayAdapter<CursoRegular> {

    LinearLayout periodoInscricao;
    TextView titulo , descricao, termino;
    ImageView capa;
    String API_URL;

    public CursoRegularAdapter(Context context, ArrayList<CursoRegular> arrayList, String url ) {
        super(context, 0 , arrayList);
        API_URL = url;

    }

    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_curso, null);
        }

        CursoRegular curso = getItem( position );

        titulo = v.findViewById(R.id.curso_titulo);
        descricao = v.findViewById(R.id.curso_descricao);
        termino = v.findViewById(R.id.curso_termino);
        capa = v.findViewById(R.id.capa);

        periodoInscricao = v.findViewById(R.id.periodo_inscricao);

        if( curso.getProcessoSeletivo().equals("1") && curso.getPostar().equals("1") ){

            periodoInscricao.setVisibility( View.VISIBLE );

        }else{
            periodoInscricao.setVisibility(View.GONE);
        }


        Picasso.with(getContext()).load(API_URL +"/views/" + curso.getUrlImagem() ).into(capa);

        titulo.setText( curso.getNome() );
        descricao.setText( curso.getDescricao() );
        termino.setText( curso.getDtFim() + " as " + curso.getHrFim() + "Hrs" );

        return v;

    }

}
