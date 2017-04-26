package com.jseo.earthquakelist;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.stream.JsonReader;
import com.jseo.earthquakelist.dummy.DummyContent;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            gatherEarthquakeData();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void gatherEarthquakeData() throws IOException {
        AsyncTask<Void, Void, Void> retrieveTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                URLConnection connection;
                InputStream in = null;
                JsonReader jsonReader = null;
                try {
                    URL url = new URL(getString(R.string.quakes_feed_all_day_url));
                    connection = url.openConnection();
                    HttpsURLConnection httpsConnection = (HttpsURLConnection)connection;

                    int responseCode = httpsConnection.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        in = httpsConnection.getInputStream();
                        Log.d("JSBOMB", in.toString());
                        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
                        jsonReader = new JsonReader(inputStreamReader);
                        Log.d("JSBOMB", jsonReader.toString());

                        final ByteArrayOutputStream bo = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        StringBuilder jsonBuilder = new StringBuilder();

                        while (in.read(buffer) > 0) {
                             // Read from Buffer.
                            bo.write(buffer); // Write Into Buffer.
                            jsonBuilder.append(bo.toString());
                        }

                        Log.d("JSBOMB", "json read ::: " + jsonBuilder.toString());
                        Log.d("JSBOMB", bo.toString());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    closeCloseable(in);
                    closeCloseable(jsonReader);
                }
                return null;
            }

            private void closeCloseable(Closeable closeable) {
                try {
                    if (closeable != null) {
                        closeable.close();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };

        retrieveTask.execute();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.earthquake_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(EarthquakeDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        EarthquakeDetailFragment fragment = new EarthquakeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.earthquake_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, EarthquakeDetailActivity.class);
                        intent.putExtra(EarthquakeDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
