package br.com.telepixel;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import br.com.entidades.Estabelecimento;
import br.com.sync.Sync;


public class EstabFrag extends Fragment {
    List<Estabelecimento> list;
    ListView listView;
    View view;
    ProgressBar p;
    public EstabFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_estabelecimento, container, false);
        listView = (ListView) view.findViewById(R.id.listViewEstab);
        p = (ProgressBar) view.findViewById(R.id.progressBarEstab);
        Log.i("OnStat", "passou");

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Sync sync = new Sync(getActivity().getBaseContext(), "http://192.168.0.67/serverPHP/sync/syncEstab.php", null);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list = sync.syncEstab("");


    }

    @Override
    public void onResume() {
        super.onResume();
        if(list.size() > 0) {
            p.setVisibility(View.GONE);
            EstabAdapter estabAdapter = new EstabAdapter(getActivity().getApplicationContext(), list);
            listView.setAdapter(estabAdapter);
        }else {

        }
     }
}
