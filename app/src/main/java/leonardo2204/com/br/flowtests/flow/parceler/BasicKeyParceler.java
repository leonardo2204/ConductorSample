package leonardo2204.com.br.flowtests.flow.parceler;

import android.os.Parcelable;

import flow.KeyParceler;

/**
 * Created by Leonardo on 04/03/2016.
 */
public final class BasicKeyParceler implements KeyParceler {

    @Override
    public Parcelable toParcelable(Object key) {
        return (Parcelable) key;
    }

    @Override
    public Object toKey(Parcelable parcelable) {
        return parcelable;
    }
}
