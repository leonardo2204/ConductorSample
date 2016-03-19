package leonardo2204.com.br.flowtests.di.component;

import dagger.Component;
import leonardo2204.com.br.flowtests.di.DaggerScope;
import leonardo2204.com.br.flowtests.di.module.EditDialogModule;

/**
 * Created by Leonardo on 08/03/2016.
 */
@DaggerScope(EditDialogComponent.class)
@Component(dependencies = DetailScreenComponent.class, modules = EditDialogModule.class)
public interface EditDialogComponent {
}
