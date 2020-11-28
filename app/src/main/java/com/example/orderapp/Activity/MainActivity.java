package com.example.orderapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderapp.Fragment.ItemListFragment;
import com.example.orderapp.Fragment.LoginFragment;
import com.example.orderapp.Fragment.OrderFragment;
import com.example.orderapp.Fragment.RegisterFragment;
import com.example.orderapp.Fragment.SellerFragment;
import com.example.orderapp.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    TextView t;
    String Token;
    SharedPreferences sp;
    public static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        sp = getSharedPreferences("login",MODE_PRIVATE);
//        sp.edit().putBoolean("logged",true).apply();
        SharedPreferences preferences = MainActivity.this.getSharedPreferences("OrderApp",MainActivity.this.MODE_PRIVATE);
        Token  = preferences.getString("TOKEN",null);//second parameter default value.
        SellerFragment sellerFragment=new SellerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                sellerFragment).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
            case R.id.nav_seller:

                break;
            case R.id.nav_cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new OrderFragment()).commit();
                break;
            case R.id.nav_password:

                break;
            case R.id.nav_logout:
                String url = "https://ordersio.herokuapp.com/users/logout";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent i =new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(i);
//                        sp.edit().putBoolean("logged",false).apply();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Logout",error.toString());
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
                RequestQueue q = Volley.newRequestQueue(MainActivity.this);
                q.add(request);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;}
    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);}
       else if(i == 0){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);}
        else if(i==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SellerFragment()).commit();
        }
        else if(i==2){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ItemListFragment()).commit();
        }
        else if(i==3){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new OrderFragment()).commit();
        }
        else if(i==4){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new OrderFragment()).commit();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SellerFragment()).commit();
    }
}
