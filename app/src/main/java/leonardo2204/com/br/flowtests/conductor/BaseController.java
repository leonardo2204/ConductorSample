package leonardo2204.com.br.flowtests.conductor;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.conductor.viewstate.lce.MvpLceViewStateController;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import butterknife.ButterKnife;

/**
 * Created by leonardo on 4/12/16.
 */
public abstract class BaseController<CV extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>> extends MvpLceViewStateController<CV, M, V, P> {

    protected BaseController() {
    }

    protected abstract View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View v;
        v = inflateView(inflater, container);
        ButterKnife.bind(this, v);
        onViewBound(v);

        return v;
    }


    protected void onViewBound(@NonNull View view) {
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        ButterKnife.unbind(this);
    }
}
