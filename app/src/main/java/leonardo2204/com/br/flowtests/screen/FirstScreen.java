package leonardo2204.com.br.flowtests.screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RouterTransaction;
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
import leonardo2204.com.br.flowtests.di.module.FirstScreenModule;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.presenter.FirstScreenPresenter;
import leonardo2204.com.br.flowtests.view.FirstView;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class FirstScreen extends BaseController<RecyclerView, List<Contact>, FirstView, FirstScreenPresenter> implements FirstView {

    private static final String MUST_HAVE_NUMBER = "must_have_number";
    @Inject
    protected FirstScreenPresenter presenter;
    @Bind(R.id.contacts_rv)
    RecyclerView contactsRv;
    private ContactsAdapter adapter;
    private boolean mustHaveNumber;

    @Override
    protected void onCreate() {
        setupInjection();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.screen_first, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        setHasOptionsMenu(true);
        super.onViewBound(view);

        adapter = new ContactsAdapter();
        adapter.setOnClickListener(new ContactsAdapter.OnClickListener() {
            @Override
            public void onClick(Contact contact) {
                getRouter().pushController(RouterTransaction.builder(new DetailsScreen(contact))
                        .build());
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        contactsRv.setLayoutManager(llm);
        contactsRv.setAdapter(adapter);
    }

    private void setupInjection() {
        DaggerFirstScreenComponent.builder()
                .activityComponent(((RootActivity) getActivity()).getActivityComponent())
                .firstScreenModule(new FirstScreenModule())
                .build().inject(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.first_screen_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        swapNumberOnlyText(menu.findItem(R.id.number_only));
    }

    private void swapNumberOnlyText(MenuItem item) {
        if (mustHaveNumber)
            item.setTitle(R.string.all_contacts);
        else
            item.setTitle(R.string.number_only);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.number_only) {
            mustHaveNumber = !mustHaveNumber;
            this.presenter.fetchContacts(mustHaveNumber);
            swapNumberOnlyText(item);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(MUST_HAVE_NUMBER, mustHaveNumber);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mustHaveNumber = savedInstanceState.getBoolean(MUST_HAVE_NUMBER);
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
        this.presenter.fetchContacts(mustHaveNumber);
    }
}
