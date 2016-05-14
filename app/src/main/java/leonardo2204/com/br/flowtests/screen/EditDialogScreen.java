package leonardo2204.com.br.flowtests.screen;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.DaggerEditDialogComponent;
import leonardo2204.com.br.flowtests.di.component.DetailScreenComponent;
import leonardo2204.com.br.flowtests.di.component.EditDialogComponent;
import leonardo2204.com.br.flowtests.di.module.EditDialogModule;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 08/03/2016.
 */
@DaggerScope(EditDialogComponent.class)
public class EditDialogScreen extends Controller {

    final Contact contact;

    public EditDialogScreen(Contact contact) {
        this.contact = contact;
    }

    public EditDialogScreen() {
        this.contact = null;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.edit_dialog_screen, container, false);
    }

    public Object createComponent(Object parent) {
        Log.d("injection", "injecting details");
        return DaggerEditDialogComponent.builder()
                .detailScreenComponent((DetailScreenComponent) parent)
                .editDialogModule(new EditDialogModule(contact, getRouter(), this))
                .build();
    }

}
