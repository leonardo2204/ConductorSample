package leonardo2204.com.br.flowtests;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.KeyDispatcher;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.AppComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerActivityComponent;
import leonardo2204.com.br.flowtests.di.module.ActivityModule;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.flow.dispatcher.Changer;
import leonardo2204.com.br.flowtests.flow.parceler.BasicKeyParceler;
import leonardo2204.com.br.flowtests.flow.serviceFactory.DaggerServiceFactory;
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

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase,this)
                .addServicesFactory(new DaggerServiceFactory(MortarScope.getScope(newBase)))
                .dispatcher(KeyDispatcher.configure(this, new Changer(this)).build())
                .defaultKey(new FirstScreen())
                .keyParceler(new BasicKeyParceler())
                .install();
        super.attachBaseContext(newBase);
    }

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
        setupDagger();
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return Flow.get(this).goBack();
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
        if (!Flow.get(this).goBack())
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
