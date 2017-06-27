package com.demo.tomcat.mpandroidcharttest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = MainActivity.class.getSimpleName();
    final int   ARRAY_SZ = 100;

    LineChart   lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //createChart()

        initView();
        initControl();
    }


    //--- User define function ---//
    private void createChart()
    {
        // programmatically create a LineChart
        LineChart lineChart = new LineChart(getBaseContext());
        // get a layout defined in xml
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        // add the programmatically created chart
        rl.addView(lineChart);
    }

    private void initView()
    {
        lineChart = (LineChart) findViewById(R.id.chart);

    }

    private void initControl()
    {
        final String startDateTime = "2016-01-01 15:00:00 GMT";
        List<Entry>  entries = createUserData(ARRAY_SZ);
        List<Date>  dateTimeList = getDateTimeList(startDateTime, entries.size());
        //HourAxisValueFormatter  havf = new HourAxisValueFormatter(dateTimeList.get(0).getTime());
        final List<String> xLable = getLabels(dateTimeList);
        LineDataSet dataSet = new LineDataSet(entries, "°C");
        //XAxis       xAxis = new XAxis();

        // set line color
        dataSet.setDrawCircleHole(false);
        dataSet.setCircleColor(Color.RED);
        dataSet.setColor(Color.RED);

        // set data to LineChart
        lineChart.setData(new LineData(dataSet));
        //lineChart.setData(getLineData());
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setAxisMaximum(dateTimeList.size());
        lineChart.getXAxis().setAxisMinimum(0);

        lineChart.getXAxis().setGranularity(1f);
        //lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
        //    @Override
        //    public String getFormattedValue(float value, AxisBase axis) {
        //        Log.w(TAG, "setValueFormatter(), value: " + value + ", axis: " + Arrays.toString(axis.mEntries));
        //        return ( xLable.get((int)value-1));
        //    }
        //});


        HourAxisValueFormatter  havf = new HourAxisValueFormatter(dateTimeList.get(0).getTime());
        lineChart.getXAxis().setValueFormatter(havf);

        lineChart.invalidate();
    }

    private List<Entry> createUserData(int size)
    {
        final double  MIN = 32.0;
        final double  MAX = 43.0;
        double[]    dataObjs = new double[size];
        Random      rand = new Random();

        int i=0;
        while (i < size)
        {
            double tmpX = rand.nextDouble() * 100;
            if ((Double.compare(tmpX, MIN) > 0) && (Double.compare(tmpX, MAX) < 1))
            {
                dataObjs[i++] = tmpX;
            }
        }

        List<Entry> entries = new ArrayList<>();
        for (int j=0; j<dataObjs.length; j++)
        {
            // turn your data into Entry objects
            entries.add(new Entry(j, (float) dataObjs[j]));
        }

        Log.w(TAG,  "createUserData(), i: " + i +
                    ", dataObjs.length: " + dataObjs.length +
                    ", entries.size: " + entries.size());

        //debug message
        System.out.println();
        for (int k=0; k<dataObjs.length; k++)
        {
            if ((k>0) && (k%10 == 0))
                System.out.println();
            System.out.printf("[%03d]: %4.2f, ", k, dataObjs[k]);

            //Log.w(TAG, "")
        }
        System.out.println();

        return  entries;
    }

    private List<Date> getDateTimeList(String startDT, int records)
    {
        List<Date>  dtList = new ArrayList<>();
        SimpleDateFormat    sdf =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
        Date    tmpDate = null;

        try
        {
            tmpDate = sdf.parse(startDT);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        dtList.add(tmpDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tmpDate);

        for (int i=1; i<records; i++)
        {
            calendar.add(Calendar.MINUTE, 1);
            //Log.d(TAG, "[" + i +"]: " + calendar.getTime().toString());
            dtList.add(calendar.getTime());
        }

        System.out.printf("records: %02d, dtList.size(): %02d %n", records, dtList.size());

        //--- debug
        for (int i=0; i<dtList.size(); i++)
        {
            Log.d(TAG, "[" + i +"]: " + dtList.get(i).toString());
            //if ((i!=0) && (i%3==0))
            //    System.out.printf("%n");
            //System.out.printf("[%02d]: %s, ", i, dtList.get(i));
        }

        return dtList;
    }

    private List<String> getLabels(List<Date> tmpDate)
    {
        List<String> chartLabels = new ArrayList<>();
        for(int i=0; i<tmpDate.size(); i++)
        {
            //chartLabels.add("X" + i);
            chartLabels.add("" + tmpDate.get(i));
        }
        return chartLabels;
    }

}

