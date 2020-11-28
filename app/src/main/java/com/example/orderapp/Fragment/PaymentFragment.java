package com.example.orderapp.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderapp.Activity.MainActivity;
import com.example.orderapp.Adapter.EditAdapter;
import com.example.orderapp.Modalclass.OrderList;
import com.example.orderapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PaymentFragment extends Fragment {
    private int r=0;
    TextView Totalprice;
    String uid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        MainActivity.i=4;
       uid=LoginFragment.userid;
        SharedPreferences preferences = getActivity().getSharedPreferences("OrderApp",getActivity().MODE_PRIVATE);
        final String Token  = preferences.getString("TOKEN",null);//second parameter default value.
        Totalprice = view.findViewById(R.id.Totalprice);
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        String url = "https://ordersio.herokuapp.com/carts";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject object = response.getJSONObject(i);
                        int a =Integer.parseInt(object.getString("price"));
                        int b = Integer.parseInt(object.getString("quantity"));
                        r+=a*b;
                       }
                    Totalprice.setText("Total price = " + r);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Payment", error.toString());
            }}){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", "Bearer " + Token);
                return headerMap;
            }
        };
        requestQueue.add(request);
        return view;}
}
