package leonardo2204.com.br.flowtests.di.module;

import com.bluelinelabs.conductor.Router;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.interactor.GetContacts;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;
import leonardo2204.com.br.flowtests.presenter.ActionBarOwner;
import leonardo2204.com.br.flowtests.presenter.FirstScreenPresenter;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Module
public class FirstScreenModule {

    private final Router router;

    public FirstScreenModule(Router router) {
        this.router = router;
    }

    @Provides
    @DaggerScope(FirstScreenComponent.class)
    public GetContacts providesGetContacts(ContactsRepository contactsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetContacts(contactsRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @DaggerScope(FirstScreenComponent.class)
    public FirstScreenPresenter providesFirstScreenPresenter(GetContacts getContacts, ActionBarOwner actionBarOwner) {
        return new FirstScreenPresenter(getContacts, actionBarOwner, router);
    }

    @Provides
    @DaggerScope(FirstScreenComponent.class)
    public Router providesRouter() {
        return router;
    }
}
