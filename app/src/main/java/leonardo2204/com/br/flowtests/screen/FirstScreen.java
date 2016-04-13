package leonardo2204.com.br.flowtests.screen;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.component.ActivityComponent;
import leonardo2204.com.br.flowtests.di.component.DaggerFirstScreenComponent;
import leonardo2204.com.br.flowtests.di.module.FirstScreenModule;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class FirstScreen extends BaseScreen {

    @Override
    protected Object createComponent(Object parent) {
        return DaggerFirstScreenComponent
                .builder()
                .activityComponent((ActivityComponent) parent)
                .firstScreenModule(new FirstScreenModule(getRouter()))
                .build();
    }

    @Override
    protected String serviceName() {
        return this.getClass().getName();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.screen_first, container, false);
    }
}
