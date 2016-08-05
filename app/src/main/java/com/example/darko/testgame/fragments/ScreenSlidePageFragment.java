package com.example.darko.testgame.fragments;

/**
 * Created by darko on 7/12/2016.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.darko.testgame.ColisionGameActivity;
import com.example.darko.testgame.R;
import com.example.darko.testgame.mole.SmashTheMole;
import com.example.darko.testgame.nemo.NemoGameActivity;

public class ScreenSlidePageFragment extends Fragment {

    /**
     * Key to insert the background color into the mapping of a Bundle.
     */
    private static final String BACKGROUND_COLOR = "color";

    /**
     * Key to insert the index page into the mapping of a Bundle.
     */
    private static final String TEXT = "text";
    private static final String GAME_INDEX = "index_game";

    private int color;
    private int game_index;
    private String text;

    /**
     * Instances a new fragment with a background color and an index page.
     *
     * @param color background color
     * @param text  String text to be displayed
     * @return a new page
     */
    public static ScreenSlidePageFragment newInstance(int color, String text, int game_index) {

        // Instantiate a new fragment
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putInt(BACKGROUND_COLOR, color);
        bundle.putString(TEXT, text);
        bundle.putInt(GAME_INDEX, game_index);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        // Load parameters when the initial creation of the fragment is done
        this.color = (getArguments() != null) ? getArguments().getInt(
                BACKGROUND_COLOR) : Color.GRAY;
        if (getArguments().getString(TEXT) != null)
            this.text = getArguments().getString(TEXT);
        if (getArguments().getString(GAME_INDEX) != null)
            this.game_index = getArguments().getInt(GAME_INDEX);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        // Show the current page index in the view
        TextView tvIndex = (TextView) rootView.findViewById(R.id.tvIndex);
        tvIndex.setText(String.valueOf(this.text));
        Log.d("fragment", "load " + text);
        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("fragment", "click " + text);
                switch (text) {
                    case "Color book":
//                        getActivity().startActivity(new Intent(getActivity(), StaraKlasaAsync.class));
                        break;
                    case "Whack a mole":
                        getActivity().startActivity(new Intent(getActivity(), SmashTheMole.class));
                        break;
                    case "Galaxy invaders":
                        getActivity().startActivity(new Intent(getActivity(), ColisionGameActivity.class));
                        break;
                    case "Nemo's bubbles":
                        getActivity().startActivity(new Intent(getActivity(), NemoGameActivity.class));
                        break;
                }
            }
        });

        // Change the background color
        rootView.setBackgroundColor(this.color);

        return rootView;

    }
}