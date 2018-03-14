package com.dishant.patel624.quotes;

import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dishant.patel624.quotes.Fragmentclasses.Maincloum;
import com.dishant.patel624.quotes.Fragmentclasses.MainpageFragment;
import com.dishant.patel624.quotes.databasefiles.DatabaseAcess;
import com.dishant.patel624.quotes.databasefiles.Dbhelper;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    public  final String TAG=MainActivity.this.getClass().getName();
    ArrayList<Maincloum> strings = new ArrayList<>();
    ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseAcess databaseAccess = DatabaseAcess.getInstance(this);
        databaseAccess.open();


        strings = databaseAccess.getRandomQuotes();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        MainpageFragment.FragmentAdapter fragmentAdapter = new MainpageFragment.FragmentAdapter(getSupportFragmentManager(), strings);
        viewPager.setAdapter(fragmentAdapter);


//        Cursor cursor=databaseAccess.getRandomQuotes();
//
//
//        if(cursor!=null && cursor.moveToFirst()){
//            do {
//
//                String quotes=cursor.getString(0);
//                Log.d(TAG, "onCreate: "+quotes);
//
//            }while (cursor.moveToNext());
//        }
    }
}
