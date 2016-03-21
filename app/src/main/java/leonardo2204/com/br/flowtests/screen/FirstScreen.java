package leonardo2204.com.br.flowtests.screen;

import flow.ClassKey;
import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerFirstScreenComponent;
import leonardo2204.com.br.flowtests.di.module.FirstScreenModule;
import leonardo2204.com.br.flowtests.flow.serviceFactory.InjectionComponent;

/**
 * Created by Leonardo on 04/03/2016.
 */
@Layout(R.layout.screen_first)
@org.parceler.Parcel
public class FirstScreen extends ClassKey implements InjectionComponent<ActivityComponent> {

    public FirstScreen() {
    }

    @Override
    public Object createComponent(ActivityComponent parent) {
        return DaggerFirstScreenComponent
                .builder()
                .activityComponent(parent)
                .firstScreenModule(new FirstScreenModule())
                .build();
    }
}
