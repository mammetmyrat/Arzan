package afisha.arzan.tm.api;




import afisha.arzan.tm.response.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {
    @GET("2.5/weather?")
    Call<WeatherResponse> getWeather(@Query("q") String city,
                                     @Query("appid") String apikey
    );
}
