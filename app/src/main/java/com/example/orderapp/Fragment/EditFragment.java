package com.example.orderapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderapp.Activity.MainActivity;
import com.example.orderapp.Adapter.EditAdapter;
import com.example.orderapp.Adapter.ItemListAdapter;
import com.example.orderapp.Modalclass.ItemList;
import com.example.orderapp.Modalclass.OrderList;
import com.example.orderapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EditFragment extends Fragment {
    List<OrderList> list;
    private EditAdapter adapter;
    RecyclerView recyclerView;
    Context ctx;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        MainActivity.i=3;
        SharedPreferences preferences = getActivity().getSharedPreferences("OrderApp",getActivity().MODE_PRIVATE);
        final String Token  = preferences.getString("TOKEN",null);//second parameter default value.
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        list = new ArrayList<>();
        view.findViewById(R.id.confirmbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new PaymentFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ctx = getActivity();
        String url = "https://ordersio.herokuapp.com/carts";
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject object = response.getJSONObject(i);
                        OrderList l = new OrderList(
                                object.getString("itemname"),
                                object.getString("price"),
                                object.getString("quantity"),
                                object.getString("seller")
                        );
                        list.add(l);}
                    adapter = new EditAdapter(list,ctx,LoginFragment.userid);
                    recyclerView.setAdapter(adapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }}){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", "Bearer " + Token);
                return headerMap;
            }
        };
        requestQueue.add(request);
        return view;
}}
