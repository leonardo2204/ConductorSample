package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;

import org.parceler.Parcels;

import javax.inject.Inject;

import leonardo2204.com.br.flowtests.base.MvpLceRxPresenter;
import leonardo2204.com.br.flowtests.di.component.DetailScreenComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.domain.interactor.GetDetailedContact;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.view.DetailsView;

/**
 * Created by Leonardo on 05/03/2016.
 */
@DaggerScope(DetailScreenComponent.class)
public class DetailsScreenPresenter extends MvpLceRxPresenter<DetailsView, Contact> {

    private final GetDetailedContact getDetailedContact;
    private Contact contact;

    @Inject
    public DetailsScreenPresenter(GetDetailedContact getDetailedContact, Contact contact) {
        this.getDetailedContact = getDetailedContact;
        this.contact = contact;
    }

    public void fetchDetailedContact() {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("contact", Parcels.wrap(contact));

        subscribe(getDetailedContact, bundle, false);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }
}
