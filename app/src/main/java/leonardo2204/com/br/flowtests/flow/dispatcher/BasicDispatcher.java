package leonardo2204.com.br.flowtests.flow.dispatcher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import flow.Direction;
import flow.Dispatcher;
import flow.Flow;
import flow.Traversal;
import flow.TraversalCallback;
import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.flow.FlowUtils;
import leonardo2204.com.br.flowtests.screen.FirstScreen;
import leonardo2204.com.br.flowtests.screen.SecondScreen;
import leonardo2204.com.br.flowtests.view.FirstView;

/**
 * Created by Leonardo on 04/03/2016.
 */
public final class BasicDispatcher implements Dispatcher {

    private static Map<Class,Integer> LAYOUT_CACHE = new LinkedHashMap<>();
    private final Activity activity;

    public BasicDispatcher(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void dispatch(final Traversal traversal, final TraversalCallback callback) {
        Object dest = traversal.destination.top();

        final ViewGroup frame = (ViewGroup) activity.findViewById(R.id.content);

        View fromView = null;
        if(traversal.origin != null){
            if(frame.getChildCount() > 0){
                fromView = frame.getChildAt(0);
                traversal.getState(traversal.origin.top()).save(frame.getChildAt(0));
            }
        }

        @LayoutRes final int layout = getLayout(dest);

        final View incomingView = LayoutInflater.from(traversal.createContext(dest,activity)).inflate(layout,frame,false);

        traversal.getState(traversal.destination.top()).restore(incomingView);

        if(fromView == null || traversal.direction == Direction.REPLACE) {
            frame.removeAllViews();
            frame.addView(incomingView);
            callback.onTraversalCompleted();
        }else{
            frame.addView(incomingView);
            final View fromViewFinal = fromView;
            FlowUtils.waitForMeasure(incomingView, new FlowUtils.OnMeasuredCallback() {
                @Override
                public void onMeasured(View view, int width, int height) {
                    runAnimation(frame, fromViewFinal, incomingView, traversal.direction, new TraversalCallback() {
                        @Override
                        public void onTraversalCompleted() {
                            frame.removeView(fromViewFinal);
                            callback.onTraversalCompleted();
                        }
                    });
                }
            });
        }
    }

    private int getLayout(Object screen) {
        Class layoutScreen = screen.getClass();
        Integer layoutId = LAYOUT_CACHE.get(layoutScreen);

        if (layoutId != null)
            return layoutId;

        Layout layout = (Layout) layoutScreen.getAnnotation(Layout.class);
        if(layout == null)
            throw new RuntimeException(String.format(Locale.getDefault(),"Must annotate Screen with @Layout annotation on class %s",layoutScreen.getClass().getName()));

        layoutId = layout.value();
        LAYOUT_CACHE.put(layoutScreen, layoutId);

        return layoutId;
    }

    private Animator createSegue(View from, View to, Direction direction) {
        boolean backward = direction == Direction.BACKWARD;
        int fromTranslation = backward ? from.getWidth() : -from.getWidth();
        int toTranslation = backward ? -to.getWidth() : to.getWidth();

        AnimatorSet set = new AnimatorSet();

        set.play(ObjectAnimator.ofFloat(from, View.TRANSLATION_X, fromTranslation));
        set.play(ObjectAnimator.ofFloat(to, View.TRANSLATION_X, toTranslation, 0));

        return set;
    }

    private void runAnimation(final ViewGroup container, final View from, final View to,
                              Direction direction, final TraversalCallback callback) {
        Animator animator = createSegue(from, to, direction);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                container.removeView(from);
                callback.onTraversalCompleted();
            }
        });
        animator.start();
    }

}
