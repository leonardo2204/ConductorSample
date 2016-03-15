package leonardo2204.com.br.flowtests.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.DetailScreenComponent;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.presenter.DetailsScreenPresenter;
import leonardo2204.com.br.flowtests.screen.DetailsScreen;
import leonardo2204.com.br.flowtests.screen.EditDialogScreen;
import leonardo2204.com.br.flowtests.ui.EndDrawableTextView;
import mortar.MortarScope;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class DetailsView extends LinearLayout {

    @Bind(R.id.name)
    EndDrawableTextView name;
    @Bind(R.id.telephone)
    EndDrawableTextView telephone;

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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        final DetailsScreen screen = Flow.getKey(this);
        name.setText(screen.getContact().getName());
        name.setOnDrawableClickListener(new EndDrawableTextView.OnDrawableClickListener() {
            @Override
            public void onEndDrawableClick() {
                Flow.get(DetailsView.this).set(new EditDialogScreen(screen.getContact()));
            }
        });

        telephone.setOnDrawableClickListener(new EndDrawableTextView.OnDrawableClickListener() {
            @Override
            public void onEndDrawableClick() {
                Toast.makeText(getContext(), "Open Edit Phone Dialog...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setTelephoneField(String telephoneNumber) {
        telephone.setText(telephoneNumber);
    }

    private void initUI(Context context) {
        setOrientation(VERTICAL);
        initializeInjection(context);
    }

    private void initializeInjection(Context context) {
        MortarScope scope = Flow.getService(Flow.getKey(this).getClass().getName(), context);
        ((DetailScreenComponent)scope.getService(DaggerService.SERVICE_NAME)).inject(this);
    }

}
