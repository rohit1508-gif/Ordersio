package com.example.ordersio_seller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ordersio_seller.ModalClass.OrderList;
import com.example.ordersio_seller.R;

import org.json.JSONObject;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
List<OrderList> list;
Context ctx;
public OrderAdapter(List<OrderList> list,Context ctx){
this.list=list;
this.ctx=ctx;
}
    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycler,parent,false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    OrderList l = list.get(position);
    holder.t.setText(l.getName());
    holder.t1.setText(l.getNumber());
    holder.t2.setText(l.getItemName());
    holder.t3.setText(l.getQuantity());
    holder.b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String url="https://ordersio.herokuapp.com/orders"+l.getId();
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            JsonObjectRequest request= new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(request);
        }
    });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
    TextView t,t1,t2,t3;
    Button b;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t = itemView.findViewById(R.id.name);
            t1=itemView.findViewById(R.id.number);
            t2=itemView.findViewById(R.id.itemname);
            t3=itemView.findViewById(R.id.quantity);
            b=itemView.findViewById(R.id.done);
        }
    }
}
