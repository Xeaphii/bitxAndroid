package com.twentytwoseven.android.bitx;

import android.os.AsyncTask;
import android.util.Base64;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.twentytwoseven.android.bitx.model.*;
import com.twentytwoseven.android.bitx.util.LogUtil;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BitXClient {
    private static final String TAG = BitXClient.class.getSimpleName();
    private static final String URL_BASE = "https://api.mybitx.com/api/1";
    private BitXService mRestService;
    private String mAuth;

    public BitXClient() {
        init();
    }

    public BitXClient(String key, String secretKey) {
        try {
            mAuth = "Basic " + Base64.encodeToString((key + ":" + secretKey).getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            mAuth = "";
            //TODO: handle
            e.printStackTrace();
        }

        init();
    }

    private void init() {

        Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

        OkHttpClient httpClient = new OkHttpClient();
        OkClient okClient = new OkClient(httpClient);

        RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(URL_BASE)
            .setClient(okClient)
            .setConverter(new GsonConverter(gson))
            //FULL log level required to be able to read response body input stream :/
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();

        mRestService = restAdapter.create(BitXService.class);
    }

    /* PUBLIC API */

    public void ticker(Callback<Ticker> callback) {
        LogUtil.i(TAG, "API: Ticker");
        mRestService.ticker(callback);
    }

    public void orderBook(Callback<OrderBook> callback) {
        LogUtil.i(TAG, "API: OrderBook");
        mRestService.orderBook(callback);
    }

    public void recentTrades(Callback<TradeList> callback) {
        LogUtil.i(TAG, "API: Recent trades");
        mRestService.recentTrades(callback);
    }

    //HACK: to get csv from bitx which is not part of the REST API
    public void allTrades(final Callback<ArrayList<Trade>> callback) {
        LogUtil.i(TAG, "API: All trades");

        AsyncTask<Void, Void, ArrayList<Trade>> asyncTask = new AsyncTask<Void, Void, ArrayList<Trade>>() {
            @Override
            protected ArrayList<Trade> doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();
                try {
                    URL url = new URL("https://bitx.co.za/data/trades.csv?pair=XBTZAR");
                    HttpURLConnection connection = client.open(url);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("GET");
                    InputStream in = connection.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String[] fields = br.readLine().split(",");
                    System.out.println(fields);
                    String line;
                    ArrayList<Trade> trades = new ArrayList<>();

                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        Trade t = new Trade();
                        t.timestamp = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(values[0]).getTime();
                        t.price = Double.parseDouble(values[1]);
                        t.volume = Double.parseDouble(values[2]);
                        trades.add(t);
                    }
                    in.close();
                    return trades;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            };
        };
        ArrayList<Trade> response = null;
        try {
            response = asyncTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.success(response, null);
    }

    /* PRIVATE API */

    public void listOrders(Callback<OrderList> callback) {
        LogUtil.i(TAG, "API: List Orders");
        mRestService.listOrders(mAuth, callback);
    }

    public void postOrder(String pair, String type, String volume, String price, Callback<Order> callback) {
        LogUtil.i(TAG, "API: Posting Order");
        mRestService.postOrder(mAuth, pair, type, volume, price, callback);
    }

    public void stopOrder(String orderId, Callback<Object> callback) {
        LogUtil.i(TAG, "API: Stopping Order");
        mRestService.stopOrder(mAuth, orderId, callback);
    }

    public void balance( Callback<BalanceList> callback) {
        LogUtil.i(TAG, "API: Balance");
        mRestService.balance(mAuth, callback);
    }

    public void fundingAddress(String asset, Callback<FundingAddress> callback) {
        LogUtil.i(TAG, "API: Funding Address");
        mRestService.fundingAddress(mAuth, asset, callback);
    }

    public void createFundingAddress(String asset, Callback<FundingAddress> callback) {
        LogUtil.i(TAG, "API: Create funding Address for asset %s", asset);
        mRestService.createFundingAddress(mAuth, asset, callback);
    }

    public void transactions(String asset, Callback<TransactionList> callback) {
        LogUtil.i(TAG, "API: Transactions");
        mRestService.transactions(mAuth, asset, "0", "10", callback);
    }

    public void transactions(String asset, String offset, String limit, Callback<TransactionList> callback) {
        LogUtil.i(TAG, "API: Transactions from %s to %s", offset, (offset + limit));
        mRestService.transactions(mAuth, asset, offset, limit, callback);
    }

    public void listWithdrawals(Callback<WithdrawalList> callback) {
        LogUtil.i(TAG, "API: Withdrawals");
        mRestService.listWithdrawals(mAuth, callback);
    }

    public void requestWithdrawal(String type, String amount, Callback<Withdrawal> callback) {
        LogUtil.i(TAG, "API: Request withdrawal of type %s for amount %s", type, amount);
        mRestService.requestWithdrawal(mAuth, type, amount, callback);
    }

    public void getWithdrawal(String id, Callback<Withdrawal> callback) {
        LogUtil.i(TAG, "API: Get withdrawal status");
        mRestService.getWithdrawal(mAuth, id, callback);
    }

    public void cancelWithdrawal(String id, Callback<Withdrawal> callback) {
        LogUtil.i(TAG, "API: Cancel withdrawal with id %s", id);
        mRestService.cancelWithdrawal(mAuth, id, callback);
    }
}
