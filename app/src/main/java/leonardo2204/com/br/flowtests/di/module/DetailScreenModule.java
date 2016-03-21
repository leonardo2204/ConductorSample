package leonardo2204.com.br.flowtests.di.module;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.br.flowtests.di.component.DetailScreenComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.interactor.GetDetailedContact;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;
import leonardo2204.com.br.flowtests.presenter.ActionBarOwner;
import leonardo2204.com.br.flowtests.presenter.DetailsScreenPresenter;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Module
public class DetailScreenModule {

    @Provides
    @DaggerScope(DetailScreenComponent.class)
    public GetDetailedContact providesGetDetailedContact(ContactsRepository contactsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetDetailedContact(contactsRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @DaggerScope(DetailScreenComponent.class)
    public DetailsScreenPresenter providesDetailsScreenPresenter(GetDetailedContact getDetailedContact, ActionBarOwner actionBarOwner) {
        return new DetailsScreenPresenter(getDetailedContact, actionBarOwner);
    }
}
