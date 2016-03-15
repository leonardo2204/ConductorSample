package leonardo2204.com.br.flowtests.screen;


import android.support.annotation.NonNull;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import flow.TreeKey;
import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.AppComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerDetailScreenComponent;
import leonardo2204.com.br.flowtests.di.module.DetailScreenModule;
import leonardo2204.com.br.flowtests.flow.keys.ContactKey;
import leonardo2204.com.br.flowtests.flow.keys.EditContactKey;
import leonardo2204.com.br.flowtests.flow.serviceFactory.InjectionComponent;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Parcel
@Layout(R.layout.screen_details)
public final class DetailsScreen extends ContactKey implements InjectionComponent<AppComponent>, TreeKey {

    @ParcelConstructor
    public DetailsScreen(Contact contact) {
        super(contact);
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public Object createComponent(AppComponent parent) {
        return DaggerDetailScreenComponent
                .builder()
                .appComponent(parent)
                .detailScreenModule(new DetailScreenModule())
                .build();
    }

    @NonNull
    @Override
    public Object getParentKey() {
        return new EditContactKey(contact);
    }
}
