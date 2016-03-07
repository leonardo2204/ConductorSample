package leonardo2204.com.br.flowtests.flow.key;

import flow.TreeKey;
import leonardo2204.com.br.flowtests.screen.SecondScreen;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class Second_1ScreenKey extends SecondScreenKey implements TreeKey {

    @Override
    public Object getParentKey() {
        return new SecondScreenKey();
    }
}
