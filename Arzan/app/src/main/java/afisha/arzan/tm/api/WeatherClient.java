package afisha.arzan.tm.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {

    private static WeatherClient weatherClient;
    private static Retrofit retrofit;
    public  static final String WEATHER_URL="https://api.openweathermap.org/data/";
    private WeatherClient(){
        retrofit = new Retrofit.Builder().baseUrl(WEATHER_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static synchronized WeatherClient getInstance(){
        if (weatherClient ==null){
            weatherClient = new WeatherClient();
        }
        return weatherClient;
    }
    public WeatherInterface getApi(){
        return retrofit.create(WeatherInterface.class);
    }
}
