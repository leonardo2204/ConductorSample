package leonardo2204.com.br.flowtests.flow.keys;

import android.support.annotation.NonNull;

import flow.TreeKey;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 08/03/2016.
 */
public final class EditContactKey extends ContactKey implements TreeKey {

    public EditContactKey(Contact contact) {
        super(contact);
    }

    @NonNull
    @Override
    public Object getParentKey() {
        return new ContactsUIKey();
    }
}
