package com.example.orderapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderapp.Modalclass.ItemList;
import com.example.orderapp.Modalclass.OrderList;
import com.example.orderapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.ViewHolder> {
    List<OrderList> list;
    Context ctx;
    int i;
    String uid="";
    public EditAdapter(List<OrderList> list, Context ctx,String uid){
        this.list=list;
        this.ctx = ctx;
        this.uid=uid;
    }
    @NonNull
    @Override
    public EditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_edit,parent,false);
        return new EditAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EditAdapter.ViewHolder holder, int position) {
        SharedPreferences preferences = ctx.getSharedPreferences("OrderApp",ctx.MODE_PRIVATE);
        final String Token  = preferences.getString("TOKEN",null);//second parameter default value.
        final OrderList l = list.get(position);
        holder.t1.setText(l.getItemname());
        holder.t2.setText(l.getPrice());
        holder.e1.setText(l.getQuantity());
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = Integer.parseInt(holder.e1.getText().toString());
               String url =  "https://ordersio.herokuapp.com/carts";
                JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int j=0;j<response.length();j++){
                                JSONObject object = response.getJSONObject(j);
                                String name = object.getString("itemname");
                                if(name.equals(l.getItemname())){
                                    String id = object.getString("_id");
                                    if(i!=0){
                                        String url = "https://ordersio.herokuapp.com/carts/" + id;
                                        JSONObject o1 = new JSONObject();
                                        o1.put("quantity",i);
                                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH, url, o1, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                            Log.i("patch",error.toString());
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
                                        q2.add(request);
                                    }
                                    else{
                                        String url = "https://ordersio.herokuapp.com/carts/" + id;
                                        JsonObjectRequest re = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

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
                                        RequestQueue q3 = Volley.newRequestQueue(ctx);
                                        q3.add(re);
                                    }
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
                q.add(req);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2;
        Button b1;
        EditText e1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.itemname);
            t2 = itemView.findViewById(R.id.itemprice);
            e1 = itemView.findViewById(R.id.itemamount);
            b1 = itemView.findViewById(R.id.okbutton);
        }
    }
}
