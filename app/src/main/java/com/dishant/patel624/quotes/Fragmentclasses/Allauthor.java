package com.dishant.patel624.quotes.Fragmentclasses;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.dishant.patel624.quotes.R;
import com.dishant.patel624.quotes.databasefiles.DatabaseAcess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class Allauthor extends Fragment {


    ListView listView;

    public String TAG=getClass().getName();
    ViewPager viewPager;
    CircleIndicator indicator;

    ArrayList<Maincloum> randomquotes = new ArrayList<>();
    List<String> allauthor = new ArrayList<>();
    DatabaseAcess databaseAccess;
    ProgressBar progressDialog;
    Handler handler = new Handler();

    public Allauthor() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        databaseAccess = DatabaseAcess.getInstance(getActivity());
        databaseAccess.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_allauthor, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView=view.findViewById(R.id.list);
        new Getallauthor().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.txt);
                Toast.makeText(getActivity().getApplicationContext(), textView.getText(), Toast.LENGTH_SHORT).show();

                ArrayList<Maincloum> maincloums = databaseAccess.getqutoesbyauthor(textView.getText().toString());
                Log.d("data", "onItemClick: " + maincloums);

                QutoesfromAuthorFragment qutoesfromAuthorFragment = new QutoesfromAuthorFragment();
                Bundle bundle = new Bundle();
                bundle.putString("authorname", textView.getText().toString());
                qutoesfromAuthorFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contanier, qutoesfromAuthorFragment).addToBackStack("tag");
                fragmentTransaction.commit();
            }
        });


    }
    public class ListAdapter extends ArrayAdapter<String> implements SectionIndexer {

        HashMap<String, Integer> mapIndex;
        String[] sections;
        List<String> fruits;
        LayoutInflater layoutInflater;

        public ListAdapter(Context context, List<String> fruitList) {
            super(context, R.layout.singleauthor_list, fruitList);

            this.fruits = fruitList;
            mapIndex = new LinkedHashMap<String, Integer>();
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (int x = 0; x < fruits.size(); x++) {
                String fruit = fruits.get(x);
                String ch = fruit.substring(0, 1);
                ch = ch.toUpperCase(Locale.US);

                // HashMap will prevent duplicates
                if (!mapIndex.containsKey(ch))
                    mapIndex.put(ch, x);
            }

            Set<String> sectionLetters = mapIndex.keySet();

            // create a list from the set to sort
            ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

            Log.d("sectionList", sectionList.toString());
            Collections.sort(sectionList);

            sections = new String[sectionList.size()];

            sectionList.toArray(sections);
        }

        public int getPositionForSection(int section) {
            Log.d("section", "" + section);
            return mapIndex.get(sections[section]);
        }

        public int getSectionForPosition(int position) {
            Log.d("position", "" + position);
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Allauthor.ListAdapter.Viewholderclass viewholderclass;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.singleauthor_list, null);
                viewholderclass = new Allauthor.ListAdapter.Viewholderclass();
                viewholderclass.textView = convertView.findViewById(R.id.txt);
                convertView.setTag(viewholderclass);

            } else {
                viewholderclass = (Allauthor.ListAdapter.Viewholderclass) convertView.getTag();
            }
            viewholderclass.textView.setText(fruits.get(position));
            return convertView;
        }

        class Viewholderclass {
            TextView textView;
        }

        public Object[] getSections() {
            return sections;
        }
    }


    class Getallauthor extends AsyncTask<Void,Void,List<String>>{

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.show();
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            allauthor = databaseAccess.getAuthorName();

            return allauthor;
        }

        @Override
        protected void onPostExecute(List<String> aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            listView.setFastScrollEnabled(true);
            Allauthor.ListAdapter listAdapter = new Allauthor.ListAdapter(getActivity().getApplicationContext(), aVoid);
            listView.setAdapter(listAdapter);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

       // MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main2, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }
}
