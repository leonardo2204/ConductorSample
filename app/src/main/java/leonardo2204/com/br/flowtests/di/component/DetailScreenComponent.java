package leonardo2204.com.br.flowtests.di.component;

import dagger.Component;
import leonardo2204.com.br.flowtests.di.module.DetailScreenModule;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.view.DetailsView;

/**
 * Created by Leonardo on 05/03/2016.
 */
@DaggerScope(DetailScreenComponent.class)
@Component(dependencies = ActivityComponent.class, modules = DetailScreenModule.class)
public interface DetailScreenComponent extends AppComponent {
    void inject(DetailsView detailsView);
}
