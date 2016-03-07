package leonardo2204.com.br.flowtests.screen;

import android.os.Parcel;
import android.os.Parcelable;

import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;

/**
 * Created by Leonardo on 04/03/2016.
 */
@Layout(R.layout.screen_first)
@org.parceler.Parcel
public class FirstScreen {

    public FirstScreen() {
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FirstScreen;
    }
}
