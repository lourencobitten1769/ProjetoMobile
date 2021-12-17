package com.example.projetomobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper= new DBHelper(this);
        bottomNavigation=findViewById(R.id.bottomNav);


        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.house));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_person_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragment=null;

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


        MqttClient client;
        try {
            client = new MqttClient("tcp://localhost:1884",MqttClient.generateClientId());
            client.setCallback( new MosquittoCallBack() );
            client.connect();
            client.subscribe("INSERT");
            client.subscribe("UPDATE");
            client.subscribe("DELETE");
        } catch (MqttException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment,fragment)
                .commit();
    }
}