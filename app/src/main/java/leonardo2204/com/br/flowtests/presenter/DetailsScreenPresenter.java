package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.Arrays;

import javax.inject.Inject;

import flow.Flow;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.DetailScreenComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.domain.interactor.DefaultSubscriber;
import leonardo2204.com.br.flowtests.domain.interactor.GetDetailedContact;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.screen.DetailsScreen;
import leonardo2204.com.br.flowtests.view.DetailsView;
import mortar.ViewPresenter;
import rx.functions.Action0;

/**
 * Created by Leonardo on 05/03/2016.
 */
@DaggerScope(DetailScreenComponent.class)
public class DetailsScreenPresenter extends ViewPresenter<DetailsView> {

    private final GetDetailedContact getDetailedContact;
    private final ActionBarOwner actionBarOwner;
    private Contact contact;

    @Inject
    public DetailsScreenPresenter(GetDetailedContact getDetailedContact, ActionBarOwner actionBarOwner) {
        this.getDetailedContact = getDetailedContact;
        this.actionBarOwner = actionBarOwner;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        this.contact = ((DetailsScreen) Flow.getKey(getView())).getContact();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("contact", Parcels.wrap(contact));
        this.getDetailedContact.execute(new DetailedSubscriber(), bundle);

        ActionBarOwner.MenuAction menuAction = new ActionBarOwner.MenuAction(contact.getName(), new Action0() {
            @Override
            public void call() {
                Toast.makeText(getView().getContext(), "Call", Toast.LENGTH_SHORT).show();
            }
        }, R.drawable.ic_call_black_24dp);

        ActionBarOwner.MenuAction menuAction2 = new ActionBarOwner.MenuAction(contact.getName(), new Action0() {
            @Override
            public void call() {
                Toast.makeText(getView().getContext(), "Call 2", Toast.LENGTH_SHORT).show();
            }
        }, R.drawable.ic_call_black_24dp);

        ActionBarOwner.Config config = new ActionBarOwner.Config(Arrays.asList(menuAction, menuAction2), contact.getName(), false, true);
        this.actionBarOwner.setConfig(config);

//        this.toolbarPresenter.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//        this.toolbarPresenter.setNavigationAction(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //noinspection CheckResult
//                Flow.get(getView()).goBack();
//            }
//        });
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
                getView().setTelephoneField(contact.getTelephone());
            }
        }

        @Override
        public void onError(Throwable e) {
            if(getView() != null)
                Toast.makeText(getView().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
