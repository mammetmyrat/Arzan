package afisha.arzan.tm.api;


import java.util.List;
import java.util.Map;

import afisha.arzan.tm.model.GlobalVar;
import afisha.arzan.tm.model.UpdateProfile;
import afisha.arzan.tm.request.LoginRequest;
import afisha.arzan.tm.request.PayResponse;
import afisha.arzan.tm.request.RecoverRequest;
import afisha.arzan.tm.request.RegisterRequest;
import afisha.arzan.tm.request.UserActionRequest;
import afisha.arzan.tm.request.UserCheckerRequest;
import afisha.arzan.tm.response.Count;
import afisha.arzan.tm.response.FollowingResponse;
import afisha.arzan.tm.response.Like;
import afisha.arzan.tm.response.LoginResponse;
import afisha.arzan.tm.response.MainBanner;
import afisha.arzan.tm.response.MainFollowing;
import afisha.arzan.tm.response.MainNotification;
import afisha.arzan.tm.response.MainOfficial;
import afisha.arzan.tm.response.MainOnePost;
import afisha.arzan.tm.response.MainPost;
import afisha.arzan.tm.response.MainRegion;
import afisha.arzan.tm.response.Profile;
import afisha.arzan.tm.response.RegisterResponse;
import afisha.arzan.tm.response.StandardResponse;
import afisha.arzan.tm.response.Statistic;
import afisha.arzan.tm.response.UserCheckerResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/api/v1/posts")
    Call<MainPost> getAllPosts(@Header("Authorization") String token,
                               @Query("regionId") int regionId,
                               @Query("categoryId") int categoryId,
                               @Query("limit") int limit ,
                               @Query("offset") int offset

    );
    @GET("/api/v1/posts")
    Call<MainPost> getRecommended(@Header("Authorization") String token,
                               @Query("regionId") int regionId,
                               @Query("categoryId") int categoryId,
                               @Query("limit") int limit ,
                               @Query("offset") int offset

    );
    @GET("/api/v1/main/mobile")
    Call<MainRegion>getRegion();
    @GET("/api/v1/banners")
    Call<MainBanner> getBanners(@Query("search") String page,
                                @Query("regionId") int regionId
    );

    @GET("/api/v1/posts")
    Call<MainPost> getSearchPost(@Query("search") String text,
                                 @Query("limit") int limit,
                                 @Query("offset") int offset
                                 );
    //get following posts
    @GET("/api/v1/posts")
    Call<MainPost> getFavorites(@Header("Authorization") String token,
                                @Query("categoryId") int categoryId,
                                @Query("filter") String filter,
                                @Query("limit") int limit,
                                @Query("offset") int offset

    );

    //post view count
    @POST("/api/v1/user-actions")
    Call<StandardResponse> postCount(@Header("Authorization") String token,
                                     @Body UserActionRequest action
    );
    @POST("/api/v1/feedbacks")
    @Multipart
    Call<StandardResponse> postThanks(@Header("Authorization") String token,
                                      @Part("content") String content,
                                      @Part("type") String type);

    //user checker
    @POST("api/v1/user-checker/")
    Call<UserCheckerResponse> usr_checker(@Body UserCheckerRequest userCheckerRequest);

    @POST("api/v1/register/")
    Call<LoginResponse> registerUser(@Body RegisterRequest registerRequest);


    @POST("api/v1/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
    @POST("/api/v1/recover-password")
    Call<LoginResponse> recoverPassword(@Body RecoverRequest recoverRequest);

    @GET("/api/v1/get-user-data-by-token")
    Call<LoginResponse> getNewToken(@Header("Authorization")String token,
                                    @Query("fireToken") String fireToken
    );
    @GET("/api/v1/main/counts")
    Call<Count> getCounts(@Header("Authorization")String token

    );



    @Multipart
    @POST("/api/v1/posts")
    Call<StandardResponse>postPosts(@Header("Authorization") String token,
                                    @Part List<MultipartBody.Part> images,
                                    @PartMap Map<String , RequestBody> requestBody,
                                    @Part("regions") List<RequestBody> regions,
                                    @Part("categories") List<RequestBody> categories
    );

    @GET("/api/v1/users/{id}/")
    Call<Profile> getProfile(@Header("Authorization") String token,
            @Path("id") int id);
    @GET("/api/v1/user-actions/by/like")
    Call<Like> getLikeToME(@Header("Authorization") String token
    );
    @GET("/api/v1/posts")
    Call<MainPost> getUserPosts(@Header("Authorization") String token,
                                @Query("userId") int userId
    );
    @GET("/api/v1/users/{userId}/posts")
    Call<MainPost> getMyPosts(@Header("Authorization") String token,
                              @Path("userId") int userId,
                              @Query("filter") String filter
    );
    //change user image
    @PUT("/api/v1/users/{id}/")
    @Multipart
    Call<StandardResponse> changeImageProfile(@Header("Authorization") String token,
                                              @Path("id") int userId,
                                              @Part MultipartBody.Part part
    );
    @PUT("/api/v1/users/{id}/")
    Call<Profile> updateProfile(@Header("Authorization") String token,
                                @Path("id") int userId,
                                @Body UpdateProfile raw
    );
    @GET("/api/v1/main/region-stat")
    Call<Statistic> getStatistic();

    @GET("/api/v1/posts/{id}")
    Call<MainOnePost> getOnePost(@Path("id") int userId
    );
    @DELETE("/api/v1/posts/{id}")
    Call<StandardResponse> deletePost(@Header("Authorization") String token,
                                      @Path("id") int userId
    );

    @GET("/api/v1/users")
    Call<MainOfficial> getOfficials(@Header("Authorization") String token,
                                    @Query("filter") String status,
                                    @Query("limit") int limit,
                                    @Query("offset") int offset
    );
    //main mobile
    @GET("/api/v1/main/mobile")
    Call<FollowingResponse>getMainMobile(@Header("Authorization") String token,
                                         @Query("regionId") int regionId
    );

    // set follow
    @GET("/api/v1/users/{id}/follow")
    Call<StandardResponse> Follow(@Header("Authorization") String token,
                                @Path("id") int userId
    );
    //set un follow
    @GET("/api/v1/users/{id}/unfollow")
    Call<StandardResponse> UnFollow(@Header("Authorization") String token,
                                  @Path("id") int userId
    );
    @GET("/api/v1/payments/{id}")
    Call<PayResponse> pay(@Path("id") String id
    );
    @GET("/api/v1/global-vars/paymentService")
    Call<GlobalVar> getServices(
    );

    @GET("/api/v1/notifications")
    Call<MainNotification> getNotification(@Header("Authorization") String token,
                                           @Query("filter") String filter,
                                           @Query("limit") int limit,
                                           @Query("offset") int offset);

}

