package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;
import android.widget.Toast;

import org.parceler.Parcels;

import javax.inject.Inject;

import flow.Flow;
import leonardo2204.com.br.flowtests.domain.interactor.DefaultSubscriber;
import leonardo2204.com.br.flowtests.domain.interactor.GetDetailedContact;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.screen.DetailsScreen;
import leonardo2204.com.br.flowtests.view.DetailsView;
import mortar.ViewPresenter;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class DetailsScreenPresenter extends ViewPresenter<DetailsView> {

    private final GetDetailedContact getDetailedContact;
    private Contact contact;


    @Inject
    public DetailsScreenPresenter(GetDetailedContact getDetailedContact) {
        this.getDetailedContact = getDetailedContact;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        this.contact = ((DetailsScreen) Flow.getKey(getView())).getContact();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("contact", Parcels.wrap(contact));
        this.getDetailedContact.execute(new DetailedSubscriber(), bundle);
    }

    @Override
    protected void onExitScope() {
        super.onExitScope();
        this.getDetailedContact.unsubscribe();
    }

    class DetailedSubscriber extends DefaultSubscriber<Contact> {

        @Override
        public void onNext(Contact contact) {
            if (getView() != null) {
                getView().setTelephoneField(contact.getTelephone().get(0));
            }
        }

        @Override
        public void onError(Throwable e) {
            if(getView() != null)
                Toast.makeText(getView().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
