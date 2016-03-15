package leonardo2204.com.br.flowtests.view;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import flow.Flow;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.presenter.FirstScreenPresenter;
import mortar.MortarScope;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class FirstView extends FrameLayout {

    @Bind(R.id.contacts_rv)
    public RecyclerView contacts_rv;

    LinearLayoutManager linearLayoutManager;

    @Inject
    protected FirstScreenPresenter presenter;

    public FirstView(Context context) {
        super(context);
        inject(context);
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inject(context);
    }

    public FirstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inject(context);
    }

    private void inject(Context context) {
        MortarScope scope = Flow.getService(Flow.getKey(this).getClass().getName(), context);
        ((FirstScreenComponent)scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        //DaggerService.<FirstScreenComponent>getDaggerComponent(context).inject(this);
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
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        this.contacts_rv.setLayoutManager(linearLayoutManager);
    }

    @OnCheckedChanged(R.id.switch_only_numbers)
    public void onlyNumbersSwitched(Switch v, boolean isChecked) {
        this.presenter.fetchContacts(isChecked);
    }

    public interface ContactListener {
        void onClick(Contact contact);
    }

}

