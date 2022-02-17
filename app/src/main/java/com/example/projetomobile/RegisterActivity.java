package com.example.projetomobile;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName,edtEmail,edtMorada,edtNif,edtPassword,edtConfirmPassword;
    Button btnRegistar, btnOk;
    Dialog dialog;


    String username,email,morada,nif,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtName=findViewById(R.id.edtName);
        edtEmail=findViewById(R.id.edtEmail);
        edtMorada=findViewById(R.id.edtMorada);
        edtNif=findViewById(R.id.edtNif);
        edtPassword=findViewById(R.id.edtPassword);
        edtConfirmPassword=findViewById(R.id.edtConfirmPassword);
        btnRegistar=findViewById(R.id.btnRegistar);
        dialog=new Dialog(this);


        RequestQueue requestQueue=Volley.newRequestQueue(this);

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=edtName.getText().toString();
                email=edtEmail.getText().toString();
                morada=edtMorada.getText().toString();
                nif=edtNif.getText().toString();
                password=edtPassword.getText().toString();

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://10.0.2.2/projetoweb/backend/web/index.php/api/user/register", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println(response);

                        openSucessDialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Erro", error.toString());

                        openErrorDialog();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("username", username);
                        map.put("email",email);
                        map.put("morada",morada);
                        map.put("nif",nif);
                        map.put("password", password);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });
    }

    private void openSucessDialog(){

        dialog.setContentView(R.layout.registersucess_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnOk=dialog.findViewById(R.id.btnChecked_sucess);
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void openErrorDialog(){

        dialog.setContentView(R.layout.registererror_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnOk=dialog.findViewById(R.id.btnChecked_sucess);
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
}