package com.dishant.patel624.quotes.Fragmentclasses;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dishant.patel624.quotes.R;
import com.dishant.patel624.quotes.databasefiles.DatabaseAcess;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class QutoesfromAuthorFragment extends Fragment {



    String authorname;
    ViewPager viewPager;
    TextView authoranme1;
    ArrayList<Maincloum> strings = new ArrayList<>();




    public QutoesfromAuthorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getArguments();
        DatabaseAcess databaseAccess = DatabaseAcess.getInstance(getActivity());
        databaseAccess.open();
        if(bundle!=null){
            authorname=bundle.getString("authorname");
            strings=databaseAccess.getqutoesbyauthor(authorname);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qutoesfrom_author, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        authoranme1=view.findViewById(R.id.autorname);
        authoranme1.setText(authorname);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), strings);
        viewPager.setAdapter(fragmentAdapter);

    }

    public static class FragmentAdapter extends FragmentStatePagerAdapter {

        ArrayList<Maincloum> maincloum;

        public FragmentAdapter(FragmentManager fm, ArrayList<Maincloum> maincloums) {
            super(fm);
            this.maincloum = maincloums;
        }

        @Override
        public Fragment getItem(int position) {
            return ViewpagerFragmentForsecondepage.newInstance(position, maincloum.get(position));
        }

        @Override
        public int getCount() {
            return maincloum.size();
        }
    }


}
