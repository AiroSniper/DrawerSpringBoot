package com.example.market;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.market.beans.Machine;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MachinesByYearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachinesByYearFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MachinesByYearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachinesByYearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MachinesByYearFragment newInstance(String param1, String param2) {
        MachinesByYearFragment fragment = new MachinesByYearFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    String getUrl = "http://10.0.2.2:8090/machines/byYear";
    List<Machine> machines = new ArrayList<>();
    RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_machines_by_year, container, false);

        BarChart barChart =v.findViewById(R.id.mbyy);
        queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("ReTAG", response.toString());
                boolean flag = false;
                JSONArray obj = null;
                try {
                    obj = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayList<BarEntry> machines = new ArrayList<>();
                for(int i = 0 ; i < obj.length() ; i++){
                    int year = 0 ;
                    int nbr = 0;
                    try {
                        year = Integer.parseInt( obj.getJSONArray(i).getString(0));
                        nbr = Integer.parseInt(obj.getJSONArray(i).getString(1));

                        machines.add(new BarEntry(year,nbr));

                        Log.d("RespYear",machines.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                BarDataSet barDataSet = new BarDataSet(machines,"Machines");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);

                BarData barData = new BarData(barDataSet);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);
                xAxis.setLabelRotationAngle(270);
                barChart.setFitBars(true);
                barChart.setData(barData);
                barChart.getDescription().setText("Machines par annee");
                barChart.animateY(2000);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("ErrTAG", "Error: " + error.getMessage());
            }
        })
        {


        };

        queue.add(request);

        return v;
    }
}