package leonardo2204.com.br.flowtests.conductor;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.rxlifecycle.ControllerEvent;
import com.bluelinelabs.conductor.rxlifecycle.ControllerLifecycleProvider;
import com.bluelinelabs.conductor.rxlifecycle.ControllerLifecycleSubjectHelper;
import com.bluelinelabs.conductor.rxlifecycle.RxControllerLifecycle;
import com.trello.rxlifecycle.RxLifecycle;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by leonardo on 4/12/16.
 */
public abstract class RxController extends Controller implements ControllerLifecycleProvider {

    private final BehaviorSubject<ControllerEvent> lifecycleSubject;

    public RxController() {
        lifecycleSubject = ControllerLifecycleSubjectHelper.create(this);
    }


    @Override
    @NonNull
    @CheckResult
    public final Observable<ControllerEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull ControllerEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxControllerLifecycle.bindController(lifecycleSubject);
    }

}
