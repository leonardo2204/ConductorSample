package leonardo2204.com.br.flowtests.screen;


import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Layout(R.layout.screen_details)
@Parcel
public final class DetailsScreen {
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
}
