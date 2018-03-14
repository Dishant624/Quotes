package com.dishant.patel624.quotes.databasefiles;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.dishant.patel624.quotes.Fragmentclasses.Maincloum;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by other on 14-Dec-17.
 */

public class DatabaseAcess  {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAcess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAcess(Context context) {
        this.openHelper = new Dbhelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAcess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAcess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }


    public ArrayList<Maincloum> getRandomQuotes(){
        ArrayList<Maincloum> maincloums=new ArrayList<>();
        Cursor query = database.rawQuery("SELECT author,status_quote FROM quotes_en ORDER BY RANDOM() LIMIT 10", null);


        if(query!=null && query.moveToFirst()){
            do {

                String author=query.getString(0);
                String quotes=query.getString(1);

                Maincloum maincloum=new Maincloum();
                maincloum.setAuthor(author);
                maincloum.setQuotes(quotes,true);

                maincloums.add(maincloum);

            }while (query.moveToNext());
        }
        if (query!=null){
            query.close();
        }
        return maincloums;


    }

    public HashSet<String> getAuthorName(){
        HashSet<String> strings=new HashSet<>();

        Cursor cursor=database.rawQuery("SELECT author FROM quotes_en",null);
        if(cursor!=null && cursor.moveToFirst()){
            do {

                String author=cursor.getString(0);
                if(!TextUtils.isEmpty(author)&&!author.matches(".*\\d+.*")){
                    strings.add(author);

                    Log.d("author", "getAuthorName: "+author);

                    if(cursor.isLast()){
                        cursor.close();
                    }
                }

            }while (cursor.moveToNext());
        }

        return strings;
    }

    public ArrayList<Maincloum> getqutoesbyauthor(String authorname){
        ArrayList<Maincloum> maincloums=new ArrayList<>();
        Cursor query = database.query("quotes_en", new String[]{"author","status_quote"}, "author=?", new String[]{authorname}, null, null, null);


        if(query!=null && query.moveToFirst()){
            do {

                String author=query.getString(0);
                String quotes=query.getString(1);

                Maincloum maincloum=new Maincloum();
                maincloum.setAuthor(author);
                maincloum.setQuotes(quotes,false);

                maincloums.add(maincloum);

            }while (query.moveToNext());
        }
        if (query!=null){
            query.close();
        }
        return maincloums;


    }



}
