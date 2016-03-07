package leonardo2204.com.br.flowtests;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.AppComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerActivityComponent;
import leonardo2204.com.br.flowtests.di.module.ActivityModule;
import leonardo2204.com.br.flowtests.flow.dispatcher.BasicDispatcher;
import leonardo2204.com.br.flowtests.flow.parceler.BasicKeyParceler;
import leonardo2204.com.br.flowtests.flow.serviceFactory.DaggerServiceFactory;
import leonardo2204.com.br.flowtests.screen.FirstScreen;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class RootActivity extends AppCompatActivity {

    private MortarScope mortarScope;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public Object getSystemService(String name) {
        if (getApplication() == null) {
            return super.getSystemService(name);
        }

        return mortarScope!= null && mortarScope.hasService(name) ? mortarScope.getService(name) : super.getSystemService(name);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase,this)
                .addServicesFactory(new DaggerServiceFactory(this))
                .dispatcher(new BasicDispatcher(this))
                .defaultKey(new FirstScreen())
                .keyParceler(new BasicKeyParceler())
                .install();
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);

        checkMortar();
        DaggerService.<ActivityComponent>getDaggerComponent(this).inject(this);

        setSupportActionBar(toolbar);
    }

    private void checkMortar() {
        mortarScope = MortarScope.findChild(getApplicationContext(),getClass().getName());
        if(mortarScope == null)
            setupMortar();
    }

    private void setupMortar() {
        ActivityComponent activityComponent = DaggerActivityComponent
                .builder()
                .appComponent(DaggerService.<AppComponent>getDaggerComponent(getApplicationContext()))
                .activityModule(new ActivityModule(this))
                .build();

        mortarScope = MortarScope
                .buildChild(this)
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .withService(DaggerService.SERVICE_NAME, activityComponent)
                .build(getClass().getName());
    }

    @Override
    public void onBackPressed() {
        if(!Flow.get(this).goBack())
            super.onBackPressed();
    }
}
