package com.dishant.patel624.quotes.Fragmentclasses;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dishant.patel624.quotes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewpagerFragmentForsecondepage extends Fragment {


    Maincloum quote;
    public final String TAG=getClass().getName();
    TextView quotetxt,authortxt;

    public ViewpagerFragmentForsecondepage() {
        // Required empty public constructor
    }

    public static ViewpagerFragmentForsecondepage newInstance(int position, Maincloum quotes) {
        ViewpagerFragmentForsecondepage viewpagerFragment = new ViewpagerFragmentForsecondepage();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putParcelable("quotes", quotes);
        viewpagerFragment.setArguments(bundle);
        return viewpagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            quote = bundle.getParcelable("quotes");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quotetxt = view.findViewById(R.id.maintxt);
        //authortxt = view.findViewById(R.id.author);
        quotetxt.setText(quote.getQuotes());
        Log.d(TAG, "onViewCreated: "+quote.getQuotes());
        //authortxt.setText("-"+quote.getAuthor());

    }
}
