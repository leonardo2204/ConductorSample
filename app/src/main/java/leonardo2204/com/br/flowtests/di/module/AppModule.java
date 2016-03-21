package leonardo2204.com.br.flowtests.di.module;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.br.flowtests.UIThread;
import leonardo2204.com.br.flowtests.data.executor.JobExecutor;
import leonardo2204.com.br.flowtests.data.repository.ContactsRepositoryImpl;
import leonardo2204.com.br.flowtests.di.scope.ApplicationScope;
import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;

/**
 * Created by Leonardo on 04/03/2016.
 */
@Module
public class AppModule {

    private final static String SHARED_NAME = "global_config";
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    public Application providesApplication() {
        return application;
    }

    @Provides
    @ApplicationScope
    public UIThread provideUIThread() {
        return new UIThread();
    }

    @Provides
    @ApplicationScope
    public ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @ApplicationScope
    public PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @ApplicationScope
    public ContentResolver providesContentResolver() {
        return this.application.getContentResolver();
    }

    @Provides
    @ApplicationScope
    public ContactsRepository providesContactsRepository(ContentResolver contentResolver) {
        return new ContactsRepositoryImpl(contentResolver);
    }

    @Provides
    @ApplicationScope
    public SharedPreferences providesSharedPreferences() {
        return this.application.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }
}
