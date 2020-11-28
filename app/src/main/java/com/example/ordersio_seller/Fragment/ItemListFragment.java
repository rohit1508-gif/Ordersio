package com.example.ordersio_seller.Fragment;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ordersio_seller.Adapter.ItemAdapter;
import com.example.ordersio_seller.ModalClass.ItemList;
import com.example.ordersio_seller.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemListFragment extends Fragment {
    ItemAdapter adapter;
    RecyclerView recyclerView;
    List<ItemList> list;
    Context ctx;
    String uid="";
    FloatingActionButton floatingActionButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        Bundle b = getArguments();
        uid=b.getString("uid");
        list=new ArrayList<>();
        ctx=getActivity();
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=new Bundle();
                b.putString("uid",uid);
                Fragment someFragment = new NewItemFragment();
                assert getFragmentManager() != null;
                someFragment.setArguments(b);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container1, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        String url="https://ordersio.herokuapp.com/items";
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                for(int i=0;i<response.length();i++){
                    JSONObject obj=response.getJSONObject(i);
                    if(obj.getString("owner").equals(uid)){
                        ItemList l = new ItemList(
                                obj.getString("itemname"),
                                obj.getString("price"),
                                obj.getString("_id")
                        );
                        list.add(l);
                    }}
                    adapter = new ItemAdapter(list,ctx);
                    recyclerView.setAdapter(adapter);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    return view;}
}
