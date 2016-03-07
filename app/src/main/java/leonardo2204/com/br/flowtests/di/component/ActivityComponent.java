package leonardo2204.com.br.flowtests.di.component;

import dagger.Component;
import leonardo2204.com.br.flowtests.RootActivity;
import leonardo2204.com.br.flowtests.UIThread;
import leonardo2204.com.br.flowtests.di.module.ActivityModule;
import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(RootActivity rootActivity);

    ContactsRepository contactsRepository();
    PostExecutionThread postExecutionThread();
    UIThread uiThread();
    ThreadExecutor threadExecutor();
}
