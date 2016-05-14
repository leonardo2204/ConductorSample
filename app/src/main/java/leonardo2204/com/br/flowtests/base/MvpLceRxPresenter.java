package leonardo2204.com.br.flowtests.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import leonardo2204.com.br.flowtests.domain.interactor.DynamicUseCase;
import leonardo2204.com.br.flowtests.domain.interactor.UseCase;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Leonardo on 13/05/16.
 */
public abstract class MvpLceRxPresenter<V extends MvpLceView<M>, M> extends MvpBasePresenter<V>
        implements MvpPresenter<V> {

    protected Subscriber<M> subscriber;

    protected void unsubscribe() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }

        subscriber = null;
    }

    public void subscribe(@NonNull UseCase useCase, @Nullable Bundle bundle, final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        useCase.unsubscribe();

        subscriber = new Subscriber<M>() {
            private boolean ptr = pullToRefresh;

            @Override
            public void onCompleted() {
                MvpLceRxPresenter.this.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                MvpLceRxPresenter.this.onError(e, ptr);
            }

            @Override
            public void onNext(M m) {
                MvpLceRxPresenter.this.onNext(m);
            }
        };

        if (useCase instanceof DynamicUseCase) {
            DynamicUseCase dynamicUseCase = (DynamicUseCase) useCase;
            dynamicUseCase.execute(subscriber, bundle);
        } else {
            useCase.execute(subscriber);
        }
    }

    public void subscribe(Observable<M> observable, final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        unsubscribe();

        subscriber = new Subscriber<M>() {
            private boolean ptr = pullToRefresh;

            @Override
            public void onCompleted() {
                MvpLceRxPresenter.this.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                MvpLceRxPresenter.this.onError(e, ptr);
            }

            @Override
            public void onNext(M m) {
                MvpLceRxPresenter.this.onNext(m);
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    protected void onCompleted() {
        if (isViewAttached())
            getView().showContent();
        unsubscribe();
    }

    protected void onError(Throwable e, boolean pullToRefresh) {
        if (isViewAttached())
            getView().showError(e, pullToRefresh);
        unsubscribe();
    }

    protected void onNext(M data) {
        if (isViewAttached())
            getView().setData(data);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance)
            unsubscribe();
    }
}
