package leonardo2204.com.br.flowtests.screen;

import android.os.Bundle;

import leonardo2204.com.br.flowtests.conductor.ButterknifeController;

/**
 * Created by Leonardo on 08/03/2016.
 */
public abstract class BaseScreen extends ButterknifeController {

    protected BaseScreen() {
    }

    protected BaseScreen(Bundle args) {
        super(args);
    }

}
