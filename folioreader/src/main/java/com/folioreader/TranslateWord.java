package com.folioreader;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TranslateWord {
    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }



  /*  public String getTrans_word() {
        getTranslate(Word,new VolleyCallback(){
            @Override
            public String onSuccess(String result){
                return Trans_word;
            }
        });
        return Trans_word;
    } */

    public void setTrans_word(String trans_word) {
        Trans_word = trans_word;
    }
    private String Word="hello";
    private String Trans_word;
    private Context mContext;

    public TranslateWord(String word, Context mcontext){
        this.Word=word;
        this.mContext=mcontext;
        Log.d("trans","girdi0");
    }

    //public void getTranslate(final String word,final VolleyCallback callback){
    public void getTranslate(){
        Log.d("trans","girdi1");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20190109T114910Z.72960f07c2ac5c38.bab26975446fda26cee90eda1419b93890e3eaf5&text=pussy&lang=en-tr&format=plain",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //    Toast.makeText(SearchActivity.this,response,Toast.LENGTH_LONG).show();
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            Log.d("trans","girdi2");
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                Log.d("trans","girdi6");
                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                JSONArray abc=product.getJSONArray("text");
                                //abc.get(0);
                                String abcd=abc.getString(0);

                               Trans_word=abcd;
                                        Log.d("trans",Trans_word);

                               Dialog dialog = new Dialog(mContext);
                                dialog.setContentView(R.layout.dictionarytr);
                                TextView edit=(TextView) dialog.findViewById(R.id.textViewTrans);
                                edit.setText(Trans_word);
                                dialog.setTitle("Türkçesi");
                                dialog.setCancelable(true);
                                dialog.show();
                            }
                           // callback.onSuccess(response);
                            Log.d("trans","girdi3");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Toast.makeText(SearchActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Toast.makeText(mContext,"Bağlantı hatası oluştu,internet bağlantısını kontrol ediniz",Toast.LENGTH_LONG).show();
                        Log.d("trans","girdi4");
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("text",Word);
                params.put("lang","en-tr");
                params.put("format","plain");

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

    }

}
