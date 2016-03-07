package leonardo2204.com.br.flowtests.domain.interactor;

import javax.inject.Inject;

import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;
import leonardo2204.com.br.flowtests.model.Contact;
import rx.Observable;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class GetContacts extends UseCase {

    private final ContactsRepository contactsRepository;

    @Inject
    public GetContacts(ContactsRepository contactsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.contactsRepository = contactsRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.contactsRepository.getContactsFromPhone();
    }
}
