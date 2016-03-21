package leonardo2204.com.br.flowtests.mortar;

import leonardo2204.com.br.flowtests.RootActivity;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.flow.serviceFactory.InjectionComponent;
import mortar.MortarScope;

/**
 * Created by Leonardo on 08/03/2016.
 */
public class ScreenScoper {

    public MortarScope getScreenScope(MortarScope parentScope, String name, Object key) {

        parentScope = parentScope.findChild(RootActivity.class.getName());
        MortarScope childScope = parentScope.findChild(name);

        if (childScope != null)
            return childScope;

        if (!(key instanceof InjectionComponent)) {
            //throw new IllegalStateException(String.format(Locale.getDefault(),"The screen (%s) must implement InjectionComponent",key.getClass().getSimpleName()));
            return null;
        }

        InjectionComponent screenComponent = (InjectionComponent) key;
        Object component = screenComponent.createComponent(parentScope.getService(DaggerService.SERVICE_NAME));

        return parentScope.buildChild().withService(DaggerService.SERVICE_NAME, component).build(name);
    }

}
