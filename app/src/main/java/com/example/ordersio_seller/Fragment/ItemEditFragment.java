package com.example.ordersio_seller.Fragment;

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
import com.example.ordersio_seller.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemEditFragment extends Fragment {
    EditText editText,editText1;
    Button button;
    String itemName,itemPrice,Token,id="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edititem, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("Ordersio",getActivity().MODE_PRIVATE);
        Bundle b= getArguments();
        id=b.getString("id");
        Token  = preferences.getString("TOKEN",null);//second parameter default value.
        editText=view.findViewById(R.id.itemName);
        editText1=view.findViewById(R.id.itemPrice);
        button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemName=editText.getText().toString().trim();
                itemPrice=editText1.getText().toString().trim();
                if(itemName.isEmpty()){
                    editText.setError("Please enter Item Name");
                    editText.requestFocus();
                }
                else if(itemPrice.isEmpty()){editText1.setError("Please enter Price");
                    editText1.requestFocus();}
                else{
                    add(itemName,itemPrice);
                }
            }
        });
        return view;
    }
    public void add(String itemName,String itemPrice){
        String url="https://ordersio.herokuapp.com/items/" + id;
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        JSONObject o= new JSONObject();
        try{
            o.put(" itemname",itemName);
            o.put("price",itemPrice);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.PATCH, url, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(),"Item is successfully edited!",Toast.LENGTH_SHORT).show();
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

