package leonardo2204.com.br.flowtests.domain.interactor;

import android.os.Bundle;

import org.parceler.Parcels;

import javax.inject.Inject;

import leonardo2204.com.br.flowtests.domain.executor.PostExecutionThread;
import leonardo2204.com.br.flowtests.domain.executor.ThreadExecutor;
import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;
import leonardo2204.com.br.flowtests.model.Contact;
import rx.Observable;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class GetDetailedContact extends DynamicUseCase {

    private final ContactsRepository contactsRepository;

    @Inject
    public GetDetailedContact(ContactsRepository contactsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.contactsRepository = contactsRepository;
    }

    @Override
    public Observable buildUseCaseObservable(Bundle bundle) {
        return this.contactsRepository.getContactById((Contact) Parcels.unwrap(bundle.getParcelable("contact")));
    }
}
