package com.dishant.patel624.quotes.Fragmentclasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by other on 14-Dec-17.
 */

public class Maincloum implements Parcelable {

    String author, quotes;

    public Maincloum() {
    }

    protected Maincloum(Parcel in) {
        author = in.readString();
        quotes = in.readString();
    }

    public static final Creator<Maincloum> CREATOR = new Creator<Maincloum>() {
        @Override
        public Maincloum createFromParcel(Parcel in) {
            return new Maincloum(in);
        }

        @Override
        public Maincloum[] newArray(int size) {
            return new Maincloum[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes,boolean fixedlength) {

        if(fixedlength) {
            if (quotes.length() <= 250 && quotes.length() >= 10)
                this.quotes = quotes;
        }else{
            this.quotes = quotes;

        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(quotes);
    }
}
