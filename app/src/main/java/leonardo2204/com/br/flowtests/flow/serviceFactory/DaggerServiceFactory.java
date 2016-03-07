package leonardo2204.com.br.flowtests.flow.serviceFactory;

import android.content.Context;

import flow.Services;
import flow.ServicesFactory;

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
        Object s = services.getKey();

    }
}
