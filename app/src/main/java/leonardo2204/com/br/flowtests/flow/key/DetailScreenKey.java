package leonardo2204.com.br.flowtests.flow.key;

import flow.ClassKey;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class DetailScreenKey extends ClassKey {
    private final Contact contact;

    public DetailScreenKey(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DetailScreenKey && contact.equals(((DetailScreenKey)o).contact);
    }

    @Override
    public int hashCode() {
        return contact.hashCode();
    }
}
