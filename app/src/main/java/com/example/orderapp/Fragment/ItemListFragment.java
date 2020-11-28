package com.example.orderapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.example.orderapp.Adapter.ItemListAdapter;
import com.example.orderapp.Adapter.SellerAdapter;
import com.example.orderapp.Modalclass.ItemList;
import com.example.orderapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItemListFragment extends Fragment {
    List<ItemList> list;
    private ItemListAdapter adapter;
    RecyclerView recyclerView;
    Context ctx;
    String id="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itemlist, container, false);
        MainActivity.i=1;
        assert getArguments() != null;
        id= SellerAdapter.id;
//        Log.i("uid",uid);
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        list = new ArrayList<>();
        view.findViewById(R.id.placeorderbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new OrderFragment();
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
        String url = "https://ordersio.herokuapp.com/items";
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                for(int i=0;i<response.length();i++){
                        JSONObject object = response.getJSONObject(i);
                        if(object.getString("owner").equals(id)){
                        ItemList l = new ItemList(
                                object.getString("itemname"),
                                object.getString("price"),
                                object.getString("owner")
                        );
                        list.add(l);}}
                    adapter = new ItemListAdapter(list,ctx,LoginFragment.userid);
                recyclerView.setAdapter(adapter);
                }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }});
        requestQueue.add(request);
        return view; }
}
