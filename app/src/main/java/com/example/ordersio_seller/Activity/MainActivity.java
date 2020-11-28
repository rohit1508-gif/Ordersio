package com.example.ordersio_seller.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ordersio_seller.Fragment.LoginFragment;
import com.example.ordersio_seller.R;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        sp = getSharedPreferences("login",MODE_PRIVATE);
//        if(sp.getBoolean("logged",true)){
//            Intent i = new Intent(MainActivity.this,MainActivity.class);
//            startActivity(i);
//        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new LoginFragment()).commit();

    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}