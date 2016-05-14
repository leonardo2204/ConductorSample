package leonardo2204.com.br.flowtests.di.module;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.br.flowtests.UIThread;
import leonardo2204.com.br.flowtests.data.executor.JobExecutor;
import leonardo2204.com.br.flowtests.data.repository.ContactsRepositoryImpl;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;
import leonardo2204.com.br.flowtests.presenter.ActionBarOwner;

/**
 * Created by Leonardo on 20/03/2016.
 */
@Module
public class ActivityModule {

    private final static String SHARED_NAME = "global_config";
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @DaggerScope(ActivityComponent.class)
    public ActionBarOwner providesActionBarOwner() {
        return new ActionBarOwner();
    }

    @Provides
    @DaggerScope(ActivityComponent.class)
    public Activity providesApplication() {
        return activity;
    }

    @Provides
    @DaggerScope(ActivityComponent.class)
    public UIThread provideUIThread() {
        return new UIThread();
    }

    @Provides
    @DaggerScope(ActivityComponent.class)
    public ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @DaggerScope(ActivityComponent.class)
    public PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @DaggerScope(ActivityComponent.class)
    public ContentResolver providesContentResolver() {
        return this.activity.getContentResolver();
    }

    @Provides
    @DaggerScope(ActivityComponent.class)
    public ContactsRepository providesContactsRepository(ContentResolver contentResolver) {
        return new ContactsRepositoryImpl(contentResolver);
    }

    @Provides
    @DaggerScope(ActivityComponent.class)
    public SharedPreferences providesSharedPreferences() {
        return this.activity.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }
}
