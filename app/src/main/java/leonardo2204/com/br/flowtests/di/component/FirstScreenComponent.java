package leonardo2204.com.br.flowtests.di.component;

import dagger.Component;
import leonardo2204.com.br.flowtests.di.module.FirstScreenModule;
import leonardo2204.com.br.flowtests.view.FirstView;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Component(dependencies = ActivityComponent.class, modules = FirstScreenModule.class)
public interface FirstScreenComponent {
    void inject(FirstView firstView);
}
