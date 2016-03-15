package leonardo2204.com.br.flowtests;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.AppComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerAppComponent;
import leonardo2204.com.br.flowtests.di.module.AppModule;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class FlowTestApplication extends Application {

    private MortarScope mortarScope;

    @Override
    public Object getSystemService(String name) {
        if(mortarScope == null){
            setupMortar();
        }

        return (mortarScope.hasService(name)) ? mortarScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }

    private void setupMortar(){

        AppComponent component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();

        component.inject(this);

        mortarScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME,component)
                .build("Root");
    }

}
