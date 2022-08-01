package com.jsbomb.earthquakelist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.jsbomb.earthquakelist.R;
import com.jsbomb.earthquakelist.databinding.ActivityEarthquakeDetailBinding;

/**
 * An activity representing a single Earthquake detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link EarthquakeListActivity}.
 */
public class EarthquakeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEarthquakeDetailBinding binding =
                ActivityEarthquakeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.detailToolbar;
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = getIntent().getBundleExtra("args");
            EarthquakeDetailFragment fragment = new EarthquakeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.earthquake_detail_container, fragment)
                    .commit();
        }

        //my id
//        MobileAds.initialize(this);
//        MobileAds.initialize(this, "ca-app-pub-7438807169301480~2950987624");
        //test id
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

//        AdView adview = binding.adView;
//        AdRequest adRequest;
//        adRequest = new AdRequest.Builder().build();
////        adRequest = new AdRequest.Builder().addTestDevice("81A442EFD7E2204CA5092B6AD6AE3029").build();
//        adview.loadAd(adRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, EarthquakeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
