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
import androidx.fragment.app.FragmentTransaction;

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

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    EditText editText,editText1;
    String email,password;
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        editText=view.findViewById(R.id.shopName);
        editText1=view.findViewById(R.id.Address);
        button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editText.getText().toString().trim();
                password=editText1.getText().toString().trim();
                if(email.isEmpty()){
                    editText.setError("Please enter Email Id");
                    editText.requestFocus();
                }
                else if(password.isEmpty()){editText1.setError("Please enter Password");
                    editText1.requestFocus();}
                else{
                    login(email,password);
                }
            }
        });
        return view;
}
public void login(String email,String password){
    RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
    String url = "https://ordersio.herokuapp.com/users/login";
    JSONObject object = new JSONObject();
    try{
        object.put("email",email);
        object.put("password",password);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Toast.makeText(getActivity(),"Login Successfully!",Toast.LENGTH_SHORT).show();
            try{
                String type=response.getJSONObject("user").getString("type");
                Global.uid=response.getJSONObject("user").getString("_id");
                if(type.equals("Purchaser")){
//                    Bundle bundle=new Bundle();
//                    bundle.putString("uid",uid);
                    Fragment someFragment = new RegistrationFragment();
                    assert getFragmentManager() != null;
//                    someFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, someFragment );
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else{
                    Intent i = new Intent(getActivity(), Main2Activity.class);
//                    i.putExtra("uid",uid);
                    startActivity(i);
                }
                String token = response.getString("token");
                SharedPreferences preferences = getActivity().getSharedPreferences("Ordersio", MODE_PRIVATE);
                preferences.edit().putString("TOKEN",token).apply();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getActivity(),"Please check your email and password!",Toast.LENGTH_SHORT).show();
        }
    });
    requestQueue.add(request);
}
}
