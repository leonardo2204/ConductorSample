package leonardo2204.com.br.flowtests.flow.serviceFactory;

/**
 * Created by Leonardo on 08/03/2016.
 */
public interface InjectionComponent<T> {
    Object createComponent(T parent);
}
