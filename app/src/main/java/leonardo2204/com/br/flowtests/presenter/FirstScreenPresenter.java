package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import flow.Flow;
import leonardo2204.com.br.flowtests.ContactsAdapter;
import leonardo2204.com.br.flowtests.domain.interactor.DefaultSubscriber;
import leonardo2204.com.br.flowtests.domain.interactor.GetContacts;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.screen.DetailsScreen;
import leonardo2204.com.br.flowtests.view.FirstView;
import mortar.ViewPresenter;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class FirstScreenPresenter extends ViewPresenter<FirstView> {

    private final GetContacts getContacts;
    private final FirstView.ContactListener contactListener = new FirstView.ContactListener() {
        @Override
        public void onClick(Contact contact) {
            Flow.get(getView()).set(new DetailsScreen(contact));
        }
    };

    public FirstScreenPresenter(GetContacts getContacts) {
        this.getContacts = getContacts;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        this.getContacts.execute(new GetContactsSubscriber());
    }

    class GetContactsSubscriber extends DefaultSubscriber<List<Contact>> {

        @Override
        public void onNext(List<Contact> contacts) {
            if(getView() != null) {
                ContactsAdapter adapter = new ContactsAdapter(contacts);
                adapter.setContactListener(contactListener);
                getView().contacts_rv.setAdapter(adapter);
            }
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(getView().getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
