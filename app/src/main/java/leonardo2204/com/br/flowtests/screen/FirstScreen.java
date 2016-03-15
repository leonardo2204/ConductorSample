package leonardo2204.com.br.flowtests.screen;

import android.support.annotation.NonNull;

import flow.ClassKey;
import flow.TreeKey;
import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.AppComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerFirstScreenComponent;
import leonardo2204.com.br.flowtests.di.module.FirstScreenModule;
import leonardo2204.com.br.flowtests.flow.keys.ContactsUIKey;
import leonardo2204.com.br.flowtests.flow.serviceFactory.InjectionComponent;

/**
 * Created by Leonardo on 04/03/2016.
 */
@Layout(R.layout.screen_first)
@org.parceler.Parcel
public class FirstScreen extends ClassKey implements InjectionComponent<AppComponent> {

    public FirstScreen() {
    }

    @Override
    public Object createComponent(AppComponent parent) {
        return DaggerFirstScreenComponent
                .builder()
                .appComponent(parent)
                .firstScreenModule(new FirstScreenModule())
                .build();
    }
}
