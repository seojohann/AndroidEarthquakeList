package com.jseo.earthquakelist.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jseo.earthquakelist.R;
import com.jseo.earthquakelist.data.EarthquakeData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeRecyclerViewAdapter extends
        RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.EarthquakeItemViewHolder> {

    public class EarthquakeItemViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTime;
        public final TextView mPlace;
        public final TextView mMag;
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

    public void setEarthquakeData(List<EarthquakeData> data) {
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
        EarthquakeData earthquakeData = mData.get(position);
        EarthquakeData.Properties earthquakeProperties = earthquakeData.getProperties();
        long timeLong = earthquakeProperties.getTime();
        holder.mTime.setText(convertTime(timeLong));
        holder.mPlace.setText(earthquakeProperties.getPlace());
        holder.mMag.setText(earthquakeProperties.getMag() + "");


//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(EarthquakeDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//                    EarthquakeDetailFragment fragment = new EarthquakeDetailFragment();
//                    fragment.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.earthquake_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, EarthquakeDetailActivity.class);
//                    intent.putExtra(EarthquakeDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//
//                    context.startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    private String convertTime(long time) {
        String convertedTime = null;

        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm:ss a");
        convertedTime = simpleDateFormat.format(date).toString();

        return convertedTime;
    }
}
