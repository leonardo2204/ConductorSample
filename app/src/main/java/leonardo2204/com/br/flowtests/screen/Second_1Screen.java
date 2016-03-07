package leonardo2204.com.br.flowtests.screen;

import android.os.Parcel;
import android.os.Parcelable;

import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;

/**
 * Created by Leonardo on 05/03/2016.
 */
@Layout(R.layout.screen_second_1)
public class Second_1Screen implements Parcelable {
    public Second_1Screen() {
    }

    public static final Creator<Second_1Screen> CREATOR = new Creator<Second_1Screen>() {
        @Override
        public Second_1Screen createFromParcel(Parcel in) {
            return new Second_1Screen();
        }

        @Override
        public Second_1Screen[] newArray(int size) {
            return new Second_1Screen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
