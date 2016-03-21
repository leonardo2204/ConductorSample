package leonardo2204.com.br.flowtests.di.module;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.presenter.ActionBarOwner;

/**
 * Created by Leonardo on 20/03/2016.
 */
@Module
public class ActivityModule {

    @Provides
    @DaggerScope(ActivityComponent.class)
    public ActionBarOwner providesActionBarOwner() {
        return new ActionBarOwner();
    }
}
