package com.twentytwoseven.android.bitx;

import com.twentytwoseven.android.bitx.model.*;
import retrofit.Callback;
import retrofit.http.*;

public interface BitXService {

    String mPair = "XBTZAR";
    String mAsset = "XBT";

    /* PUBLIC API */

    @GET("/ticker?pair="+mPair)
    void ticker(Callback<Ticker> callback);

    @GET("/orderbook?pair="+mPair)
    void orderBook(Callback<OrderBook> callback);

    @GET("/trades?pair="+mPair)
    void recentTrades(Callback<TradeList> callback);

    @GET("https://bitx.co.za/data/trades.csv?pair="+mPair)
    void allTrades(Callback<TradeList> callback);

    /* PRIVATE API */

    @GET("/listorders?pair="+mPair)
    void listOrders(
        @Header("Authorization") String auth,
        Callback<OrderList> callback
    );

    @FormUrlEncoded
    @POST("/postorder")
    void postOrder(
        @Header("Authorization") String auth,
        @Field("pair") String pair,
        @Field("type") String type,
        @Field("volume") String volume,
        @Field("price") String price,
        Callback<Order> callback
    );

    @FormUrlEncoded
    @POST("/stoporder")
    void stopOrder(
        @Header("Authorization") String auth,
        @Field("order_id") String orderId,
        Callback<Object> callback
    );

    @GET("/balance")
    void balance(
        @Header("Authorization") String auth,
        Callback<BalanceList> callback
    );

    @GET("/funding_address")
    void fundingAddress(
        @Header("Authorization") String auth,
        @Query("asset") String asset,
        Callback<FundingAddress> callback
    );

    @FormUrlEncoded
    @POST("/funding_address")
    void createFundingAddress(
        @Header("Authorization") String auth,
        @Field("asset") String asset,
        Callback<FundingAddress> callback
    );

    @GET("/transactions")
    void transactions(
        @Header("Authorization") String auth,
        @Query("asset") String asset,
        @Query("offset") String offset,
        @Query("limit") String limit,
        Callback<TransactionList> callback
    );

    @GET("/withdrawals")
    void listWithdrawals(
        @Header("Authorization") String auth,
        Callback<WithdrawalList> callback
    );

    @FormUrlEncoded
    @POST("/withdrawals")
    void requestWithdrawal(
        @Header("Authorization") String auth,
        @Field("type") String type,
        @Field("amount") String amount,
        Callback<Withdrawal> callback
    );

    @GET("/withdrawals/{id}")
    void getWithdrawal(
        @Header("Authorization") String auth,
        @Path("id") String id,
        Callback<Withdrawal> callback
    );

    @DELETE("/withdrawals/{id}")
    void cancelWithdrawal(
        @Header("Authorization") String auth,
        @Path("id") String id,
        Callback<Withdrawal> callback
    );

}
