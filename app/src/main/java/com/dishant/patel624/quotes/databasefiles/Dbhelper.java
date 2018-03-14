package com.dishant.patel624.quotes.databasefiles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by other on 14-Dec-17.
 */

public class Dbhelper extends SQLiteAssetHelper {

    public static final String databasename = "quotes_en.db";
    public static final int databaseversion = 1;

    public Dbhelper(Context context) {
        super(context, databasename, null, databaseversion);
    }
}
