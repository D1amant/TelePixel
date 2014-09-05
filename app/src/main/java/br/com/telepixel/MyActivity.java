package br.com.telepixel;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.entidades.Estabelecimento;


public class MyActivity extends Activity implements ActionBar.TabListener {

    List<Estabelecimento> list;
    String calssName = "MyActivity";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    List<Estabelecimento> estabelecimentoArrayList = new ArrayList<Estabelecimento>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_my);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


        ProgressBar p = new ProgressBar(this);


        Ion.with(this)
                .load(getResources().getString(R.string.servidorEstabelecimento))
                .progressBar(p)
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
                                                            Log.e("errro" , e1.toString());
                                                            // Toast.makeText(this, getBaseContext().getResources().getString(R.string.erroDadosNaoEncontrados), Toast.LENGTH_LONG).show();
                                                        }catch (NullPointerException e1){
                                                            Log.e("errro2" , e1.toString());
                                                            // Toast.makeText(this, getResources().getString(R.string.erroServer),Toast.LENGTH_LONG).show();
                                                        }
                                                        Fragment   fragment = new EstabFrag(estabelecimentoArrayList);


//                                                        getFragmentManager().beginTransaction().
//                                                                remove(getFragmentManager().findFragmentByTag("EstabFrag")).commit();

                                                        getFragmentManager()
                                                                .beginTransaction()
                                                                .replace(R.id.estabFrag, fragment)
                                                                .addToBackStack(null) // enables back key
                                                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // if you need transition
                                                                .commit();



                                                    }
                                                });


    }

    @Override
    protected void onStart() {
        super.onStart();



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        Fragment fragment;

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new Fragment();
            switch (position){
                case 0:
                    fragment = new EstabFrag(estabelecimentoArrayList);

                    return fragment;
                case 1:
                    fragment = new Historico();
                    return fragment;
                case 2:
                    fragment = new Pedidos();
                    return fragment;
                case 3:
                    fragment = new EstabFrag(estabelecimentoArrayList);
            }


            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(calssName,
                    "MainActivity.onConfigurationChanged (ORIENTATION_PORTRAIT)");
            // setting orientation portrait


        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(calssName,
                    "MainActivity.onConfigurationChanged (ORIENTATION_LANDSCAPE)");

        }


    }


}
