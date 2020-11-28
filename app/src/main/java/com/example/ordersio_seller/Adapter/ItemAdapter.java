package com.example.ordersio_seller.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ordersio_seller.Fragment.ItemEditFragment;
import com.example.ordersio_seller.Fragment.RegistrationFragment;
import com.example.ordersio_seller.ModalClass.ItemList;
import com.example.ordersio_seller.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<ItemList> list;
    Context ctx;
    String Token;
    public ItemAdapter(List<ItemList> list,Context ctx){
        this.list=list;
        this.ctx=ctx;
    }
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences preferences = ctx.getSharedPreferences("Ordersio",ctx.MODE_PRIVATE);
        Token  = preferences.getString("TOKEN",null);//second parameter default value.
        ItemList l = list.get(position);
        holder.e.setText(l.getItemName());
        holder.e1.setText(l.getItemPrice());
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("id",l.getId());
                Fragment someFragment = new ItemEditFragment();
                someFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((FragmentActivity) ctx).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container1, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://ordersio.herokuapp.com/items/" + l.getId();
                RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(ctx));
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        list.remove(position);
                        notifyItemRemoved(position);
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
                requestQueue.add(request);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView e,e1;
        Button b,b1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            e=itemView.findViewById(R.id.tv);
            e1=itemView.findViewById(R.id.tv1);
            b=itemView.findViewById(R.id.edit);
            b1=itemView.findViewById(R.id.delete);
        }
    }
}
