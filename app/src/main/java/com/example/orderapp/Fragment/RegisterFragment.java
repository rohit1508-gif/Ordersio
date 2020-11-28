package com.example.orderapp.Fragment;

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

public class RegisterFragment extends Fragment {
     EditText editText,editText2,editText3,editText1;
     Button button;
     TextView textView2;
     String name,email,password,number;
    int a;
    RequestQueue requestQueue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
      requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        editText1=view.findViewById(R.id.editText1);
        editText = view.findViewById(R.id.editText);
        editText2 = view.findViewById(R.id.editText2);
        editText3 = view.findViewById(R.id.editText3);
        button = view.findViewById(R.id.button);
        textView2 = view.findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new LoginFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container1, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
//        ImageButton imageButton2 = view.findViewById(R.id.imageButton2);
//        a=1;
//        imageButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(a==1)
//                {
//                    editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    a=0;
//                }
//                else if(a==0){
//                    editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    a=1;
//                }
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number=editText1.getText().toString();
                name = editText3.getText().toString();
                email = editText.getText().toString();
                password = editText2.getText().toString();
                if(name.isEmpty() ){  editText3.setError("Please enter Email Id");
                    editText3.requestFocus();}
                else if(email.isEmpty()){  editText.setError("Please enter Email Id");
                    editText.requestFocus();}
                else if(password.isEmpty()){  editText2.setError("Please enter Email Id");
                    editText2.requestFocus();}
                else if(password.length()<7){
                    Toast.makeText(getActivity(),"Password length must be greater than 7",Toast.LENGTH_SHORT).show();
                }
                else if(number.isEmpty()){  editText1.setError("Please enter Number");
                    editText1.requestFocus();}
                else{createuser(name,email,password,number);}
            }
        });
        return view;}
        public void createuser(String name,String email,String password,String number){
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
            JSONObject object = new JSONObject();
        try {
            object.put("name",name);
            object.put("email",email);
            object.put("password",password);
            object.put("mobile",number);
            object.put("type","Purchaser");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url =" https://ordersio.herokuapp.com/users";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getActivity(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                    try{
                        LoginFragment.userid =String.valueOf(response.getJSONObject("user").get("_id"));
                        String token = response.getString("token");
                        SharedPreferences preferences = getActivity().getSharedPreferences("OrderApp", Context.MODE_PRIVATE);
                        preferences.edit().putString("TOKEN",token).apply();
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);}
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Rohit",error.toString());
                    Toast.makeText(getActivity(),"Email is already taken",Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(request);
        }
}
