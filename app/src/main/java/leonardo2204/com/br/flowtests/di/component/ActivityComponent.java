package leonardo2204.com.br.flowtests.di.component;

import dagger.Component;
import leonardo2204.com.br.flowtests.RootActivity;
import leonardo2204.com.br.flowtests.di.module.ActivityModule;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.presenter.ActionBarOwner;

/**
 * Created by Leonardo on 20/03/2016.
 */
@DaggerScope(ActivityComponent.class)
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent extends AppComponent {
    void inject(RootActivity rootActivity);

    ActionBarOwner actionBarOwner();
}
