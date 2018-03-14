package com.dishant.patel624.quotes.Fragmentclasses;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
public class MainpageFragment extends Fragment {


    ViewPager viewPager;
    CircleIndicator indicator;

    ListView listView;
    ArrayList<Maincloum> strings = new ArrayList<>();
    HashSet<String> allauthor;
    DatabaseAcess databaseAccess;

    public MainpageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseAccess = DatabaseAcess.getInstance(getActivity());
        databaseAccess.open();

        strings = databaseAccess.getRandomQuotes();
        allauthor = databaseAccess.getAuthorName();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mainpage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), strings);
        viewPager.setAdapter(fragmentAdapter);
        indicator.setViewPager(viewPager);


        viewPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {

            }
        });


        listView = (ListView) view.findViewById(R.id.list);
        listView.setFastScrollEnabled(true);
        List<String> stringsauthor = new ArrayList<>();

        for (String s : allauthor) {

            stringsauthor.add(s);
        }
        Collections.sort(stringsauthor);

        ListAdapter listAdapter = new ListAdapter(getActivity().getApplicationContext(), stringsauthor);
        listView.setAdapter(listAdapter);
        //stringsauthor.clear();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.txt);
                Toast.makeText(getActivity().getApplicationContext(), textView.getText(), Toast.LENGTH_SHORT).show();

                ArrayList<Maincloum> maincloums = databaseAccess.getqutoesbyauthor(textView.getText().toString());

                Log.d("data", "onItemClick: " + maincloums);

                QutoesfromAuthorFragment qutoesfromAuthorFragment=new QutoesfromAuthorFragment();
                Bundle bundle=new Bundle();
                bundle.putString("authorname",textView.getText().toString());
                qutoesfromAuthorFragment.setArguments(bundle);

                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contanier,qutoesfromAuthorFragment).addToBackStack("tag");
                fragmentTransaction.commit();
            }
        });


    }

    public static class FragmentAdapter extends FragmentStatePagerAdapter {

        ArrayList<Maincloum> maincloum;

        public FragmentAdapter(FragmentManager fm, ArrayList<Maincloum> maincloums) {
            super(fm);
            this.maincloum = maincloums;
        }

        @Override
        public Fragment getItem(int position) {
            return ViewpagerFragment.newInstance(position, maincloum.get(position));
        }

        @Override
        public int getCount() {
            return maincloum.size();
        }
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

            Viewholderclass viewholderclass;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.singleauthor_list, null);
                viewholderclass = new Viewholderclass();
                viewholderclass.textView = convertView.findViewById(R.id.txt);
                convertView.setTag(viewholderclass);

            } else {
                viewholderclass = (Viewholderclass) convertView.getTag();
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

    @Override
    public void onResume() {
        super.onResume();

//        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
//        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), strings);
        viewPager.setAdapter(fragmentAdapter);
        indicator.setViewPager(viewPager);



//        listView.setFastScrollEnabled(true);
//
//        for (String s : allauthor) {
//
//            stringsauthor.add(s);
//        }
//        Collections.sort(stringsauthor);
//
//        ListAdapter listAdapter = new ListAdapter(getActivity().getApplicationContext(), stringsauthor);
//        listView.setAdapter(listAdapter);


    }
}
