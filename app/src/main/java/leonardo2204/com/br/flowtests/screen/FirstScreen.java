package leonardo2204.com.br.flowtests.screen;

import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerFirstScreenComponent;
import leonardo2204.com.br.flowtests.di.module.FirstScreenModule;

/**
 * Created by Leonardo on 04/03/2016.
 */
@org.parceler.Parcel
public class FirstScreen extends BaseScreen {

    public FirstScreen() {
    }

    @Override
    public Object createComponent(Object parent) {
        return DaggerFirstScreenComponent
                .builder()
                .activityComponent((ActivityComponent) parent)
                .firstScreenModule(new FirstScreenModule())
                .build();
    }

    @Override
    public int layoutResId() {
        return R.layout.screen_first;
    }
}
