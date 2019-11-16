package com.example.monitoringair;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monitoringair.Support.TempleteChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChartFragment extends Fragment {


    //Chart and it's Array
    LineChart mlineChart;
    LineDataSet lineDataSet = new LineDataSet (null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    ArrayList<Entry>yValue;

    TextView tulisan;

    //Date and Format
    Date now = new Date();
    long tanggalpenentu = now.getTime()/1000;
    SimpleDateFormat sdf = new SimpleDateFormat("mm-HH-dd");

    //FIREBASE
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference("history");



    //GET THE LAYOUT FROM FRAGMENT_CHART
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        tulisan=view.findViewById(R.id.grafikHargaFrag);
        mlineChart=view.findViewById(R.id.lineChartFrag);

        mlineChart.setDragEnabled(true);
        mlineChart.setScaleEnabled(true);
        styleGraph();

        yValue = new ArrayList<>();

        //GET DATA FIREBASE TO CHART
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot mydatasnapshot : dataSnapshot.getChildren()){
                       TempleteChart datapoint = mydatasnapshot.getValue(TempleteChart.class);



                       if(datapoint.getDate()>=1538927258){
                           yValue.add(new Entry(datapoint.getDate(),(long)datapoint.getHarga()));
                           if(yValue.size()>4){
                              // yValue.remove(0);
                           }
                       }


                    }
                    showChart(yValue);
                }
                else {
                    mlineChart.clear();
                    mlineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void showChart(ArrayList<Entry> yValue) {

        //CONFIGURATION LINE CHART
        lineDataSet.setValues(yValue);
        lineDataSet.setValueTextSize(15f);
        lineDataSet.setCircleRadius(8f);
        lineDataSet.setLabel("Menit-Jam-Hari");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);

        lineData=new LineData(iLineDataSets);
        mlineChart.clear();
        mlineChart.setData(lineData);
        mlineChart.invalidate();

    }

    private void styleGraph () {

        //CUSTOM X AND Y AXIS
        final XAxis xAxis = mlineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(45);


        mlineChart.getAxisRight().setEnabled(false);


        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                xAxis.setLabelCount(5,true);
                long datenya = (long) value;

              return sdf.format(datenya*1000) ;

            }
        });

    }


}
