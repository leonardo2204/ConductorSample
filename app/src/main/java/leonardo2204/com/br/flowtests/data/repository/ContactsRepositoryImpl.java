package leonardo2204.com.br.flowtests.data.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import leonardo2204.com.br.flowtests.domain.repository.ContactsRepository;
import leonardo2204.com.br.flowtests.model.Contact;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class ContactsRepositoryImpl implements ContactsRepository {

    private final ContentResolver contentResolver;

    public ContactsRepositoryImpl(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public Observable<List<Contact>> getContactsFromPhone() {
        return Observable.create(new Observable.OnSubscribe<List<Contact>>() {
            @Override
            public void call(Subscriber<? super List<Contact>> subscriber) {
                Cursor queryContacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

                if(queryContacts.getCount() > 0) {
                    List<Contact> contacts = new ArrayList<>(queryContacts.getColumnCount());

                    while (queryContacts.moveToNext()) {
                        Contact contact = new Contact();

                        String id = queryContacts.getString(
                                queryContacts.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = queryContacts.getString(
                                queryContacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        contact.setId(id);
                        contact.setName(name);
                        contacts.add(contact);
                    }
                    subscriber.onNext(contacts);
                    subscriber.onCompleted();
                }else{
                    subscriber.onError(new Exception("No contacts were found"));
                    subscriber.onCompleted();
                }
                queryContacts.close();
            }
        });
    }

    @Override
    public Observable<Contact> getContactById(final Contact contact) {
        return Observable.create(new Observable.OnSubscribe<Contact>() {
            @Override
            public void call(Subscriber<? super Contact> subscriber) {
                Cursor queryContacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[] {ContactsContract.Contacts._ID,
                                ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                        new String[]{contact.getId()},
                        null);

                if(queryContacts.getCount() > 0) {
                    while (queryContacts.moveToNext()) {
                        Cursor pCur = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{contact.getId()}, null);

                        List<String> phones = new ArrayList<>();
                        contact.setTelephone(phones);

                        while (pCur.moveToNext()) {
                            contact.getTelephone().add(pCur.getString((pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
                        }
                        pCur.close();
                    }
                    subscriber.onNext(contact);
                    subscriber.onCompleted();
                }else{
                    subscriber.onError(new Exception("No contacts were found"));
                    subscriber.onCompleted();
                }

                queryContacts.close();
            }
        });
    }

}
