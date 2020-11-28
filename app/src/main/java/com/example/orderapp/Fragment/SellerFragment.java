package com.example.orderapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderapp.Activity.MainActivity;
import com.example.orderapp.Adapter.ItemListAdapter;
import com.example.orderapp.Adapter.SellerAdapter;
import com.example.orderapp.Modalclass.ItemList;
import com.example.orderapp.Modalclass.SellerList;
import com.example.orderapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SellerFragment extends Fragment {
    List<SellerList> list;
    SellerAdapter adapter;
    RecyclerView recyclerView;
    Context ctx;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller, container, false);
        MainActivity.i=0;
        ctx=getActivity();
        list=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        String url="https://ordersio.herokuapp.com/users";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject o = response.getJSONObject(i);
                        if(o.getString("type").equals("Seller")){
                        SellerList l = new SellerList(
                                o.getString("shopname"),
                                o.getString("address"),
                                o.getString("_id")
                        );
                        list.add(l);
                        Log.i("seller", String.valueOf(l));
                    }}
                    adapter = new SellerAdapter(list,ctx,LoginFragment.userid);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
        return  view;
    }
}
