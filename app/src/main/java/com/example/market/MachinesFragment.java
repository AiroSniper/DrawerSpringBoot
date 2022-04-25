package com.example.market;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.market.placeholder.PlaceholderContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class MachinesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MachinesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MachinesFragment newInstance(int columnCount) {
        MachinesFragment fragment = new MachinesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    String getUrl = "http://10.0.2.2:8090/machines/all";
    List<Machine> machines = new ArrayList<>();
    RequestQueue queue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

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

                for(int i = 0 ; i < obj.length() ; i++){
                    String id = null , ref = null, dateAchat = null, prix = null, marque = null;
                    try {
                        id = obj.getJSONObject(i).getString("id");
                        ref = obj.getJSONObject(i).getString("reference");
                        dateAchat = obj.getJSONObject(i).getString("dateAchat");
                        prix = obj.getJSONObject(i).getString("prix");
                        marque = obj.getJSONObject(i).getJSONObject("marque").getString("libelle");

                        machines.add(new Machine(Integer.parseInt(id),ref,Double.parseDouble(prix),dateAchat,marque));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_machines_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            MainActivity.adapter = new MyItemRecyclerViewAdapter(machines);
            recyclerView.setAdapter(MainActivity.adapter);
        }
        return view;
    }
}