package leonardo2204.com.br.flowtests.conductor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import leonardo2204.com.br.flowtests.di.DaggerService;
import mortar.MortarScope;

/**
 * Created by leonardo on 4/12/16.
 */
public abstract class ButterknifeController extends RxController {

    protected ButterknifeController() {
    }

    protected ButterknifeController(Bundle args) {
        super(args);
    }

    protected abstract View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);

    protected Object createComponent(Object parent) {
        return null;
    }

    protected String serviceName() {
        return null;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        MortarScope scope = MortarScope.getScope(container.getContext());
        MortarScope child;
        View v;

        Object component = createComponent(scope.getService(DaggerService.SERVICE_NAME));
        if (component != null && scope.findChild(serviceName()) == null)
            child = scope.buildChild().withService(DaggerService.SERVICE_NAME, component).build(serviceName());
        else
            child = scope.findChild(serviceName());

        if (child != null)
            v = inflateView(inflater.cloneInContext(child.createContext(container.getContext())), container);
        else
            v = inflateView(inflater, container);


        ButterKnife.bind(this, v);
        return v;
    }


    protected void onViewBound(@NonNull View view) {
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        ButterKnife.unbind(this);
        if (MortarScope.findChild(view.getContext(), serviceName()) != null) {
            Log.e("Mortar", "destroying " + serviceName());
            MortarScope.findChild(view.getContext(), serviceName()).destroy();
        }
    }
}
