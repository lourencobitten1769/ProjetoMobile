package com.example.projetomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projetomobile.databinding.ActivityLoginBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    String username;
    String password;
    String baseUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //baseUrl = "http://10.0.2.2/projetoweb/backend/web/index.php/api/user/login";
        DBHelper dbHelper = new DBHelper(this);
        //SingletonUser singletonUser=SingletonUser.getInstance(this);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://10.0.2.2/projetoweb/backend/web/index.php/api/user";

        /*JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i=0;i<response.length();i++){
                                JSONObject jsonObject= response.getJSONObject(i);

                                User user= new User();
                                user.setId(jsonObject.getInt("id"));
                                user.setUsername(jsonObject.getString("username"));
                                user.setAuth_key(jsonObject.getString("auth_key"));
                                user.setPassword_hash(jsonObject.getString("password_hash"));
                                user.setPassword_reset_token(jsonObject.getString("password_reset_token"));
                                user.setEmail(jsonObject.getString("email"));
                                user.setMorada(jsonObject.getString("morada"));
                                user.setNif(jsonObject.getInt("nif"));
                                user.setPontos(jsonObject.getInt("pontos"));
                                user.setSocio(false);
                                user.setStatus(jsonObject.getInt("status"));
                                user.setCreated_at(jsonObject.getInt("created_at"));
                                user.setUpdated_At(jsonObject.getInt("updated_at"));
                                user.setVerification_token(jsonObject.getString("verification_token"));
                                //binding.edtEmail.setText(binding.edtEmail.getText()+user.getUsername());

                                //singletonUser.adicionarUser(user);
                                dbHelper.adicionarUser(user);


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(jsonArrayRequest);

*/

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username= binding.edtEmail.getText().toString().trim();
                password = binding.edtPassword.getText().toString().trim();


                if (username.equals("") || password.equals("")) {
                    //Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_LONG).show();
                    Log.d("result", "Please enter all fields");
                } else {

                   /* ApiAuthenticationClient apiAuthenticationClient =
                            new ApiAuthenticationClient(baseUrl, username, password);

                    AsyncTask<Void, Void, String> execute = new ExecuteNetworkOperation(apiAuthenticationClient);
                    execute.execute();
*/
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2/projetoweb/backend/web/index.php/api/user/login", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals("null")) {
                                System.out.println((String) null);
                            } else {

                                try {
                                    Bundle bundle=new Bundle();
                                    bundle.putString("username",username);
                                    bundle.putString("password",password);
                                    //bundle.putString("baseUrl",baseUrl);

                                    Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                } catch (Exception ex) {
                                }



                                System.out.println(response);
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Erro", error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("username", username);
                            map.put("password", password);
                            return map;
                        }
                    };

                    queue.add(stringRequest);

                }

            }
        });
    }

    /*class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String isValidCredentials;

        public ExecuteNetworkOperation(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient=apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("message",isValidCredentials);

            if (isValidCredentials.equals("true")) {
                goToSecondActivity();
            }
            else{
                Log.d("message","Login Failed");
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                isValidCredentials = apiAuthenticationClient.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    private void goToSecondActivity() {
            Bundle bundle=new Bundle();
            bundle.putString("username",username);
            bundle.putString("password",password);
            bundle.putString("baseUrl",baseUrl);

            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
    }
}*/
}
    

