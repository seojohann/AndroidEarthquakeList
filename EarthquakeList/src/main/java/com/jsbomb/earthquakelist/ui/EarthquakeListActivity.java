package com.jsbomb.earthquakelist.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jsbomb.earthquakelist.R;
import com.jsbomb.earthquakelist.actors.DataRetriever;
import com.jsbomb.earthquakelist.actors.EarthquakeDataRetriever;
import com.jsbomb.earthquakelist.actors.EarthquakeDataViaVolleyRetriever;
import com.jsbomb.earthquakelist.data.EarthquakeData;
import com.jsbomb.earthquakelist.data.EarthquakesSummary;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * An activity representing a list of Earthquakes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EarthquakeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EarthquakeListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private EarthquakeRecyclerViewAdapter mAdapter;

    private FirebaseAnalytics mFirebaseAnalytics;
    private InterstitialAd mInterstitialAd;

    private MagnitudeFilter mMagnitudeFilter = MagnitudeFilter.ALL;
    public enum MagnitudeFilter {
        ALL(0),
        MAG_10(1),
        MAG_25(2),
        MAG_45(3),
        SIG(4);

        private int mIndex;

        MagnitudeFilter(int filter) {
            mIndex = filter;
        }
    }

    private static final int URL_RESOURCES[] = {
            R.string.quakes_feed_all_day_url,
            R.string.quakes_feed_mag_1_0_day_url,
            R.string.quakes_feed_mag_2_5_day_url,
            R.string.quakes_feed_mag_4_5_day_url,
            R.string.quakes_feed_significant_day_url
    };

    private static final int SUBTITLE_RESOURCES[] = {
            R.string.list_subtitle_all,
            R.string.list_subtitle_m10,
            R.string.list_subtitle_m25,
            R.string.list_subtitle_m45,
            R.string.list_subtitle_significant
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.earthquake_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.earthquake_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //my id
        MobileAds.initialize(this, "ca-app-pub-7438807169301480~2950987624");
        //test id
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        AdView adview = (AdView)findViewById(R.id.adView);
        AdRequest adRequest;
        adRequest = new AdRequest.Builder().build();
//        adRequest = new AdRequest.Builder().addTestDevice("81A442EFD7E2204CA5092B6AD6AE3029").build();
        adview.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        //my id
        mInterstitialAd.setAdUnitId("ca-app-pub-7438807169301480/7226587631");
        //test id
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

    }

    @Override
    protected void onStart() {
        super.onStart();

        gatherEarthquakeData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mInterstitialAd != null && !mInterstitialAd.isLoaded()) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
