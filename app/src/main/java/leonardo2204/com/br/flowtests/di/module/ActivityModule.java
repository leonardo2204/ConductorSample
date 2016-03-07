package leonardo2204.com.br.flowtests.di.module;

import android.app.Activity;
import android.content.ContentResolver;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.br.flowtests.data.repository.ContactsRepositoryImpl;
import leonardo2204.com.br.flowtests.di.scope.ActivityScope;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Activity providesActivity() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    public ContentResolver providesContentResolver() {
        return this.activity.getContentResolver();
    }

    @Provides
    @ActivityScope
    public ContactsRepository providesContactsRepository(ContentResolver contentResolver) {
        return new ContactsRepositoryImpl(contentResolver);
    }

}
