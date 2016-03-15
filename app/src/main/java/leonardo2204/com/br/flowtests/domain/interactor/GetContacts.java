package leonardo2204.com.br.flowtests.domain.interactor;

import android.os.Bundle;

import javax.inject.Inject;

import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;
import rx.Observable;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class GetContacts extends DynamicUseCase {

    private final ContactsRepository contactsRepository;

    @Inject
    public GetContacts(ContactsRepository contactsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.contactsRepository = contactsRepository;
    }

    @Override
    public Observable buildUseCaseObservable(Bundle bundle) {
        return this.contactsRepository.getContactsFromPhone(bundle.getBoolean("mustHaveNumber", false));
    }
}
