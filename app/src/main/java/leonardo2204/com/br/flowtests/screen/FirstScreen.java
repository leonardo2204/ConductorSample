package leonardo2204.com.br.flowtests.screen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bluelinelabs.conductor.RouterTransaction;

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
public class FirstScreen extends BaseController implements FirstView<List<Contact>> {

    private static final String MUST_HAVE_NUMBER = "must_have_number";
    @Inject
    protected FirstScreenPresenter presenter;
    @Bind(R.id.contacts_rv)
    RecyclerView contactsRv;
    @Bind(R.id.loadingView)
    ProgressBar progress;
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
        super.onViewBound(view);
        setHasOptionsMenu(true);

        presenter.setView(this);
        presenter.fetchContacts(mustHaveNumber);
        adapter = new ContactsAdapter();
        adapter.setOnClickListener(new ContactsAdapter.OnClickListener() {
            @Override
            public void onClick(Contact contact) {
                getRouter().pushController(RouterTransaction.builder(new DetailsScreen(contact))
                        .build());
            }
        });

        RecyclerView.LayoutManager llm;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        else
            llm = new GridLayoutManager(getActivity(), 3);

        contactsRv.setLayoutManager(llm);
        contactsRv.setAdapter(adapter);
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        presenter.setView(null);
        adapter = null;
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
    public void setData(List<Contact> data) {
        this.adapter.setContacts(data);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {
        progress.setVisibility(View.GONE);
    }

}
