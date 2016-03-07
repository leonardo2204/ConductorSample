package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;
import android.widget.Toast;

import org.parceler.ParcelWrapper;
import org.parceler.Parcels;

import javax.inject.Inject;

import leonardo2204.com.br.flowtests.domain.interactor.DefaultSubscriber;
import leonardo2204.com.br.flowtests.domain.interactor.GetDetailedContact;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.view.DetailsView;
import mortar.ViewPresenter;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class DetailsScreenPresenter extends ViewPresenter<DetailsView> {

    private final GetDetailedContact getDetailedContact;
    private final Contact contact;


    @Inject
    public DetailsScreenPresenter(Contact contact, GetDetailedContact getDetailedContact) {
        this.getDetailedContact = getDetailedContact;
        this.contact = contact;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("contact", Parcels.wrap(contact));
        this.getDetailedContact.execute(new DetailedSubscriber(), bundle);
    }

    class DetailedSubscriber extends DefaultSubscriber<Contact> {

        @Override
        public void onNext(Contact contact) {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(getView().getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
