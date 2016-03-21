package leonardo2204.com.br.flowtests.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import flow.TreeKey;
import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.DaggerEditDialogComponent;
import leonardo2204.com.br.flowtests.di.component.DetailScreenComponent;
import leonardo2204.com.br.flowtests.di.module.EditDialogModule;
import leonardo2204.com.br.flowtests.flow.keys.EditContactKey;
import leonardo2204.com.br.flowtests.flow.serviceFactory.InjectionComponent;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 08/03/2016.
 */
//@Dialog
@Layout(R.layout.edit_dialog_screen)
public class EditDialogScreen implements InjectionComponent<DetailScreenComponent>, TreeKey {

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
    public Object getParentKey() {
        return new EditContactKey(contact);
    }
}
