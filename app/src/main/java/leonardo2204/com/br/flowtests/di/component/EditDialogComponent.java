package leonardo2204.com.br.flowtests.di.component;

import dagger.Component;
import leonardo2204.com.br.flowtests.di.module.EditDialogModule;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.view.EditDialogView;

/**
 * Created by Leonardo on 08/03/2016.
 */
@DaggerScope(EditDialogComponent.class)
@Component(dependencies = DetailScreenComponent.class, modules = EditDialogModule.class)
public interface EditDialogComponent {
    void inject(EditDialogView editDialogView);
}
