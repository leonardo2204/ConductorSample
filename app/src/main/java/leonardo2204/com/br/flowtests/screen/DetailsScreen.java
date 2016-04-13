package leonardo2204.com.br.flowtests.screen;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerDetailScreenComponent;
import leonardo2204.com.br.flowtests.di.module.DetailScreenModule;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 05/03/2016.
 */
public final class DetailsScreen extends BaseScreen {

    private final Contact contact;

    public DetailsScreen(Contact contact) {
        this.contact = contact;
    }

    public DetailsScreen() {
        super();
        contact = null;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public Object createComponent(Object parent) {
        return DaggerDetailScreenComponent
                .builder()
                .activityComponent((ActivityComponent) parent)
                .detailScreenModule(new DetailScreenModule(contact, this))
                .build();
    }

    @Override
    protected String serviceName() {
        return this.getClass().getName();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.screen_details, container, false);
    }
}
