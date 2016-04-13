package leonardo2204.com.br.flowtests.mortar;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.ControllerChangeHandler;

import mortar.MortarScope;

/**
 * Created by leonardo on 4/12/16.
 */
public class MortarChangeHandler extends ControllerChangeHandler {

    @Override
    public void performChange(@NonNull ViewGroup container, View from, View to, boolean isPush, @NonNull ControllerChangeCompletedListener changeListener) {

        MortarScope scope = null;

        if (to != null && to.getParent() == null) {
            if (isPush || from == null) {
                container.addView(to);
                scope = MortarScope.getScope(container.getContext());
            }
        } else {
            scope = MortarScope.getScope(from.getContext());
            container.addView(to, container.indexOfChild(from));
        }

        if (to != null) {
            scope.buildChild().build(to.getClass().getName());
        }

        changeListener.onChangeCompleted();
    }


}
