package leonardo2204.com.br.flowtests.di.component;

import dagger.Component;
import leonardo2204.com.br.flowtests.di.DaggerScope;
import leonardo2204.com.br.flowtests.di.module.DetailScreenModule;
import leonardo2204.com.br.flowtests.view.DetailsView;

/**
 * Created by Leonardo on 05/03/2016.
 */
@DaggerScope(DetailScreenComponent.class)
@Component(dependencies = AppComponent.class, modules = DetailScreenModule.class)
public interface DetailScreenComponent {
    void inject(DetailsView detailsView);
}
