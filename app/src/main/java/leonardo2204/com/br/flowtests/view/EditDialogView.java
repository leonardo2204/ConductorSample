package leonardo2204.com.br.flowtests.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.EditDialogComponent;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.presenter.EditDialogPresenter;
import mortar.MortarScope;

/**
 * Created by leonardo on 4/13/16.
 */
public class EditDialogView extends FrameLayout {

    @Inject
    protected EditDialogPresenter presenter;
    @Inject
    protected Contact contact;

    @Bind(R.id.name)
    EditText name;

    public EditDialogView(Context context) {
        super(context);
        initUI(context);
    }

    public EditDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public EditDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
    }

    private void initUI(Context context) {
        initializeInjection(context);
    }

    private void initializeInjection(Context context) {
        MortarScope scope = MortarScope.getScope(context);
        ((EditDialogComponent) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupUI();
    }

    private void setupUI() {
        ButterKnife.bind(this);
        name.setText(contact.getName());
    }

    @OnClick(R.id.cancel)
    public void onCancelClick(View v) {

    }

    @OnClick(R.id.save)
    public void onSave(View v) {

    }

}
