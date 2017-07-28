package com.jsbomb.earthquakelist.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsbomb.earthquakelist.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A fragment representing a single Earthquake detail screen.
 * This fragment is either contained in a {@link EarthquakeListActivity}
 * in two-pane mode (on tablets) or a {@link EarthquakeDetailActivity}
 * on handsets.
 */
public class EarthquakeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_TIME = "item_time";
    public static final String ARG_ITEM_PLACE = "item_place";
    public static final String ARG_ITEM_MAG = "item_mag";
    public static final String ARG_ITEM_LONGI = "item_longi";
    public static final String ARG_ITEM_LATTI = "item_latti";
    public static final String ARG_ITEM_TSUNAMI = "item_tsunami";

    private EarthquakeDetail mEarthquake;

    View.OnClickListener mLocClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //marker but zoomed in too close
            String format = "geo:%s,%s?z=10&q=%s";
            String queryFormat = "%s,%s(%s M%f)";
            String encodedQuery = Uri.encode(String.format(Locale.US, queryFormat,
                    mEarthquake.mLattitude, mEarthquake.mLongitude,
                    mEarthquake.mPlace, mEarthquake.mMag));

            String uriString = String.format(Locale.US, format,
                    mEarthquake.mLattitude, mEarthquake.mLongitude, encodedQuery);

            //no marker
//            format = "geo:%s,%s?z=10&%s";
//            queryFormat = "(%s M%f)";
//            encodedQuery = Uri.encode(String.format(Locale.US, queryFormat, mEarthquake.mPlace,
//                    mEarthquake.mMag));
//            uriString = String.format(Locale.US, format, mEarthquake.mLattitude,
//                    mEarthquake.mLongitude, encodedQuery);

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uriString)));
        }
    };


    private class EarthquakeDetail {
        private long mTime;
        private String mPlace;
        private double mMag;
        private double mLongitude;
        private double mLattitude;
        private int mTsunami;

        EarthquakeDetail() {
        }
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EarthquakeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        mEarthquake = new EarthquakeDetail();
        mEarthquake.mTime = arguments.getLong(ARG_ITEM_TIME);
        mEarthquake.mPlace = arguments.getString(ARG_ITEM_PLACE);
        mEarthquake.mMag = arguments.getDouble(ARG_ITEM_MAG);
        mEarthquake.mLongitude = arguments.getDouble(ARG_ITEM_LONGI);
        mEarthquake.mLattitude = arguments.getDouble(ARG_ITEM_LATTI);
        mEarthquake.mTsunami = arguments.getInt(ARG_ITEM_TSUNAMI);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout =
                (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(getString(R.string.title_earthquake_detail));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.earthquake_detail, container, false);
        if (mEarthquake != null) {
            rootView.setVisibility(View.VISIBLE);
            TextView timeView = (TextView) rootView.findViewById(R.id.time_view);
            TextView placeView = (TextView)rootView.findViewById(R.id.place_view);
            TextView magView = (TextView)rootView.findViewById(R.id.magnitude_view);
            TextView coordView = (TextView)rootView.findViewById(R.id.coordinates_view);
            TextView tsunamiView = (TextView)rootView.findViewById(R.id.tsunami_view);

            timeView.setText(convertTime(mEarthquake.mTime));
            placeView.setText(mEarthquake.mPlace);
            magView.setText(getString(R.string.format_magnitude, mEarthquake.mMag));
            coordView.setText(getString(R.string.format_coordinates,
                    mEarthquake.mLongitude, mEarthquake.mLattitude));

            placeView.setClickable(true);
            coordView.setClickable(true);
            placeView.setOnClickListener(mLocClickListener);
            coordView.setOnClickListener(mLocClickListener);

            tsunamiView.setText(
                    mEarthquake.mTsunami == 1 ? R.string.yes : R.string.no);
        } else {
            rootView.setVisibility(View.GONE);
        }

        return rootView;
    }

    private String convertTime(long time) {
        String convertedTime = null;

        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm:ss a");

        convertedTime = simpleDateFormat.format(date);

        return convertedTime;
    }
}
