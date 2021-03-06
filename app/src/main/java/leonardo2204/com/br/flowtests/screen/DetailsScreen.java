package leonardo2204.com.br.flowtests.screen;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerTransaction;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import javax.inject.Inject;

import butterknife.Bind;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.RootActivity;
import leonardo2204.com.br.flowtests.conductor.BaseController;
import leonardo2204.com.br.flowtests.custom.view.EndDrawableTextView;
import leonardo2204.com.br.flowtests.custom.view.MultiEditableTextView;
import leonardo2204.com.br.flowtests.di.component.DaggerDetailScreenComponent;
import leonardo2204.com.br.flowtests.di.component.DetailScreenComponent;
import leonardo2204.com.br.flowtests.di.module.DetailScreenModule;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.presenter.DetailsScreenPresenter;
import leonardo2204.com.br.flowtests.view.DetailsView;

/**
 * Created by Leonardo on 05/03/2016.
 */
public final class DetailsScreen extends BaseController<ScrollView, Contact, DetailsView, DetailsScreenPresenter> implements DetailsView {

    private final Contact contact;
    @Inject
    protected DetailsScreenPresenter presenter;
    @Bind(R.id.name)
    EndDrawableTextView nameEndDrawableTextView;
    @Bind(R.id.multi_telephone)
    MultiEditableTextView telephoneMultiEditableTextView;
    @Nullable
    @Bind(R.id.avatar)
    ImageView avatar;

    public DetailsScreen(Contact contact) {
        this.contact = contact;
    }

    public DetailsScreen() {
        this.contact = null;
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.screen_details, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        DetailScreenComponent component = DaggerDetailScreenComponent.builder()
                .activityComponent(((RootActivity) getActivity()).getActivityComponent())
                .detailScreenModule(new DetailScreenModule(contact))
                .build();

        component.inject(this);

        if (contact != null) {
            this.nameEndDrawableTextView.setText(contact.getName());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerTransaction.ControllerChangeType changeType) {
        super.onChangeEnded(changeHandler, changeType);
        if (changeType == ControllerTransaction.ControllerChangeType.PUSH_ENTER)
            setHasOptionsMenu(true);
    }

    @Override
    protected void onChangeStarted(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerTransaction.ControllerChangeType changeType) {
        super.onChangeStarted(changeHandler, changeType);
        if (changeType == ControllerTransaction.ControllerChangeType.POP_EXIT)
            setHasOptionsMenu(false);
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public Contact getData() {
        return contact;
    }

    @Override
    public void setData(Contact data) {
        if (data.getTelephone() != null)
            this.telephoneMultiEditableTextView.addItems(data.getTelephone());
        if (data.getPicture() != null && avatar != null) {
            if (data.getPicture() != null)
                avatar.setImageBitmap(data.getPicture());
        }
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @NonNull
    @Override
    public LceViewState<Contact, DetailsView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @NonNull
    @Override
    public DetailsScreenPresenter createPresenter() {
        return presenter;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        this.presenter.fetchDetailedContact();
    }
}
