package leonardo2204.com.br.flowtests;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;

import butterknife.Bind;
import butterknife.ButterKnife;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerActivityComponent;
import leonardo2204.com.br.flowtests.di.module.ActivityModule;
import leonardo2204.com.br.flowtests.di.scope.DaggerScope;
import leonardo2204.com.br.flowtests.screen.FirstScreen;

/**
 * Created by Leonardo on 04/03/2016.
 */
@DaggerScope(ActivityComponent.class)
public class RootActivity extends AppCompatActivity {

    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ActivityComponent activityComponent;
    private Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
        setupDagger();
        setupConductor(savedInstanceState);
    }

    private void setupConductor(Bundle savedInstanceState) {
        router = Conductor.attachRouter(this, content, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(new FirstScreen());
        }
    }

    private void setupDagger() {
        activityComponent = DaggerActivityComponent
                .builder()
                .activityModule(new ActivityModule(this))
                .build();

    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }


    private void setupUI() {
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        //actionBarOwner.setConfig(null);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        router.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return router.handleBack();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack())
            super.onBackPressed();
    }
}
