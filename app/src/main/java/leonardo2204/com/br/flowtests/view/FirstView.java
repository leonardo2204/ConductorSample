package leonardo2204.com.br.flowtests.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerFirstScreenComponent;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.di.module.FirstScreenModule;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.presenter.FirstScreenPresenter;
import mortar.MortarScope;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class FirstView extends FrameLayout {

    @Bind(R.id.contacts_rv)
    public RecyclerView contacts_rv;

    @Inject
    protected FirstScreenPresenter presenter;

    public FirstView(Context context) {
        super(context);
        initUI(context);
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public FirstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
    }

    private void initUI(Context context) {
        MortarScope scope = MortarScope.findChild(context,getClass().getName());
        if(scope == null){

            FirstScreenComponent component = DaggerFirstScreenComponent
                    .builder()
                    .activityComponent(DaggerService.<ActivityComponent>getDaggerComponent(context))
                    .firstScreenModule(new FirstScreenModule())
                    .build();

            scope = MortarScope.getScope(context)
                    .buildChild()
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build(getClass().getName());
        }

        DaggerService.<FirstScreenComponent>getDaggerComponent(scope.createContext(context)).inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        this.contacts_rv.setLayoutManager(linearLayoutManager);
    }

    public interface ContactListener {
        void onClick(Contact contact);
    }

}

