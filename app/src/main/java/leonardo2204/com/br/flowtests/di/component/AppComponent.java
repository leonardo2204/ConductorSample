package leonardo2204.com.br.flowtests.di.component;

import android.content.SharedPreferences;

import dagger.Component;
import leonardo2204.com.br.flowtests.FlowTestApplication;
import leonardo2204.com.br.flowtests.UIThread;
import leonardo2204.com.br.flowtests.di.module.AppModule;
import leonardo2204.com.br.flowtests.di.scope.ApplicationScope;
import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;

/**
 * Created by Leonardo on 04/03/2016.
 */
@ApplicationScope
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(FlowTestApplication flowTestApplication);

    PostExecutionThread postExecutionThread();
    UIThread uiThread();
    ThreadExecutor threadExecutor();
    ContactsRepository contactsRepository();

    SharedPreferences sharedPreferences();
}
