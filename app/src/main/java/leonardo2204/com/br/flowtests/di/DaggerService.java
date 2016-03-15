package leonardo2204.com.br.flowtests.di;

import android.content.Context;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class DaggerService {

    public static final String SERVICE_NAME = DaggerService.class.getName();

    @SuppressWarnings("unchecked")
    public static <T> T getDaggerComponent(Context context) {
        //noinspection ResourceType
        return (T) context.getSystemService(SERVICE_NAME);
    }

}
