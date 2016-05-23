package leonardo2204.com.br.flowtests.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import leonardo2204.com.br.flowtests.domain.interactor.DynamicUseCase;
import leonardo2204.com.br.flowtests.domain.interactor.UseCase;
import leonardo2204.com.br.flowtests.view.MvpView;
import rx.Subscriber;

/**
 * Created by Leonardo on 13/05/16.
 */
public abstract class RxPresenter<M, V extends MvpView<M>> {

    protected Subscriber<M> subscriber;
    protected V view;

    protected void unsubscribe() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }

        subscriber = null;
    }

    public void subscribe(@NonNull UseCase useCase, @Nullable Bundle bundle) {
        useCase.unsubscribe();

        subscriber = new Subscriber<M>() {

            @Override
            public void onCompleted() {
                RxPresenter.this.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                RxPresenter.this.onError(e);
            }

            @Override
            public void onNext(M m) {
                RxPresenter.this.onNext(m);
            }
        };

        if (useCase instanceof DynamicUseCase) {
            DynamicUseCase dynamicUseCase = (DynamicUseCase) useCase;
            dynamicUseCase.execute(subscriber, bundle);
        } else {
            useCase.execute(subscriber);
        }
    }

    protected void onCompleted() {
        unsubscribe();
        if (view != null)
            view.onCompleted();
    }

    protected void onError(Throwable e) {
        unsubscribe();
        if (view != null)
            view.onError(e);
    }

    protected void onNext(M data) {
        if (view != null)
            view.setData(data);
    }

    public void setView(V view) {
        this.view = view;
    }
}
