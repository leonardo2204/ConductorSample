package leonardo2204.com.br.flowtests.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import flow.MultiKey;
import flow.TreeKey;
import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.DaggerEditDialogComponent;
import leonardo2204.com.br.flowtests.di.component.DetailScreenComponent;
import leonardo2204.com.br.flowtests.di.module.EditDialogModule;
import leonardo2204.com.br.flowtests.flow.annotations.Dialog;
import leonardo2204.com.br.flowtests.flow.keys.ContactKey;
import leonardo2204.com.br.flowtests.flow.keys.EditContactKey;
import leonardo2204.com.br.flowtests.flow.serviceFactory.InjectionComponent;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 08/03/2016.
 */
//@Dialog
@Layout(R.layout.edit_dialog_screen)
public class EditDialogScreen implements InjectionComponent<DetailScreenComponent>, MultiKey {

    final Contact contact;

    public EditDialogScreen(Contact contact) {
        this.contact = contact;
    }

    @Override
    public Object createComponent(DetailScreenComponent parent) {
        Log.d("injection", "injecting details");
        return DaggerEditDialogComponent.builder()
                .detailScreenComponent(parent)
                .editDialogModule(new EditDialogModule())
                .build();
    }

    @NonNull
    @Override
    public List<Object> getKeys() {
        return Collections.singletonList((Object) new EditContactKey(contact));
    }
}
