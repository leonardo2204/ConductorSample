package leonardo2204.com.br.flowtests.presenter;

import android.support.design.widget.NavigationView;
import android.view.Menu;

import mortar.ViewPresenter;

/**
 * Created by Leonardo on 18/03/2016.
 */
public class NavigationPresenter extends ViewPresenter<NavigationView> {

    public Menu setupMenuItem() {
        return getView().getMenu();
    }
}
