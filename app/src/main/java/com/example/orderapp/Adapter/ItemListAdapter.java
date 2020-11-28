package com.example.orderapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.orderapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    List<ItemList> list;
    Context ctx;
    int i;
    int r=0;
    String uid="";
    public ItemListAdapter(List<ItemList> list, Context ctx,String uid){
        this.list=list;
        this.ctx = ctx;
        this.uid=uid;
    }
    @NonNull
    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_itemlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListAdapter.ViewHolder holder, int position) {
        SharedPreferences preferences = ctx.getSharedPreferences("OrderApp",ctx.MODE_PRIVATE);
        final String Token  = preferences.getString("TOKEN",null);//second parameter default value.
        final ItemList l = list.get(position);
        holder.itemname.setText(l.getItemname());
        holder.itemprice.setText(l.getPrice());
        holder.e.setMaxValue(4);
        holder.e.setMinValue(0);
        holder.e.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                i = newVal;
            }
        });
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i!=0) {
                    final JSONObject o = new JSONObject();
                    try {
                        o.put("itemname",l.getItemname());
                        o.put("price",l.getPrice());
                        o.put("quantity",i);
                        o.put("seller",l.getSellerID());} catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final JSONObject o2 = new JSONObject();
                    try{
                        o2.put("quantity",i);
                        o2.put("seller",l.getSellerID());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url = "https://ordersio.herokuapp.com/carts";
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try{
                           for(int i=0;i<response.length();i++){
                                   JSONObject o1 = response.getJSONObject(i);
                                   String name = o1.getString("itemname");
                                   if(name.equals(l.getItemname())){
                                       String id = o1.getString("_id");
                                       String url = "https://ordersio.herokuapp.com/carts/" + id ;
                                       JsonObjectRequest r1 = new JsonObjectRequest(Request.Method.PATCH, url, o2, new Response.Listener<JSONObject>() {
                                           @Override
                                           public void onResponse(JSONObject response) {

                                           }
                                       }, new Response.ErrorListener() {
                                           @Override
                                           public void onErrorResponse(VolleyError error) {
                                           }
                                       }) {
                                           @Override
                                           public Map<String, String> getHeaders() throws AuthFailureError {
                                               Map<String, String> headerMap = new HashMap<String, String>();
                                               headerMap.put("Content-Type", "application/json");
                                               headerMap.put("Authorization", "Bearer " + Token);
                                               return headerMap;
                                           }
                                       };
                                       RequestQueue q2 = Volley.newRequestQueue(ctx);
                                       q2.add(r1);
                                       break;
                                   }
                                   else{ r++;}
                               }
                           if(r==response.length()){
                               String url = "https://ordersio.herokuapp.com/carts";
                               JsonObjectRequest req= new JsonObjectRequest(Request.Method.POST, url, o, new Response.Listener<JSONObject>() {
                                   @Override
                                   public void onResponse(JSONObject response) {

                                   }
                               }, new Response.ErrorListener() {
                                   @Override
                                   public void onErrorResponse(VolleyError error) {
                                       Log.i("Post",error.toString());
                                   }
                               })
                               {
                                   @Override
                                   public Map<String, String> getHeaders() throws AuthFailureError {
                                       Map<String, String> headerMap = new HashMap<String, String>();
                                       headerMap.put("Content-Type", "application/json");
                                       headerMap.put("Authorization", "Bearer " + Token);
                                       return headerMap;
                                   }
                               };RequestQueue q1 = Volley.newRequestQueue(ctx);
                               q1.add(req);
                           }
                           }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Ro",error.toString());
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headerMap = new HashMap<String, String>();
                            headerMap.put("Content-Type", "application/json");
                            headerMap.put("Authorization", "Bearer " + Token);
                            return headerMap;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(ctx);
                    queue.add(request);}
                else{
                    Toast.makeText(ctx,"Quantity cannot be null",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemname,itemprice;
        NumberPicker e;
      Button b;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname =itemView.findViewById(R.id.itemname);
            itemprice = itemView.findViewById(R.id.itemprice);
            b = itemView.findViewById(R.id.okbutton);
            e = itemView.findViewById(R.id.itemamount);
        }
    }
}
