package leonardo2204.com.br.flowtests.screen;

import android.os.Parcel;
import android.os.Parcelable;

import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;

/**
 * Created by Leonardo on 04/03/2016.
 */
@Layout(R.layout.screen_second)
public class SecondScreen implements Parcelable {

    public static final Creator<SecondScreen> CREATOR = new Creator<SecondScreen>() {
        @Override
        public SecondScreen createFromParcel(Parcel in) {
            return new SecondScreen();
        }

        @Override
        public SecondScreen[] newArray(int size) {
            return new SecondScreen[size];
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
