package leonardo2204.com.br.flowtests.presenter;

import android.os.Bundle;

import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.view.EditDialogView;
import mortar.ViewPresenter;

/**
 * Created by leonardo on 4/13/16.
 */
public class EditDialogPresenter extends ViewPresenter<EditDialogView> {

    private final Contact contact;

    public EditDialogPresenter(Contact contact) {
        this.contact = contact;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);

        if (getView() == null)
            return;


    }
}
