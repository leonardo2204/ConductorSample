package leonardo2204.com.br.flowtests.flow.keys;

import flow.ClassKey;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 08/03/2016.
 */
public abstract class ContactKey extends ClassKey {
    protected final Contact contact;

    protected ContactKey(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ContactKey screen = (ContactKey)o;
        return contact.equals(screen.contact);
    }

    @Override
    public int hashCode() {
        return contact.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +contact.toString();
    }
}
