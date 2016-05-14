package leonardo2204.com.br.flowtests.screen;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import leonardo2204.com.br.flowtests.ContactsAdapter;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.RootActivity;
import leonardo2204.com.br.flowtests.conductor.BaseController;
import leonardo2204.com.br.flowtests.di.component.DaggerFirstScreenComponent;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.di.module.FirstScreenModule;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.presenter.FirstScreenPresenter;
import leonardo2204.com.br.flowtests.view.FirstView;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class FirstScreen extends BaseController<RecyclerView, List<Contact>, FirstView, FirstScreenPresenter> implements FirstView {

    @Inject
    protected FirstScreenPresenter presenter;
    @Bind(R.id.contacts_rv)
    RecyclerView contactsRv;
    private ContactsAdapter adapter;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.screen_first, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        FirstScreenComponent component = DaggerFirstScreenComponent
                .builder()
                .activityComponent(((RootActivity) getActivity()).getActivityComponent())
                .firstScreenModule(new FirstScreenModule())
                .build();

        component.inject(this);
        adapter = new ContactsAdapter();
        adapter.setOnClickListener(new ContactsAdapter.OnClickListener() {
            @Override
            public void onClick(Contact contact) {
                getRouter().pushController(RouterTransaction.builder(new DetailsScreen(contact))
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
                        .build());
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        contactsRv.setLayoutManager(llm);
        contactsRv.setAdapter(adapter);
    }

    @Override
    public List<Contact> getData() {
        return adapter.getContacts();
    }

    @Override
    public void setData(List<Contact> data) {
        adapter.setContacts(data);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @NonNull
    @Override
    public LceViewState<List<Contact>, FirstView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @NonNull
    @Override
    public FirstScreenPresenter createPresenter() {
        return presenter;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        this.presenter.fetchContacts(true);
    }
}
