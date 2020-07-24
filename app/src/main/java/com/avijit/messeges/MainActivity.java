package com.avijit.messeges;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static String FCM_API =  "https://fcm.googleapis.com/fcm/send";
    private static String SERVER_KEY = "key="+
            "AAAAuNv1YE4:APA91bGmFZHwmD-F3PeQmIptn6bK8IRg70aNGvjviuR6xDnWOz6VqtIgNwfYhoje444WKESnfKj4RPspOMm0BzmCXxOgwQ9vryqPKOR8-cCkqfVQ5X9xJiM_8s67tcfYcWip7ycdDDOY";
    EditText editText;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/abc");
        editText = findViewById(R.id.edit_text);
        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(v->{
            String topic = "/topics/abc";
            JSONObject notification = new JSONObject();
            JSONObject notificationBody = new JSONObject();
            try {
                notificationBody.put("title","hello");
                notificationBody.put("message",editText.getText().toString());
                notification.put("to",topic);
                notification.put("data",notificationBody);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sendNotification(notification);
        });

    }
    private void sendNotification(JSONObject notification){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API,notification,response -> {

        },error -> {

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization",SERVER_KEY);
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}