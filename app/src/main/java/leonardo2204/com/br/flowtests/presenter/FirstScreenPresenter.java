package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import flow.Flow;
import leonardo2204.com.br.flowtests.ContactsAdapter;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.DaggerScope;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.domain.interactor.DefaultSubscriber;
import leonardo2204.com.br.flowtests.domain.interactor.GetContacts;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.screen.DetailsScreen;
import leonardo2204.com.br.flowtests.view.FirstView;
import mortar.ViewPresenter;

/**
 * Created by Leonardo on 05/03/2016.
 */
@DaggerScope(FirstScreenComponent.class)
public class FirstScreenPresenter extends ViewPresenter<FirstView> {

    private final ToolbarPresenter toolbarPresenter;
    private final NavigationPresenter navigationPresenter;
    private final GetContacts getContacts;
    private final FirstView.ContactListener contactListener = new FirstView.ContactListener() {
        @Override
        public void onClick(Contact contact) {
            Flow.get(getView()).set(new DetailsScreen(contact));
        }
    };

    @Inject
    public FirstScreenPresenter(GetContacts getContacts, ToolbarPresenter toolbarPresenter, NavigationPresenter navigationPresenter) {
        this.getContacts = getContacts;
        this.toolbarPresenter = toolbarPresenter;
        this.navigationPresenter = navigationPresenter;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        this.toolbarPresenter.setTitle("Contacts list");
        this.toolbarPresenter.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        this.toolbarPresenter.setNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getView() != null)
                    getView().openDrawer(getView().navigationView);
            }
        });
        SwitchCompat switchCompat = (SwitchCompat) this.navigationPresenter.setupMenuItem().findItem(R.id.switch_only_numbers).getActionView();
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fetchContacts(isChecked);
                getView().setMustHaveNumber(isChecked);
            }
        });
    }

    @Override
    protected void onExitScope() {
        super.onExitScope();
        this.getContacts.unsubscribe();
    }

    public void fetchContacts(boolean mustHaveNumber) {
        if(getView() == null)
            return;

        if (getView().contacts_rv.getAdapter() != null)
            ((ContactsAdapter) getView().contacts_rv.getAdapter()).clearAdapter();

        Bundle bundle = new Bundle(1);

        bundle.putBoolean("mustHaveNumber", mustHaveNumber);
        this.getContacts.execute(new GetContactsSubscriber(), bundle);
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
            if(getView() != null)
                Toast.makeText(getView().getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}
