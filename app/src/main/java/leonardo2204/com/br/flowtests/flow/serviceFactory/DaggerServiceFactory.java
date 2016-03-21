package leonardo2204.com.br.flowtests.flow.serviceFactory;

import android.util.Log;

import flow.Services;
import flow.ServicesFactory;
import leonardo2204.com.br.flowtests.flow.keys.ContactsUIKey;
import leonardo2204.com.br.flowtests.flow.keys.EditContactKey;
import leonardo2204.com.br.flowtests.mortar.ScreenScoper;
import mortar.MortarScope;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class DaggerServiceFactory extends ServicesFactory {

    private final MortarScope parentScope;
    private final ScreenScoper screenScoper;

    public DaggerServiceFactory(MortarScope parentScope) {
        this.parentScope = parentScope;
        this.screenScoper = new ScreenScoper();
    }

    @Override
    public void bindServices(Services.Binder services) {
        MortarScope scope = null;

        Log.d("services", services.getKey().toString());

        if(services.getKey() instanceof ContactsUIKey) {
            scope = parentScope;
        }else if(services.getKey() instanceof EditContactKey) {
            EditContactKey key = services.getKey();
            scope = services.getService(key.getParentKey().getClass().getName());
            scope = screenScoper.getScreenScope(scope, services.getKey().getClass().getName(), services.getKey());
        }else{
            scope = screenScoper.getScreenScope(parentScope, services.getKey().getClass().getName(), services.getKey());
        }

        if(scope != null)
            services.bind(services.getKey().getClass().getName(), scope);
    }

    @Override
    public void tearDownServices(Services services) {
        super.tearDownServices(services);
        MortarScope scope = parentScope.findChild(services.getKey().getClass().getName());

        if(scope != null)
            scope.destroy();
    }
}
