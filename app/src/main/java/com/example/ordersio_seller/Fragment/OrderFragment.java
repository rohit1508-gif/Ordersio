package com.example.ordersio_seller.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ordersio_seller.Activity.Main2Activity;
import com.example.ordersio_seller.Activity.MainActivity;
import com.example.ordersio_seller.Adapter.ItemAdapter;
import com.example.ordersio_seller.Adapter.OrderAdapter;
import com.example.ordersio_seller.ModalClass.Global;
import com.example.ordersio_seller.ModalClass.OrderList;
import com.example.ordersio_seller.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderFragment extends Fragment {
    RecyclerView recyclerView;
    OrderAdapter adapter;
    List<OrderList> list;
    Context ctx;
    Button button3;
    SwipeRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        Main2Activity.current = "OrderFragment";
//        Bundle b = getArguments();
//        uid=b.getString("uid");
        ctx=getActivity();
        list=new ArrayList<>();
        refreshLayout = view.findViewById(R.id.swipeRefresh);
        button3 = view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle b=new Bundle();
//                b.putString("uid",uid);
                Fragment someFragment = new ItemListFragment();
                assert getFragmentManager() != null;
//                someFragment.setArguments(b);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container1, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateorder();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                updateorder();
                refreshLayout.setRefreshing(false);
            }
        });
    return view;}
    public void updateorder(){
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        String url="https://ordersio.herokuapp.com/orders";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject o = response.getJSONObject(i);
                        if(o.getString("seller").equals(Global.uid)){
                            OrderList l = new OrderList(
                                    o.getString("purchaserName"),
                                    o.getString("purchaserNumber"),
                                    o.getString("itemname"),
                                    o.getString("quantity"),
                                    o.getString("_id")
                            );
                            list.add(l);
                        }
                    }
                    adapter = new OrderAdapter(list,ctx);
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
    }
}
