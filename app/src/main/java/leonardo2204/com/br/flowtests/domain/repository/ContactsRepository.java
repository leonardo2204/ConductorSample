package leonardo2204.com.br.flowtests.domain.repository;

import java.util.List;

import leonardo2204.com.br.flowtests.model.Contact;
import rx.Observable;

/**
 * Created by Leonardo on 05/03/2016.
 */
public interface ContactsRepository {
    Observable<List<Contact>> getContactsFromPhone(boolean mustHaveNumber);
    Observable<Contact> getContactById(Contact contact);
}
