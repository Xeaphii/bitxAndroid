package workflow.xeaphii.com.temp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.twentytwoseven.android.bitx.BitXClient;
import com.twentytwoseven.android.bitx.model.BalanceList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.twentytwoseven.android.bitx.model.Asset;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    BitXClient client = new BitXClient("cw83q7swfwabs", "3akFVcrZ974dBxr3-VaFKIbCGdJ6o2Hw4lDkv2qijA8");
    TextView Balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Balance = (TextView) findViewById(R.id.balance);
        //example call to get ticker information
        client.balance(

                new Callback<BalanceList>() {
                    @Override
                    public void success(BalanceList balanceList, Response response) {
                        Balance.setText(responseBodyInputStreamToString(response));
                        //mResultTextView.setText(prettyJson);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //handleFailure(error);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected String responseBodyInputStreamToString(Response response) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(response.getBody().in()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String prettyJson = sb.toString();
        try {
            prettyJson = new JSONObject(sb.toString()).toString(2);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return prettyJson;
    }

}
