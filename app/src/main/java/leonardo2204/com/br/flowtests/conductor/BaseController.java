package leonardo2204.com.br.flowtests.conductor;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import butterknife.ButterKnife;

/**
 * Created by leonardo on 4/12/16.
 */
public abstract class BaseController extends Controller {

    private boolean created;

    protected BaseController() {
    }

    protected abstract View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);

    @NonNull
    @Override
    @CallSuper
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

        if (!created) {
            onCreate();
            created = true;
        }

        View v;
        v = inflateView(inflater, container);
        ButterKnife.bind(this, v);
        onViewBound(v);

        return v;
    }

    protected void onCreate() {

    }


    protected void onViewBound(@NonNull View view) {
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        ButterKnife.unbind(this);
    }
}
