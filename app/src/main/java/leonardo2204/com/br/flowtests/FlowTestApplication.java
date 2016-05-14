package leonardo2204.com.br.flowtests;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class FlowTestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
