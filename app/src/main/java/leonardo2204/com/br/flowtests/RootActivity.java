package leonardo2204.com.br.flowtests;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.AppComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerActivityComponent;
import leonardo2204.com.br.flowtests.di.module.ActivityModule;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.presenter.ActionBarOwner;
import leonardo2204.com.br.flowtests.screen.FirstScreen;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

/**
 * Created by Leonardo on 04/03/2016.
 */
@DaggerScope(ActivityComponent.class)
public class RootActivity extends AppCompatActivity implements ActionBarOwner.Activity {

    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ActionBarOwner actionBarOwner;

    private MortarScope mortarScope;
    private List<ActionBarOwner.MenuAction> actionBarMenuActionList;
    private Router router;

    @Override
    public Object getSystemService(String name) {
        if(mortarScope == null){
            setupMortar();
        }

        return (mortarScope.hasService(name)) ? mortarScope.getService(name) : super.getSystemService(name);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
        setupConductor(savedInstanceState);
        setupMortar();
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
    }

    private void setupConductor(Bundle savedInstanceState) {
        router = Conductor.attachRouter(this, content, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(new FirstScreen());
        }
    }

    private ActivityComponent setupDagger() {
        return DaggerActivityComponent
                .builder()
                .appComponent(DaggerService.<AppComponent>getDaggerComponent(getApplicationContext()))
                .activityModule(new ActivityModule())
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (actionBarMenuActionList != null && actionBarMenuActionList.size() > 0) {

            for (final ActionBarOwner.MenuAction menuAction : actionBarMenuActionList) {
                menu.add(menuAction.title)
                        .setIcon(menuAction.icon)
                        .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                        .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                menuAction.action.call();
                                return true;
                            }
                        });
            }
        }

        return true;
    }

    private void setupUI() {
        actionBarOwner.takeView(this);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        actionBarOwner.dropView(this);
        actionBarOwner.setConfig(null);

        if (isFinishing() && mortarScope != null) {
            mortarScope.destroy();
            mortarScope = null;
        }

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        router.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return router.handleBack();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupMortar() {
        mortarScope = MortarScope.findChild(getApplicationContext(), getClass().getName());
        ActivityComponent component = setupDagger();

        if(mortarScope == null) {
            mortarScope = MortarScope
                    .buildChild(getApplicationContext())
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build(getClass().getName());
        }

        component.inject(this);
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack())
            super.onBackPressed();
    }

    @Override
    public void setMenu(List<ActionBarOwner.MenuAction> menuActionList) {
        if (menuActionList != actionBarMenuActionList) {
            actionBarMenuActionList = menuActionList;
            invalidateOptionsMenu();
        }
    }

    @Override
    public void setToolbarTitle(CharSequence title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    @Override
    public void setShowHomeEnabled(boolean enabled) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public void setUpButtonEnabled(boolean enabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
            getSupportActionBar().setHomeButtonEnabled(enabled);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
