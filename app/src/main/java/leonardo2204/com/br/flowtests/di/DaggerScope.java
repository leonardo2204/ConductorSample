package leonardo2204.com.br.flowtests.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Leonardo on 18/03/2016.
 */
@Scope
@Retention(RetentionPolicy.SOURCE)
public @interface DaggerScope {
    Class<?> value();
}
