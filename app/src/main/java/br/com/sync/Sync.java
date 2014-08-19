package br.com.sync;

import android.content.Context;
import android.util.Log;


import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.com.entidades.Estabelecimento;

/**
 * Created by diamante on 09/08/14.
 */
public class Sync  {

    private Context context;
    private String url;
    private String uri;
    public List<Estabelecimento> estabelecimentoArrayList ;

    public Sync(Context context, String url, String uri) {
        this.context = context;
        this.url = url;
        this.uri = uri;
    }


    public List<Estabelecimento> syncEstab(String indice){

        estabelecimentoArrayList = new ArrayList<Estabelecimento>();

              Ion.with(this.context)
                .load(this.url)
                .asString().setCallback(new
                                                FutureCallback<String>() {
                                                    @Override
                                                    public void onCompleted(Exception e, String result) {

                                                        try {
                                                            JSONArray jsonArray = new JSONArray(result);
                                                            Estabelecimento p;

                                                            for (int i = 0; i < jsonArray.length(); i++) {

                                                                p = new Estabelecimento();

                                                                int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));

                                                                p.setId(id);
                                                                p.setNome(jsonArray.getJSONObject(i).getString("nome"));
                                                                // p.setTelefone(jsonArray.getJSONObject(i).getString("telefone"));
                                                                // p.setEndereco(jsonArray.getJSONObject(i).getString("endereco"));
                                                                p.setTipo(jsonArray.getJSONObject(i).getString("tipo"));
                                                                p.setDescricao(jsonArray.getJSONObject(i).getString("descricao"));
                                                                p.setImagem(jsonArray.getJSONObject(i).getString("imagem"));

                                                                estabelecimentoArrayList.add(p);

                                                            }
                                                        } catch (JSONException e1) {
                                                            e1.printStackTrace();
                                                        }catch (NullPointerException e1){
                                                            e1.printStackTrace();
                                                        }
                                                    }
                                                });






        return estabelecimentoArrayList;
    }








    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
