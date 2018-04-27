package com.example.zixia.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String global_word = "";
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        Button flipButton = findViewById(R.id.flip);
        flipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("adf", "asdf");
                startFlip();
            }
        });
//        Button previousButton = findViewById(R.id.previous);
//        previousButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startPrevious();
//            }
//        });
        Button nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startNext();
            }
        });
    }

//    private void startPrevious() {
//
//    }
    private void startNext() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "http://api.wordnik.com:80/v4/words.json/randomWord?hasDictionaryDef=true&minCorpusCount=0&minLength=0&maxLength=-1&api_key=fae23ee2376bb7ae2643994bc990660bbf278167b23729e56",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            String jsonString = response.toString();
                            Log.d("word", jsonString);
                            JsonParser parser = new JsonParser();
                            JsonObject result = parser.parse(jsonString).getAsJsonObject();
                            String word = result.getAsJsonPrimitive("word").getAsString();
                            Log.d("", word);
                            TextView wordView = findViewById(R.id.word);
                            wordView.setText(word);
                            global_word = word;
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(final VolleyError error) {
                            Log.e("", error.toString());
                        }
                });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("", e.toString());
        }
    }
    private void startFlip() {
//        String apiCall = "http://api.wordnik.com:80/v4/word.json/" + global_word + "/definitions?limit=200&includeRelated=true&useCanonical=false&includeTags=false&api_key=fae23ee2376bb7ae2643994bc990660bbf278167b23729e56";
//        Log.d("api Call", apiCall);
        try {
            Log.d("WOOOORD", global_word);
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    "http://api.wordnik.com:80/v4/word.json/" + global_word + "/definitions?limit=200&includeRelated=true&useCanonical=false&includeTags=false&api_key=fae23ee2376bb7ae2643994bc990660bbf278167b23729e56",
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(final JSONArray response) {
                            String jsonString = response.toString();
                            Log.d("", jsonString);
                            JsonParser parser = new JsonParser();
                            JsonArray result = parser.parse(jsonString).getAsJsonArray();
                            String definition = result.get(0).getAsJsonObject().getAsJsonPrimitive("text").getAsString();
                            //String definition = firstLayer.get("text").getAsString();
                            //String definition = element.getAsJsonObject().get("text").getAsString();
                            Log.d("definition", definition);
                            TextView definitionView = findViewById(R.id.definition);
                            definitionView.setText(definition);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(final VolleyError error) {
                            Log.e("", error.toString());
                        }
                });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("", e.toString());
        }
    }



}
