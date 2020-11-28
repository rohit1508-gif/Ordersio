package com.example.orderapp.Adapter;

import android.content.Context;
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

import com.example.orderapp.Fragment.ItemListFragment;
import com.example.orderapp.Modalclass.SellerList;
import com.example.orderapp.R;

import java.util.List;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {
    List<SellerList> list;
    Context ctx;
    String uid;
    public static String id;
    public SellerAdapter(List<SellerList> list,Context ctx,String uid){
        this.list=list;
        this.ctx=ctx;
        this.uid=uid;
    }
    @NonNull
    @Override
    public SellerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_seller,parent,false);
        return new SellerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAdapter.ViewHolder holder, int position) {
        final SellerList l = list.get(position);
        holder.t1.setText(l.getSellerName());
        holder.t2.setText(l.getSellerAddress());
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new ItemListFragment();
                id=l.getId();
                FragmentManager fragmentManager = ((FragmentActivity) ctx).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2;
        View mview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.sellerName);
            t2 = itemView.findViewById(R.id.sellerAddress);
            mview =itemView;
        }
    }
}
