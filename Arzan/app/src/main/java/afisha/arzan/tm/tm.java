package afisha.arzan.tm;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

import static afisha.arzan.tm.app.SPManager.METRICA_API_KEY;

public class tm extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(METRICA_API_KEY).build();

        YandexMetrica.activate(getApplicationContext(), config);
        YandexMetrica.enableActivityAutoTracking(this);

    }
}
