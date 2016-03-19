package leonardo2204.com.br.flowtests.presenter;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mortar.ViewPresenter;

/**
 * Created by Leonardo on 18/03/2016.
 */
public class ToolbarPresenter extends ViewPresenter<Toolbar> {

    public void setTitle(String title) {
        getView().setTitle(title);
    }

    public void setNavigationIcon(@DrawableRes int resId) {
        getView().setNavigationIcon(resId);
    }

    public void setNavigationClickListener(View.OnClickListener onClickListener) {
        getView().setNavigationOnClickListener(onClickListener);
    }
}
