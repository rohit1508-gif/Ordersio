package com.example.orderapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderapp.Fragment.OrderFragment;
import com.example.orderapp.Modalclass.OrderList;
import com.example.orderapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<OrderList> list;
    Context ctx;
    public OrderAdapter(List<OrderList> list, Context ctx){
        this.list=list;
        this.ctx = ctx;
    }
    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, final int position) {
        SharedPreferences preferences = ctx.getSharedPreferences("OrderApp",ctx.MODE_PRIVATE);
        final String Token  = preferences.getString("TOKEN",null);//second parameter default value.
        final OrderList l = list.get(position);
        holder.t1.setText(l.getItemname());
        holder.t2.setText(l.getPrice());
        holder.t3.setText(l.getQuantity());
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ordersio.herokuapp.com/carts";
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject o = response.getJSONObject(i);
                                String name = o.getString("itemname");
                                if(name.equals(l.getItemname())){
                                    String id = o.getString("_id");
                                    String url = "https://ordersio.herokuapp.com/carts/" + id;
                                    JsonObjectRequest obj = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            list.remove(position);
                                         notifyItemRemoved(position);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("delete",error.toString());
                                        }
                                    }){
                                        @Override
                                        public Map<String, String> getHeaders() throws AuthFailureError {
                                            Map<String, String> headerMap = new HashMap<String, String>();
                                            headerMap.put("Content-Type", "application/json");
                                            headerMap.put("Authorization", "Bearer " + Token);
                                            return headerMap;
                                        }
                                    };
                                    RequestQueue q2 = Volley.newRequestQueue(ctx);
                                    q2.add(obj);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headerMap = new HashMap<String, String>();
                        headerMap.put("Content-Type", "application/json");
                        headerMap.put("Authorization", "Bearer " + Token);
                        return headerMap;
                    }
                };
                RequestQueue q = Volley.newRequestQueue(ctx);
                q.add(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        Button b1,b2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.itemname);
            t2 = itemView.findViewById(R.id.itemprice);
            t3 = itemView.findViewById(R.id.itemamount);
            b1= itemView.findViewById(R.id.deletebutton);
        }
    }
}
