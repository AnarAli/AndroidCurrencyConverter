package com.anarali.currencyconverter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView textView2;
    TextView textView3;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        editText = (EditText) findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    public void getRates(View view){
        DownloadData downloadData = new DownloadData();
        try {
            String url = "https://api.fixer.io/latest?base=";
            String chosenBase = editText.getText().toString();
            downloadData.execute(url+chosenBase);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                String date = jsonObject.getString("date");
                String rates = jsonObject.getString("rates");
                JSONObject jsonObject1 = new JSONObject(rates);
                String chf = jsonObject1.getString("CHF");
                String czk = jsonObject1.getString("CZK");                ;
                String tl = jsonObject1.getString("TRY");
                textView.setText("CHF:" + chf);
                textView2.setText("CZK:" + czk);
                textView3.setText("TRY:" + tl);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);

                int data = inputStreamReader.read();

                while (data > 0) {

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }
    }

}
