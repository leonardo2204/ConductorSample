package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;
import android.widget.Toast;

import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import leonardo2204.com.br.flowtests.ContactsAdapter;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.domain.interactor.DefaultSubscriber;
import leonardo2204.com.br.flowtests.domain.interactor.GetContacts;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.screen.DetailsScreen;
import leonardo2204.com.br.flowtests.view.FirstView;
import mortar.ViewPresenter;
import rx.functions.Action0;

/**
 * Created by Leonardo on 05/03/2016.
 */
@DaggerScope(FirstScreenComponent.class)
public class FirstScreenPresenter extends ViewPresenter<FirstView> {

    private final GetContacts getContacts;
    private final ActionBarOwner actionBarOwner;
    private final Router router;
    private final FirstView.ContactListener contactListener = new FirstView.ContactListener() {
        @Override
        public void onClick(Contact contact) {
            router.pushController(RouterTransaction.builder(new DetailsScreen(contact))
                    .pushChangeHandler(new FadeChangeHandler())
                    .popChangeHandler(new FadeChangeHandler())
                    .build());
        }
    };

    private boolean mustHaveNumber = true;

    @Inject
    public FirstScreenPresenter(GetContacts getContacts, ActionBarOwner actionBarOwner, Router router) {
        this.getContacts = getContacts;
        this.actionBarOwner = actionBarOwner;
        this.router = router;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        if (!hasView()) return;

        ActionBarOwner.MenuAction menuAction = new ActionBarOwner.MenuAction("Show only texts", new Action0() {
            @Override
            public void call() {
                fetchContacts(mustHaveNumber);
                mustHaveNumber = !mustHaveNumber;
            }
        }, 0);

        ActionBarOwner.Config config = new ActionBarOwner.Config(Arrays.asList(menuAction), "Contacts list", true, false);
        this.actionBarOwner.setConfig(config);

//        SwitchCompat switchCompat = (SwitchCompat) this.navigationPresenter.setupMenuItem().findItem(R.id.switch_only_numbers).getActionView();
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                fetchContacts(isChecked);
//                getView().setMustHaveNumber(isChecked);
//            }
//        });
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

        getView().setMustHaveNumber(mustHaveNumber);

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
