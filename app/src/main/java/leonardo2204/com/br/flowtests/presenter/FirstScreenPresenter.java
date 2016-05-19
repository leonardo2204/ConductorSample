package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import leonardo2204.com.br.flowtests.base.MvpLceRxPresenter;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.domain.interactor.GetContacts;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.view.FirstView;

/**
 * Created by Leonardo on 05/03/2016.
 */
@DaggerScope(FirstScreenComponent.class)
public class FirstScreenPresenter extends MvpLceRxPresenter<FirstView, List<Contact>> {

    private final GetContacts getContacts;

    @Inject
    public FirstScreenPresenter(GetContacts getContacts) {
        this.getContacts = getContacts;
    }

    public void fetchContacts(boolean mustHaveNumber) {
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("mustHaveNumber", mustHaveNumber);

        subscribe(this.getContacts, bundle, false);
    }
}
