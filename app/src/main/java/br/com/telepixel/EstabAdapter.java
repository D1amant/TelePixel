package br.com.telepixel;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.entidades.Estabelecimento;


/**
 * Created by luis on 07/08/14.
 */
public class EstabAdapter extends ArrayAdapter {

    private  Context context;
    private List<Estabelecimento> list;
    private String serverUrl;
    public EstabAdapter(Context context , List<Estabelecimento> list ,String serverUrl) {

        super(context,0, list);
        this.context = context;
        this.list = list;
        this.serverUrl = serverUrl;

    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        try {
            Log.e("image" , this.list.get(0).getImagem().toString());
           if (view == null) {
               view = LayoutInflater.from(context).inflate(R.layout.estab_adapter, null);

               TextView titulo = (TextView) view.findViewById(R.id.tituloEstab);
               TextView tipo = (TextView) view.findViewById(R.id.tipoEstb);
               TextView descricao = (TextView) view.findViewById(R.id.descicaoEstab);
               ImageView imageView = (ImageView) view.findViewById(R.id.imageEstab);

               titulo.setText(this.list.get(0).getNome().toString());
               tipo.setText(this.list.get(0).getTipo().toString());
               descricao.setText(this.list.get(0).getDescricao().toString());
               Picasso.with(context).load(this.serverUrl+this.list.get(0).getImagem().toString()).into(imageView);

           }

       }catch (NullPointerException e){
           e.printStackTrace();
       }
        return view;
    }










}
