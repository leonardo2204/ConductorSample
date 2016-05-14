package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import leonardo2204.com.br.flowtests.base.MvpLceRxPresenter;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.domain.interactor.GetContacts;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.view.FirstView;
import rx.functions.Action0;

/**
 * Created by Leonardo on 05/03/2016.
 */
@DaggerScope(FirstScreenComponent.class)
public class FirstScreenPresenter extends MvpLceRxPresenter<FirstView, List<Contact>> {

    private final GetContacts getContacts;
    private final ActionBarOwner actionBarOwner;
//    private final FirstView.ContactListener contactListener = new FirstView.ContactListener() {
//        @Override
//        public void onClick(Contact contact) {
//            router.pushController(RouterTransaction.builder(new DetailsScreen(contact))
//                    .pushChangeHandler(new FadeChangeHandler())
//                    .popChangeHandler(new FadeChangeHandler())
//                    .build());
//        }
//    };

    private boolean mustHaveNumber = true;

    @Inject
    public FirstScreenPresenter(GetContacts getContacts, ActionBarOwner actionBarOwner) {
        this.getContacts = getContacts;
        this.actionBarOwner = actionBarOwner;
    }

    @Override
    public void attachView(FirstView view) {
        super.attachView(view);
        ActionBarOwner.MenuAction menuAction = new ActionBarOwner.MenuAction("Show only texts", new Action0() {
            @Override
            public void call() {
                fetchContacts(mustHaveNumber);
                mustHaveNumber = !mustHaveNumber;
            }
        }, 0);

        ActionBarOwner.Config config = new ActionBarOwner.Config(Arrays.asList(menuAction), "Contacts list", true, false);
        this.actionBarOwner.setConfig(config);


    }
    public void fetchContacts(boolean mustHaveNumber) {
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("mustHaveNumber", mustHaveNumber);

        subscribe(this.getContacts, bundle, false);
    }
}
