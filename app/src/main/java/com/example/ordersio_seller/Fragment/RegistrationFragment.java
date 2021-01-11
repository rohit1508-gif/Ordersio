package com.example.ordersio_seller.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ordersio_seller.Activity.Main2Activity;
import com.example.ordersio_seller.ModalClass.Global;
import com.example.ordersio_seller.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationFragment extends Fragment {
    EditText editText,editText1;
    Button button;
    String shopname,address,Token;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
//        Bundle bundle = getArguments();
//        assert bundle != null;
//        uid=bundle.getString("uid");
        SharedPreferences preferences = getActivity().getSharedPreferences("Ordersio",getActivity().MODE_PRIVATE);
        Token  = preferences.getString("TOKEN",null);//second parameter default value.
        editText=view.findViewById(R.id.shopName);
        editText1=view.findViewById(R.id.Address);
        button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopname=editText.getText().toString().trim();
                address=editText1.getText().toString().trim();
                if(shopname.isEmpty()){
                    editText.setError("Please enter ShopName");
                    editText.requestFocus();
                }
                else if(address.isEmpty()){editText1.setError("Please enter ShopAddress");
                    editText1.requestFocus();}
                else{
                    register(shopname,address);
                }
            }
        });
    return view;
    }
    public void register(String shopname,String address){
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        String url = "https://ordersio.herokuapp.com/users/update";
        JSONObject object = new JSONObject();
        try{
            object.put("shopname",shopname);
            object.put("address",address);
            object.put("type","Seller");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.PATCH, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(),"Successfully registered as a seller",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), Main2Activity.class);
//                i.putExtra("uid", Global.uid);
                startActivity(i);
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
}
