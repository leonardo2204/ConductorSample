package leonardo2204.com.br.flowtests;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.KeyChanger;
import flow.KeyDispatcher;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.flow.dispatcher.Changer;
import leonardo2204.com.br.flowtests.flow.dispatcher.MortarDispatcher;
import leonardo2204.com.br.flowtests.flow.parceler.BasicKeyParceler;
import leonardo2204.com.br.flowtests.flow.serviceFactory.DaggerServiceFactory;
import leonardo2204.com.br.flowtests.screen.FirstScreen;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class RootActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MortarScope mortarScope;

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase,this)
                .addServicesFactory(new DaggerServiceFactory(MortarScope.getScope(newBase)))
                //.dispatcher(new BasicDispatcher(this))
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
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

//    private void checkMortar() {
//        mortarScope = MortarScope.findChild(getApplicationContext(),getClass().getName());
//        if(mortarScope == null)
//            setupMortar();
//    }

    private void setupMortar() {
        mortarScope = MortarScope.findChild(getApplicationContext(),getClass().getName());
        if(mortarScope == null) {
            mortarScope = MortarScope
                    .buildChild(getApplicationContext())
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .build(getClass().getName());
        }
}

    @Override
    public void onBackPressed() {
        if(!Flow.get(this).goBack())
            super.onBackPressed();
    }
}
