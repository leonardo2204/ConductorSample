package leonardo2204.com.br.flowtests.screen;

import android.support.annotation.LayoutRes;

import flow.ClassKey;
import leonardo2204.com.br.flowtests.flow.serviceFactory.InjectionComponent;

/**
 * Created by Leonardo on 08/03/2016.
 */
public abstract class BaseScreen extends ClassKey implements InjectionComponent {

    public abstract
    @LayoutRes
    int layoutResId();
}
