package leonardo2204.com.br.flowtests.flow.serviceFactory;

import android.content.Context;

import flow.Services;
import flow.ServicesFactory;
import leonardo2204.com.br.flowtests.mortar.ScreenScoper;
import mortar.MortarScope;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class DaggerServiceFactory extends ServicesFactory {

    private final Context context;

    public DaggerServiceFactory(Context context) {
        this.context = context;
    }

    @Override
    public void bindServices(Services.Binder services) {
        ScreenScoper scoper = new ScreenScoper();
        services.bind(services.getKey().getClass().getName(), scoper.getScreenScope(context, services.getKey().getClass().getName(), services.getKey()));
    }

    @Override
    public void tearDownServices(Services services) {
        super.tearDownServices(services);
        services.<MortarScope>getService(services.getKey().getClass().getName()).destroy();
    }
}
