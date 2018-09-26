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

public class VagaAdapter extends ArrayAdapter<Vaga> {

    TextView empresa , cargo, local;

    public VagaAdapter(Context context, ArrayList<Vaga> arrayList ) {
        super(context, 0 , arrayList);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_vaga, null);
        }

        Vaga vaga = getItem( position );

        empresa = v.findViewById(R.id.vaga_empresa);
        cargo = v.findViewById(R.id.vaga_cargo);
        local = v.findViewById(R.id.vaga_local);

        empresa.setText( vaga.getEmpresa() );
        cargo.setText( vaga.getCargo() );
        local.setText( vaga.getLocalTrabalho() );

        return v;

    }

}
