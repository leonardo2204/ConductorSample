package leonardo2204.com.br.flowtests.screen;


import android.support.annotation.NonNull;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import flow.TreeKey;
import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerDetailScreenComponent;
import leonardo2204.com.br.flowtests.di.module.DetailScreenModule;
import leonardo2204.com.br.flowtests.flow.keys.DetailsKey;
import leonardo2204.com.br.flowtests.flow.keys.EditContactKey;
import leonardo2204.com.br.flowtests.flow.serviceFactory.InjectionComponent;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Parcel
@Layout(R.layout.screen_details)
public final class DetailsScreen extends DetailsKey implements InjectionComponent<ActivityComponent>, TreeKey {

    @ParcelConstructor
    public DetailsScreen(Contact contact) {
        super(contact);
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public Object createComponent(ActivityComponent parent) {
        return DaggerDetailScreenComponent
                .builder()
                .activityComponent(parent)
                .detailScreenModule(new DetailScreenModule())
                .build();
    }

    @NonNull
    @Override
    public Object getParentKey() {
        return new EditContactKey(contact);
    }
}
