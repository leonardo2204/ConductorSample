package leonardo2204.com.br.flowtests.di.component;

import dagger.Component;
import leonardo2204.com.br.flowtests.di.module.FirstScreenModule;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.screen.FirstScreen;

/**
 * Created by Leonardo on 05/03/2016.
 */
@DaggerScope(FirstScreenComponent.class)
@Component(dependencies = ActivityComponent.class, modules = FirstScreenModule.class)
public interface FirstScreenComponent extends ActivityComponent {
    void inject(FirstScreen firstScreen);
}
