package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPERATOR = " of ";

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);
        TextView magText = listItemView.findViewById(R.id.magnitude);
        magText.setText(formatMagnitude(currentEarthquake.getmMagnitude()));

        GradientDrawable magnitudeCircle = (GradientDrawable) magText.getBackground();
        magnitudeCircle.setColor(getMagnitudeColor(currentEarthquake.getmMagnitude()));

        String primaryLocation, locationOffset;
        String originalLocation = currentEarthquake.getmLocation();
        if (originalLocation.contains(LOCATION_SEPERATOR)) {
            String parts[] = originalLocation.split(LOCATION_SEPERATOR);
            primaryLocation = parts[1];
            locationOffset = parts[0] + LOCATION_SEPERATOR;
        } else {
            primaryLocation = originalLocation;
            locationOffset = getContext().getString(R.string.near_the);
        }

        TextView primaryText = listItemView.findViewById(R.id.location_place);
        primaryText.setText(primaryLocation);

        TextView offsetText = listItemView.findViewById(R.id.location_offset);
        offsetText.setText(locationOffset);

        TextView timeText = listItemView.findViewById(R.id.time);
        timeText.setText(formatTime(currentEarthquake.getmMilliSeconds()));

        TextView dateText = listItemView.findViewById(R.id.date);
        dateText.setText(formatDate(currentEarthquake.getmMilliSeconds()));

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorId = R.color.magnitude9;
                break;
            default:
                magnitudeColorId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorId);
    }


    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private String formatTime(long time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(time);
    }

    private String formatDate(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(time);
    }
}
