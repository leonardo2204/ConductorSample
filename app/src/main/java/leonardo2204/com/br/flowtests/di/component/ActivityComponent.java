package leonardo2204.com.br.flowtests.di.component;

import android.content.SharedPreferences;

import dagger.Component;
import leonardo2204.com.br.flowtests.RootActivity;
import leonardo2204.com.br.flowtests.UIThread;
import leonardo2204.com.br.flowtests.di.module.ActivityModule;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;
import leonardo2204.com.br.flowtests.presenter.ActionBarOwner;

/**
 * Created by Leonardo on 20/03/2016.
 */
@DaggerScope(ActivityComponent.class)
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(RootActivity rootActivity);

    PostExecutionThread postExecutionThread();

    UIThread uiThread();

    ThreadExecutor threadExecutor();

    ContactsRepository contactsRepository();

    SharedPreferences sharedPreferences();

    ActionBarOwner actionBarOwner();
}
