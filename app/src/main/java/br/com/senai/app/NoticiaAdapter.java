package br.com.senai.app;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class NoticiaAdapter extends ArrayAdapter<Noticia>{

    ImageView imagem;
    TextView titulo , descricao, fim;
    String API_URL;

    public NoticiaAdapter(Context context, ArrayList<Noticia> arrayList, String url ) {
        super(context, 0 , arrayList);
        API_URL = url;

    }

    public View getView(int position,  View convertView, ViewGroup parent){


        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_noticia, null);
        }

        Noticia noticia = getItem( position );

        imagem = v.findViewById(R.id.capa_noticia);
        titulo = v.findViewById(R.id.noticia_titulo);
        descricao = v.findViewById(R.id.noticia_descricao);
        fim = v.findViewById(R.id.noticia_fim);


        Picasso.with(getContext()).load(API_URL +"/views/" + noticia.getUrlImagem() ).into(imagem);

        titulo.setText( noticia.getTitulo() );
        descricao.setText( noticia.getDescricao() );
        fim.setText( noticia.getDtFim() );

        return v;

    }


}
