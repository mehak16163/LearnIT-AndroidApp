package com.example.mehak.moreless;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class search extends AppCompatActivity {
    private Button data;
    private TextView s1, s2, s3,p1,p2,p3;
    private EditText name;
    private String api ="DLD4upgJjaZVmPHrzyfVv0PaN2neytEgAxJ";
    private boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        data = (Button)findViewById(R.id.data);
        s1 = (TextView)findViewById(R.id.s1);
        s2 = (TextView)findViewById(R.id.s2);
        s3 = (TextView)findViewById(R.id.s3);
        p1 = (TextView)findViewById(R.id.p1);
        p2 = (TextView)findViewById(R.id.p2);
        p3 = (TextView)findViewById(R.id.p3);
        name = (EditText)findViewById(R.id.name);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product = name.getText().toString();
                String url = "https://price-api.datayuge.com/api/v1/compare/search?api_key="+api+"&product=\""+product+"\"";
                new JsonTask().execute(url);
            }
        });

    }
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("", "" + line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (flag){
                int index = result.indexOf("product_id")+13;
                result = result.substring(index);
                String id = "";
                int w=0;
                while(result.charAt(w)!='"'){
                    id = id + result.charAt(w);
                    w++;
                }
                //data.setText(id);
                String exec = "https://price-api.datayuge.com/api/v1/compare/price?api_key="+api+"&id="+id;
                flag=false;
                new JsonTask().execute(exec);

            }
            else {
                result = result.substring(1,result.length()-2);
                String[] results = result.split(",");
                String[][] mat = new String[(results.length)][2];
                for(int i=0;i<results.length;i++){
                        String company = results[i].split(":")[0];
                    String rate = results[i].split(":")[1];

                    mat[i][0] = company.substring(1,company.length()-1);
                        mat[i][1] = rate.substring(1, rate.length() -1);
                        if (mat[i][1].equals("")) {
                            mat[i][0] = "";
                        }
                    }

                int[] m = new int[3];
                m[0]=0;
                m[1]=1;
                m[2]=2;
                int index=0;

                float max =1000000;
                float maximum = -500;
                for (int u=0;u<3;u++) {
                    for (int j = 0; j < mat.length; j++) {
                        if ( !mat[j][1].equals("")) {
                            int val = Integer.parseInt(mat[j][1]);
                            if (val < max && val > maximum) {
                                max = val;
                                index = j;
                            }
                        }
                    }
                    m[u]=index;
                    maximum = max;
                    max=1000000;
                }
                s1.setText(results[m[0]].split(":")[0].substring(1,results[m[0]].split(":")[0].length()-1));
                s2.setText(results[m[1]].split(":")[0].substring(1,results[m[1]].split(":")[0].length()-1));
                s3.setText(results[m[2]].split(":")[0].substring(1,results[m[2]].split(":")[0].length()-1));
                p1.setText(results[m[0]].split(":")[1].substring(1,results[m[0]].split(":")[1].length()-1));
                p2.setText(results[m[1]].split(":")[1].substring(1,results[m[1]].split(":")[1].length()-1));
                p3.setText(results[m[2]].split(":")[1].substring(1,results[m[2]].split(":")[1].length()-1));
                flag = true;
            }
        }
    }
}