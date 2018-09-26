package br.com.senai.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 16255204 on 17/05/2018.
 */

public class CursoFicAdapter  extends ArrayAdapter<CursoFic> {

    TextView titulo , duracao, dtInicio, dtFim, periodo, valor;

    public CursoFicAdapter(Context context, ArrayList<CursoFic> arrayList ) {
        super(context, 0 , arrayList);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_fic, null);
        }

        CursoFic curso = getItem( position );

        titulo = v.findViewById(R.id.fic_titulo);
        duracao = v.findViewById(R.id.fic_duracao);
        dtInicio = v.findViewById(R.id.fic_dtInicio);
        dtFim = v.findViewById(R.id.fic_dtFim);
        periodo = v.findViewById(R.id.fic_periodo);
        valor = v.findViewById(R.id.fic_valor);

        titulo.setText( curso.getNome() );
        duracao.setText( curso.getDucacao() );
        dtInicio.setText( curso.getDtInicio() );
        dtFim.setText( curso.getDtFim() );
        periodo.setText( curso.getPeriodo() );
        valor.setText( curso.getValor() + " em até " + curso.getParcelas() + "x" );


        return v;

    }

}
