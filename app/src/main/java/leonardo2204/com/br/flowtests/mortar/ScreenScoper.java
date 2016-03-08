package leonardo2204.com.br.flowtests.mortar;

import android.content.Context;

import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.flow.serviceFactory.InjectionComponent;
import mortar.MortarScope;

/**
 * Created by Leonardo on 08/03/2016.
 */
public class ScreenScoper {

    public MortarScope getScreenScope(Context context, String name, Object key) {
        MortarScope parentScope = MortarScope.getScope(context);

        MortarScope childScope = parentScope.findChild(name);

        if (childScope != null)
            return childScope;

        if (!(key instanceof InjectionComponent)) {
            throw new IllegalStateException("The screen (key) must implement InjectionComponent");
        }

        InjectionComponent screenComponent = (InjectionComponent) key;
        Object component = screenComponent.createComponent(parentScope.getService(DaggerService.SERVICE_NAME));

        return parentScope.buildChild().withService(DaggerService.SERVICE_NAME, component).build(name);
    }

}
