package leonardo2204.com.br.flowtests.di.module;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.br.flowtests.di.component.EditDialogComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.presenter.EditDialogPresenter;

/**
 * Created by Leonardo on 08/03/2016.
 */
@Module
public class EditDialogModule {

    private final Contact contact;
    private final Router router;
    private final Controller controller;

    public EditDialogModule(Contact contact, Router router, Controller controller) {
        this.contact = contact;
        this.router = router;
        this.controller = controller;
    }

    @Provides
    @DaggerScope(EditDialogComponent.class)
    public EditDialogPresenter providesEditDialogPresenter() {
        return new EditDialogPresenter(contact);
    }

    @Provides
    @DaggerScope(EditDialogComponent.class)
    public Contact providesContact() {
        return this.contact;
    }

    @Provides
    @DaggerScope(EditDialogComponent.class)
    public Router providesRouter() {
        return this.router;
    }

    @Provides
    @DaggerScope(EditDialogComponent.class)
    public Controller providesController() {
        return this.controller;
    }

}
