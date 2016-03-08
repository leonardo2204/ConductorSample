package leonardo2204.com.br.flowtests.screen;


import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerDetailScreenComponent;
import leonardo2204.com.br.flowtests.di.module.DetailScreenModule;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Parcel
public final class DetailsScreen extends BaseScreen {
    final Contact contact;

    @ParcelConstructor
    public DetailsScreen(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        DetailsScreen screen = (DetailsScreen)o;
        return contact.equals(screen.contact);
    }

    @Override
    public int hashCode() {
        return contact.hashCode();
    }

    @Override
    public Object createComponent(Object parent) {
        return DaggerDetailScreenComponent
                .builder()
                .activityComponent((ActivityComponent) parent)
                .detailScreenModule(new DetailScreenModule())
                .build();
    }

    @Override
    public int layoutResId() {
        return R.layout.screen_details;
    }
}
