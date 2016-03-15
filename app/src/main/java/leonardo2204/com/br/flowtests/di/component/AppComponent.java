package leonardo2204.com.br.flowtests.di.component;

import dagger.Component;
import leonardo2204.com.br.flowtests.FlowTestApplication;
import leonardo2204.com.br.flowtests.UIThread;
import leonardo2204.com.br.flowtests.di.module.AppModule;
import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;

/**
 * Created by Leonardo on 04/03/2016.
 */
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(FlowTestApplication flowTestApplication);

    PostExecutionThread postExecutionThread();
    UIThread uiThread();
    ThreadExecutor threadExecutor();
    ContactsRepository contactsRepository();
}
