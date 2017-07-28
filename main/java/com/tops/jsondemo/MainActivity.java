package com.tops.jsondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<HashMap<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.myList);
        arrayList = new ArrayList<>();

        AsynckLoader asynckLoader = new AsynckLoader(MainActivity.this, "http://api.androidhive.info/contacts/", new OnAsynckLoader() {
            @Override
            public void onResult(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("name", obj.getString("name"));
                        hashMap.put("gender", obj.getString("gender"));
                        JSONObject phone = obj.getJSONObject("phone");
                        hashMap.put("home", phone.getString("home"));
                        arrayList.add(hashMap);
                    }
                    String []keys = {"name", "gender", "home"};
                    int []ids = {R.id.txtName, R.id.txtGender, R.id.txtHome};
                    SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, arrayList, R.layout.my_layout, keys, ids);
                    listView.setAdapter(simpleAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        asynckLoader.execute();
    }
}
