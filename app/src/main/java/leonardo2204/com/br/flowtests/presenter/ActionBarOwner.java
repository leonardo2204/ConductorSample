package leonardo2204.com.br.flowtests.presenter;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import mortar.Presenter;
import mortar.bundler.BundleService;
import rx.functions.Action0;

/**
 * Created by Leonardo on 20/03/2016.
 */
@DaggerScope(ActivityComponent.class)
public class ActionBarOwner extends Presenter<ActionBarOwner.Activity> {

    private Config config;

    @Override
    protected BundleService extractBundleService(Activity view) {
        return BundleService.getBundleService(view.getContext());
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        if (config != null) update();
    }

    private void update() {
        if (!hasView()) return;

        Activity activity = getView();
        activity.setMenu(config.menuActionList);
        activity.setShowHomeEnabled(config.showHomeEnabled);
        activity.setUpButtonEnabled(config.upButtonEnabled);
        activity.setToolbarTitle(config.title);
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
        this.update();
    }

    public interface Activity {
        void setMenu(List<MenuAction> menuActionList);

        void setToolbarTitle(CharSequence title);

        void setShowHomeEnabled(boolean enabled);

        void setUpButtonEnabled(boolean enabled);

        Context getContext();
    }

    public static class Config {
        public final List<MenuAction> menuActionList;
        public final CharSequence title;
        public final boolean showHomeEnabled;
        public final boolean upButtonEnabled;


        public Config(List<MenuAction> menuActionList, CharSequence title, boolean showHomeEnabled, boolean upButtonEnabled) {
            this.menuActionList = menuActionList;
            this.title = title;
            this.showHomeEnabled = showHomeEnabled;
            this.upButtonEnabled = upButtonEnabled;
        }

        public Config withAction(List<MenuAction> menuActionList) {
            return new Config(menuActionList, title, showHomeEnabled, upButtonEnabled);
        }
    }

    public static class MenuAction {
        public final CharSequence title;
        public final Action0 action;
        public final int icon;

        public MenuAction(CharSequence title, Action0 action, int icon) {
            this.title = title;
            this.action = action;
            this.icon = icon;
        }
    }

}
