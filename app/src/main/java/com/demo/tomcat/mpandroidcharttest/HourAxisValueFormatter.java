package com.demo.tomcat.mpandroidcharttest;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yasir on 02/06/16.
 */
public class HourAxisValueFormatter implements IAxisValueFormatter
{
    private long referenceTimestamp; // minimum timestamp in your data set
    private DateFormat mDataFormat;
    private Date mDate;

    public HourAxisValueFormatter(long referenceTimestamp)
    {
        this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        this.mDate = new Date();
    }

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     */
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        //long convertedTimestamp = (long) value;
        // convertedTimestamp = originalTimestamp - referenceTimestamp;
        long convertedTimestamp = (long) value *100;

        // Retrieve original timestamp
        long originalTimestamp = referenceTimestamp + convertedTimestamp;

        Log.w("x axis", "getFormattedValue(), value: " + value +
                ", referenceTimestamp: " + referenceTimestamp +
                ", originalTimestamp: " + originalTimestamp +
                ", new date: " + new Date(originalTimestamp));


        // Convert timestamp to hour:minute
        return getHour(originalTimestamp);
    }

    public int getDecimalDigits() {
        return (int)(mDate.getTime()/60000);
    }

    private String getHour(long timestamp){
        try{
            mDate.setTime(timestamp*1000);
            Log.w("getHour()", "mDate.setTime: " + mDataFormat.format(mDate));
            return mDataFormat.format(mDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}