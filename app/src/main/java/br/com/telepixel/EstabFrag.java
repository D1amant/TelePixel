package br.com.telepixel;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.com.entidades.Estabelecimento;



public class EstabFrag extends Fragment {
    List<Estabelecimento> list;
    ListView listView;
    View view;
    EstabAdapter estabAdapter;
    public EstabFrag(List<Estabelecimento> list) {
        this.list = list;
    }


    public EstabFrag() {
        this.list = list;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public List<Estabelecimento> getList() {
        return list;
    }

    public void setList(List<Estabelecimento> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_estabelecimento, container, false);
        listView = (ListView) view.findViewById(R.id.listViewEstab);



        Log.i("list", ""+this.list.size() );
        Log.i("onCreateView", "passou");

        try {

            if (this.list.size() > 0) {

                 estabAdapter = new EstabAdapter(getActivity().getApplicationContext(), this.list , getResources().getString(R.string.servidor));
                listView.setAdapter(estabAdapter);
            } else {

              //  Toast.makeText(getActivity().getBaseContext(), "Lista zerada", Toast.LENGTH_LONG).show();
            }
        }catch (NullPointerException e){
            Log.i("null interno", e.toString());
        }


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();



    }


    @Override
    public void onResume() {
        super.onResume();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Lista","onclic");
            }
        });

        listView.setOnScrollListener( new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,int totalItemCount) {

                if ((visibleItemCount + firstVisibleItem) == totalItemCount) {


                    ProgressBar p = new ProgressBar(getActivity().getApplicationContext());


                    Ion.with(getActivity().getApplicationContext())
                            .load(getResources().getString(R.string.servidorEstabelecimento))
                            .progressBar(p)
                            .asString().setCallback(new
                                                            FutureCallback<String>() {


                                                                @Override
                                                                public void onCompleted(Exception e, String result) {
                                                                    List<Estabelecimento> list2 = new ArrayList<Estabelecimento>();
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

                                                                              list.add(p);



                                                                        }

                                                                        for (Estabelecimento obj :  list2) {
                                                                            estabAdapter.add(obj);
                                                                        }
                                                                    } catch (JSONException e1) {
                                                                        Log.e("errro", e1.toString());
                                                                        // Toast.makeText(this, getBaseContext().getResources().getString(R.string.erroDadosNaoEncontrados), Toast.LENGTH_LONG).show();
                                                                    } catch (NullPointerException e1) {
                                                                        Log.e("errro2", e1.toString());
                                                                        // Toast.makeText(this, getResources().getString(R.string.erroServer),Toast.LENGTH_LONG).show();
                                                                    }


                                                                }
                                                            });
                }

            }
        });



    }
}
