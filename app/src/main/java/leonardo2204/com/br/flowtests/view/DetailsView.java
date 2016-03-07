package leonardo2204.com.br.flowtests.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import javax.inject.Inject;

import flow.Flow;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerDetailScreenComponent;
import leonardo2204.com.br.flowtests.di.component.DetailScreenComponent;
import leonardo2204.com.br.flowtests.di.module.DetailScreenModule;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.presenter.DetailsScreenPresenter;
import leonardo2204.com.br.flowtests.screen.DetailsScreen;
import mortar.MortarScope;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class DetailsView extends LinearLayout {

    @Inject
    DetailsScreenPresenter presenter;

    public DetailsView(Context context) {
        super(context);
        initUI(context);
    }

    public DetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public DetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
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

    private void initUI(Context context) {
        DetailsScreen screen = Flow.getKey(this);
        MortarScope scope = MortarScope.findChild(context,getClass().getName());
        if(scope == null){

            DetailScreenComponent component = DaggerDetailScreenComponent
                    .builder()
                    .activityComponent(DaggerService.<ActivityComponent>getDaggerComponent(context))
                    .detailScreenModule(new DetailScreenModule(screen.getContact()))
                    .build();

            scope = MortarScope.getScope(context)
                    .buildChild()
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build(getClass().getName());
        }

        DaggerService.<DetailScreenComponent>getDaggerComponent(scope.createContext(context)).inject(this);
    }

}
