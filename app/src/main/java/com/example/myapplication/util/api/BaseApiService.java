package com.example.myapplication.util.api;

import android.net.Uri;

import com.example.myapplication.model.ApplyEvent;
import com.example.myapplication.model.BaseResult;
import com.example.myapplication.model.BaseUser;
import com.example.myapplication.model.EarnedMoney.Result;
import com.example.myapplication.model.ListEvent.Example;
import com.example.myapplication.model.Notification.BadgeNumber;
import com.example.myapplication.model.Profile;
import com.example.myapplication.model.VerifyJoinEvent;


import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

//note: example class in different folder will be different
public interface BaseApiService {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(    @Field("email") String email,
                                        @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest( @Field("email") String email,
                                        @Field("password") String password,
                                        @Field("fullName") String fullName);

    @FormUrlEncoded
    @POST("login-google")
    Call<ResponseBody> login_googleRequest(     @Field("googleId") String googleId,
                                                @Field("name") String name,
                                                @Field("imageUrl") Uri imageUrl,
                                                @Field("email") String email);

    @POST("login-google ")
    Call<ResponseBody> login_google ( @Body Profile profile);
//    googleId, name, imageUrl, email

    @FormUrlEncoded
    @POST("requestForgotPassword")
    Call<ResponseBody> requestForgotPasswordRequest(@Field("email") String email);

    @FormUrlEncoded
    @POST("verifyForgotPassword")
    Call<ResponseBody> verifyForgotPasswordRequest(@Field("email") String email);
//    ???? otp sent by email

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<ResponseBody> forgotPasswordRequest(@Field("email") String email,
                                             @Field("otp") String otp,
                                             @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST("updatePassword")
    Call<ResponseBody> updatePasswordRequest(@Field("oldpassword") String oldpassword,
                                             @Field("newpassword") String newpassword);

    @FormUrlEncoded
    @POST("user/updateInfo")
    Call<ResponseBody> updateInfoRequest(   @Field("fullName") String fullName,
                                            @Field("gender") String gender,
                                            @Field("phone") String phone,
                                            @Field("discription") String discription);


//    get method

    @GET("current_user")
    Call<BaseUser> getuser_profile();
//    get history
    @GET("user/history")
    Call<BaseUser> get_History();
//    get logout
    @GET("logout")
    Call<BaseResult> getlogout();
//    get verify code/token after registering
    @GET("verifyToken")
    Call<BaseResult> getverifyToken(@Query("token") String token);
//    get list all event participate
    @GET("user/get_history_take_part_in")
    Call<Example> getHistoryTakePartIn (            @Query("categoryEventId") String categoryEventId,
                                                    @Query("startDate") Date startDate,
                                                    @Query("endDate") Date endDate,
                                                    @Query("txtSearch") String txtSearch,
                                                    @Query("pageNumber") Number pageNumber,
                                                    @Query("numberRecord") Number numberRecord,
                                                    @Query("type") String type);
    @GET("user/get_history_take_part_in")
    Call<Example> getHistoryParticipate (@Query("type") String type);
//    get list event "self"
    @GET("user/historyCreate")
    Call<Example> get_HistoryCreate();
    @GET("user/historyCreate")
    Call<Example> getHistoryCreate (   @Query("categoryEventId") String categoryEventId,
                                       @Query("startDate") Date startDate,
                                       @Query("endDate") Date endDate,
                                       @Query("txtSearch") String txtSearch,
                                       @Query("pageNumber") Number pageNumber,
                                       @Query("numberRecord") Number numberRecord,
                                       @Query("status") String status);
    @GET("get_event_inf")
    Call<com.example.myapplication.model.DetailEvent.Example> get_event_inf_web(
            @Query("eventId") String eventId);
//    get_event_info_app
    @GET("get_event_info_app")
    Call<com.example.myapplication.model.DetailEvent.Example> get_event_inf(
            @Query("eventId") String eventId);

//    get notification number
    @GET("getBadgeNumber")
    Call<BadgeNumber> getBadgeNumber();
//    get list notification
    @GET("getListNotification")
    Call<com.example.myapplication.model.Notification.Example> getListNotificationFirst();
//    load more notification
    @GET("getListNotification")
    Call<com.example.myapplication.model.Notification.Example> getListNotification(@Query("pageNumber") int pageNumber);

//    setReadNotification
    @FormUrlEncoded
    @POST("setReadNotification")
    Call<ResponseBody> setReadNotification(@Field("notificationId") String notificationId);
//    setDeleteNotification
    @FormUrlEncoded
    @POST("setDeleteNotification")
    Call<ResponseBody> setDeleteNotification (@Field("notificationId") String notificationId);
//    get list comment
    @GET("comment/get_list")
    Call<com.example.myapplication.model.Comment.Example> get_list_comment(@Query("eventId") String eventId);
//    post comment
    @FormUrlEncoded
    @POST("comment/save")
    Call<ResponseBody> saveComment(     @Field("eventId") String eventId,
                                        @Field("content") String content);
//    verify event member
    @FormUrlEncoded
    @POST("verifyEventMember")
    Call<ResponseBody> verifyEventMember(@Field("joinUserId") String joinUserId,
                                         @Field("eventId") String eventId,
                                         @Field("sessionId") String sessionId);
//    verify event member update
    @POST ("verifyEventMember")
    Call<ResponseBody> verifyEventMemberUpdate (@Body VerifyJoinEvent verifyJoinEvent);

// payment
    @FormUrlEncoded
    @POST("set_card_default")
    Call<ResponseBody> setCardDefault(@Field("cardId") String cardId );

    @FormUrlEncoded
    @POST("del_card")
    Call<ResponseBody> delCard(@Field("cardId") String cardId);

    @FormUrlEncoded
    @POST("add_card")
    Call<ResponseBody> addCard(@Field("cardToken") String cardToken);

//    get list card
    @GET("get_listcard")
    Call<com.example.myapplication.model.ListCard.Example> getListCard();
//    get payment_history not query
    @GET("payment_history")
    Call<com.example.myapplication.model.PaymentHistory.Example> getPaymentHistory();
//    get payment history, query theo pageNumber or
    @GET("payment_history")
    Call<com.example.myapplication.model.PaymentHistory.Example> getPaymentHistoryLoadMore
    (@Query("pageNumber") int pageNumber);
//    join event
    @POST("joinEvent")
    Call<ResponseBody> joinEvent(@Body ApplyEvent applyEvent);
//    cancel Event
    @FormUrlEncoded
    @POST("cancelEvent")
    Call<ResponseBody> cancelEvent(@Field("eventId")String eventId,
                                   @Field("sessionIds") String[] sessionIds);
//  reject member event
    @FormUrlEncoded
    @POST("rejectEventMenber")
    Call<ResponseBody> rejectEventMenber (@Field("joinUserId") String joinUserId,
                                          @Field("eventId") String eventId,
                                          @Field("sessionId") String sessionId);
//    getUserJoinEvent
    @GET("get_user_join_event")
    Call<com.example.myapplication.model.UserJoinEvent.Example> get_user_join_event(
            @Query("eventId") String eventId,
            @Query("sessionId") String sessionId,
            @Query("pageNumber") int pageNumber
    );
//    getPaymentInfo
    @GET("payment_info")
    Call<com.example.myapplication.model.DetailPayment.Example> get_payment_info(@Query("paymentId") String paymentId);
//    get apply user profile
    @GET("user/profile")
    Call<BaseUser> get_profile_user(@Query("id")String id);
//    get payment total
    @GET("payment_history_total")
    Call<ResponseBody> get_payment_history_total();

//    get list chat
    @GET("chat/get_list")
    Call<com.example.myapplication.model.Chat.Example> get_list_chat(@Query("sender") String sender);
//    save chat
    @FormUrlEncoded
    @POST("chat/save")
    Call<ResponseBody> saveChat (@Field("sender") String sender,
                                 @Field("receiver") String receiver,
                                 @Field("fullName") String fullName,
                                 @Field("content") String content);
//    get list earn money
    @GET("user/report_revenus")
    Call<com.example.myapplication.model.EarnedMoney.Example> getReportRevenus (
            @Query("startDate") Date startDate,
            @Query("endDate") Date endDate,
            @Query("eventId") String eventId);
    @GET("user/report_revenus")
    Call<com.example.myapplication.model.EarnedMoney.Example> getListRevenus (@Query("startDate") Date startDate,
                                                                              @Query("endDate") Date endDate);
    @GET("user/list_payment_session")
    Call<com.example.myapplication.model.ListPaymentSession.Example> getListPaymentSession
            (@Query("eventId") String eventId,
             @Query("sessionId") String sessionId,
             @Query("pageNumber") Integer pageNumber);
}
