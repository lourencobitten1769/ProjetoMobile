package com.example.projetomobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

/*import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
*/

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation=findViewById(R.id.bottomNav);



        Bundle bundle=getIntent().getExtras();

        String username=bundle.getString("username");
        String password= bundle.getString("password");

        DBHelper dbHelper=new DBHelper(this);

        User userLogado= dbHelper.userlogado(username);

        //Log.d("user",userLogado.getUsername());




        Bundle bundleSender=new Bundle();
        bundleSender.putSerializable("user",userLogado);
        bundleSender.putString("username",username);

        sharedPreferences=getPreferences(Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt(getString(R.string.userid),userLogado.getId());
        editor.putString(String.valueOf(R.string.username),userLogado.getUsername());
        editor.commit();



        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.house));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_person_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragment= null;

                switch (item.getId()){
                    case 1:
                        fragment=new CarrinhoFragment();
                        break;

                    case 2:
                        fragment= new InicialFragment();
                        break;

                    case 3:
                        fragment=new PerfilFragment();

                        break;

                }
                fragment.setArguments(bundleSender);
                loadFragment(fragment);
            }
        });

        bottomNavigation.show(2,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });


        /*MqttClient client;
        try {
            client = new MqttClient("tcp://localhost:1884",MqttClient.generateClientId());
            client.setCallback( new MosquittoCallBack() );
            client.connect();
            client.subscribe("INSERT");
            client.subscribe("UPDATE");
            client.subscribe("DELETE");
        } catch (MqttException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment,fragment)
                .commit();
    }
}