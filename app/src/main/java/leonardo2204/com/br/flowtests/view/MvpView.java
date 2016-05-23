package leonardo2204.com.br.flowtests.view;

/**
 * Created by leonardo on 5/23/16.
 */
public interface MvpView<M> {

    void setData(M data);

    void onError(Throwable e);

    void onCompleted();

}
