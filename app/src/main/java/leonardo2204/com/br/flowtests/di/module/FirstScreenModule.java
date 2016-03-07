package leonardo2204.com.br.flowtests.di.module;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.interactor.GetContacts;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;
import leonardo2204.com.br.flowtests.presenter.FirstScreenPresenter;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Module
public class FirstScreenModule {

    @Provides
    public GetContacts providesGetContacts(ContactsRepository contactsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetContacts(contactsRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public FirstScreenPresenter providesFirstScreenPresenter(GetContacts getContacts) {
        return new FirstScreenPresenter(getContacts);
    }

}
