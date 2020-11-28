package com.example.ordersio_seller.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ordersio_seller.Fragment.OrderFragment;
import com.example.ordersio_seller.R;

public class Main2Activity extends AppCompatActivity {

    SharedPreferences sp;
    String uid="",Token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        uid=i.getStringExtra("uid");
//        sp = getSharedPreferences("login",MODE_PRIVATE);
//        sp.edit().putBoolean("logged",true).apply();
        SharedPreferences preferences = Main2Activity.this.getSharedPreferences("Ordersio",Main2Activity.this.MODE_PRIVATE);
        Token  = preferences.getString("TOKEN",null);//second parameter default value.
        Bundle b =new Bundle();
        b.putString("uid",uid);
        OrderFragment orderFragment=new OrderFragment();
        orderFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
               orderFragment).commit();
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}