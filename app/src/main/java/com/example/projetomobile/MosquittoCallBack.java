package com.example.projetomobile;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MosquittoCallBack implements MqttCallback {

    MqttClient client;
    ObjectMapper mapper = new ObjectMapper();
    //List<Purchases> m_arrPurchases = new ArrayList<Purchases>();
    //ObservableList<Purchases> list = FXCollections.observableArrayList();
    //public ListView<Purchases> listview;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Perda de ligação ao mosquitto");
        this.connect();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void connect(){
        try {
            client = new MqttClient("tcp://localhost:1884",MqttClient.generateClientId());
            client.setCallback( new MosquittoCallBack() );
            client.connect();
        } catch (MqttException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
