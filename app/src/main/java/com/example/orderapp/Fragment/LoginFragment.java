package com.example.orderapp.Fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.example.orderapp.Activity.MainActivity;
import com.example.orderapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.content.Context.LAUNCHER_APPS_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class
LoginFragment extends Fragment {
    EditText editText, editText2;
    Button button;
    TextView textView2;
    String email,password;
    RequestQueue requestQueue;
    public static String userid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_login, container, false);
        editText = view.findViewById(R.id.name);
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        editText2 = view.findViewById(R.id.password);
        button = view.findViewById(R.id.btn_LogIn);
        textView2 = view.findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new RegisterFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container1, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
//        imageButton3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(b==1)
//                {
//                    editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    b=0;
//                }
//                else if(b==0){
//                    editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    b=1;
//                }
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editText.getText().toString();
                password = editText2.getText().toString();
                if(email.isEmpty()){
                    editText.setError("Please enter Email Id");
                    editText.requestFocus();
                }
                else if(password.isEmpty()){editText2.setError("Please enter Password");
                    editText2.requestFocus();}
                else{
                   login(email,password);
                }
            }
        });
        return view;}
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
                        userid = String.valueOf(response.getJSONObject("user").get("_id"));
                    String token = response.getString("token");
                    SharedPreferences preferences = getActivity().getSharedPreferences("OrderApp", MODE_PRIVATE);
                    preferences.edit().putString("TOKEN",token).apply();
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Rohit",error.toString());
                    Toast.makeText(getActivity(),"Please check your email and password!",Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(request);
        }
}
