package leonardo2204.com.br.flowtests.flow.dispatcher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import flow.Direction;
import flow.KeyChanger;
import flow.State;
import flow.TraversalCallback;
import leonardo2204.com.br.flowtests.Layout;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.flow.FlowUtils;

/**
 * Created by Leonardo on 08/03/2016.
 */
public class Changer implements KeyChanger {

    private static Map<Class,Integer> LAYOUT_CACHE = new LinkedHashMap<>();
    private final Activity activity;

    public Changer(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void changeKey(@Nullable State outgoingState, State incomingState,
                          final Direction direction, Map<Object, Context> incomingContexts,
                          final TraversalCallback callback) {

        final ViewGroup frame = (ViewGroup) activity.findViewById(R.id.content);

        View fromView = null;
        if(incomingState != null){

            if(frame.getChildCount() > 0){
                fromView = frame.getChildAt(0);
                incomingState.save(frame.getChildAt(0));
            }
        }

        Context context = incomingContexts.get(incomingState.getKey());
        @LayoutRes final int layout = getLayout(incomingState.getKey());

        //MortarScope scope = MortarScope.getScope(context.getApplicationContext());
        //Context newContext =  scope.findChild(incomingState.getKey().getClass().getName()).createContext(context);

        final View incomingView = LayoutInflater.from(context).inflate(layout,frame,false);
        if(outgoingState != null)
            outgoingState.restore(incomingView);

        if(fromView == null || direction == Direction.REPLACE) {
            frame.removeAllViews();
            frame.addView(incomingView);
            callback.onTraversalCompleted();
        } else {
            frame.addView(incomingView);
            final View fromViewFinal = fromView;
            FlowUtils.waitForMeasure(incomingView, new FlowUtils.OnMeasuredCallback() {
                @Override
                public void onMeasured(View view, int width, int height) {
                    runAnimation(frame, fromViewFinal, incomingView, direction, new TraversalCallback() {
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
            throw new RuntimeException(String.format(Locale.getDefault(),"Must annotate Screen with @Layout annotation on class %s",layoutScreen.getClass().getSimpleName()));

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
