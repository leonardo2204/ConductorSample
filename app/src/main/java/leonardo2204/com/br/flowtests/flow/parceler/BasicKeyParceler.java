package leonardo2204.com.br.flowtests.flow.parceler;

import android.os.Parcelable;

import org.parceler.Parcel;
import org.parceler.Parcels;

import flow.KeyParceler;

/**
 * Created by Leonardo on 04/03/2016.
 */
public final class BasicKeyParceler implements KeyParceler {

    @Override
    public Parcelable toParcelable(Object key) {
        if (key.getClass().isAnnotationPresent(Parcel.class)) {
            return Parcels.wrap(key);
        }

        return (Parcelable) key;
    }

    @Override
    public Object toKey(Parcelable parcelable) {
        return parcelable;
    }
}