//            mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("81A442EFD7E2204CA5092B6AD6AE3029").build());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.main_menu_filter:
                FragmentManager fragmentManager = getSupportFragmentManager();
                SelectMagnitudeDialog selectMagnitudeDialog = new SelectMagnitudeDialog();
                selectMagnitudeDialog.setFilterSelectedListener(new FilterSelectedListener() {
                    @Override
                    public void onFilterSelected(int which) {
                        mMagnitudeFilter.mIndex = which;
                        gatherEarthquakeData();
                    }
                });
                selectMagnitudeDialog.show(fragmentManager, "select");
                return true;

            case R.id.main_menu_view_ad:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                return true;

            case R.id.main_menu_menu:
                Intent intent = new Intent();
                intent.setPackage("com.jsbomb.earthquakelist");
                intent.setClassName("com.jsbomb.earthquakelist", GdaxActivity.class.getName());
                startActivity(intent);

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * connect to USGS and retrieve earthquake data. it would be a good idea to prevent connecting
     * on every onResume(), but will consider that in the future
     */
    private void gatherEarthquakeData() {

        //choose which method to retrieve. httpurlconnection
        DataRetriever dataRetriever = new EarthquakeDataRetriever();
        //or volley, released from google
        dataRetriever = new EarthquakeDataViaVolleyRetriever(this);
        String urlString = getString(URL_RESOURCES[mMagnitudeFilter.mIndex]);
        try {
            URL url = new URL(urlString);
            dataRetriever.setUrlString(urlString);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "invalid URL set", Toast.LENGTH_SHORT).show();
            return;
        }

        DataRetriever.OnRetrieveCompleteListener onRetrieveCompleteListener =
                new DataRetriever.OnRetrieveCompleteListener() {
                    @Override
                    public void onRetrieveComplete(final boolean isSuccess, final Object retrievedData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess &&
                                        retrievedData != null &&
                                        retrievedData instanceof EarthquakesSummary) {
                                    EarthquakesSummary earthquakesSummary =
                                            (EarthquakesSummary)retrievedData;
                                    List<EarthquakeData> earthquakeDataList =
                                            earthquakesSummary.getEarthquakeList();
                                    if (earthquakeDataList.isEmpty()) {
                                        displayEmptyList();
                                    } else {
                                        displayUpdatedData(earthquakeDataList);
                                    }
                                } else if (!isSuccess) {
                                    displayRetrievalFailed();
                                } else {
                                    displayEmptyList();
                                }
                            }
                        });
                    }
                };
        dataRetriever.setOnRetrieveCompleteListener(onRetrieveCompleteListener);

        dataRetriever.retrieve();

    }

    private void displayUpdatedData(List<EarthquakeData> updatedData) {
        mAdapter.setEarthquakeData(updatedData);
        mAdapter.notifyDataSetChanged();
        findViewById(R.id.earthquake_list).setVisibility(View.VISIBLE);
        View detailContainer = findViewById(R.id.earthquake_detail_container);
        if (detailContainer != null) {
            detailContainer.setVisibility(View.GONE);
        }
        findViewById(R.id.list_empty_text).setVisibility(View.GONE);
        findViewById(R.id.retrieval_failed_layout).setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle(SUBTITLE_RESOURCES[mMagnitudeFilter.mIndex]);
    }

    private void displayEmptyList() {
        mAdapter.setEarthquakeData(new ArrayList<EarthquakeData>());
        mAdapter.notifyDataSetChanged();
        findViewById(R.id.earthquake_list).setVisibility(View.GONE);
        View detailContainer = findViewById(R.id.earthquake_detail_container);
        if (detailContainer != null) {
            detailContainer.setVisibility(View.GONE);
        }
        findViewById(R.id.list_empty_text).setVisibility(View.VISIBLE);
        findViewById(R.id.retrieval_failed_layout).setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle(SUBTITLE_RESOURCES[mMagnitudeFilter.mIndex]);
    }

    private void displayRetrievalFailed() {
        mAdapter.setEarthquakeData(new ArrayList<EarthquakeData>());
        mAdapter.notifyDataSetChanged();
        findViewById(R.id.earthquake_list).setVisibility(View.GONE);
        View detailContainer = findViewById(R.id.earthquake_detail_container);
        if (detailContainer != null) {
            detailContainer.setVisibility(View.GONE);
        }
        findViewById(R.id.list_empty_text).setVisibility(View.GONE);
        findViewById(R.id.retrieval_failed_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.retry_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gatherEarthquakeData();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle(SUBTITLE_RESOURCES[mMagnitudeFilter.mIndex]);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new EarthquakeRecyclerViewAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }


    public class EarthquakeRecyclerViewAdapter extends
            RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.EarthquakeItemViewHolder> {

        private Context mContext;

        public EarthquakeRecyclerViewAdapter(Context context) {
            super();
            mContext = context;
        }

        public class EarthquakeItemViewHolder extends RecyclerView.ViewHolder {
            private final View mView;
            private final TextView mTime;
            private final TextView mPlace;
            private final TextView mMag;
            //EarthquakeData mData;

            public EarthquakeItemViewHolder(View view) {
                super(view);
                mView = view;
                mTime = (TextView)view.findViewById(R.id.time);
                mPlace = (TextView)view.findViewById(R.id.place);
                mMag = (TextView)view.findViewById(R.id.magnitude);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTime.getText() + " " + mPlace.getText()
                        + " " + mMag.getText() + "'";
            }
        }

        List<EarthquakeData> mData = null;

        private void setEarthquakeData(List<EarthquakeData> data) {
            mData = data;
        }

        @Override
        public EarthquakeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.earthquake_list_item, parent, false);
            return new EarthquakeItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EarthquakeItemViewHolder holder, int position) {
            final EarthquakeData earthquakeData = mData.get(position);
            final EarthquakeData.Properties earthquakeProperties = earthquakeData.getProperties();
            long timeLong = earthquakeProperties.getTime();
            holder.mTime.setText(convertTime(timeLong));
            holder.mPlace.setText(earthquakeProperties.getPlace());
            double mag = earthquakeProperties.getMag();
            holder.mMag.setText(mContext.getString(R.string.format_magnitude, mag));


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle arguments = new Bundle();
                    arguments.putLong(EarthquakeDetailFragment.ARG_ITEM_TIME,
                            earthquakeProperties.getTime());
                    arguments.putString(EarthquakeDetailFragment.ARG_ITEM_PLACE,
                            earthquakeProperties.getPlace());
                    arguments.putDouble(EarthquakeDetailFragment.ARG_ITEM_MAG,
                            earthquakeProperties.getMag());
                    arguments.putDouble(EarthquakeDetailFragment.ARG_ITEM_LONGI,
                            earthquakeData.getGeometry().getLongitude());
                    arguments.putDouble(EarthquakeDetailFragment.ARG_ITEM_LATTI,
                            earthquakeData.getGeometry().getLatitude());
                    arguments.putInt(EarthquakeDetailFragment.ARG_ITEM_TSUNAMI,
                            earthquakeProperties.getTsunami());

                    if (mTwoPane) {
                        View detailContainer = findViewById(R.id.earthquake_detail_container);
                        if (detailContainer != null) {
                            detailContainer.setVisibility(View.VISIBLE);
                        }
                        EarthquakeDetailFragment fragment = new EarthquakeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.earthquake_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, EarthquakeDetailActivity.class);
                        intent.putExtra("args", arguments);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (mData == null) {
                return 0;
            }
            return mData.size();
        }

        private String convertTime(long time) {
            Date date = new Date(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm:ss a");

            return simpleDateFormat.format(date);
        }
    }

    interface FilterSelectedListener {
        void onFilterSelected(int which);
    }

    public static class SelectMagnitudeDialog extends DialogFragment {
        FilterSelectedListener mListener;

        void setFilterSelectedListener(FilterSelectedListener listener) {
            mListener = listener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle(R.string.select_dialog_title);
            dialogBuilder.setItems(R.array.magnitude_items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    if (mListener != null) {
                        mListener.onFilterSelected(which);
                    }
                }
            });

            return dialogBuilder.create();
        }
    }
}
