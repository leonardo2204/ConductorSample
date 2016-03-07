package leonardo2204.com.br.flowtests.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Leonardo on 07/03/2016.
 */
public class EndDrawableTextView extends TextView {

    private OnDrawableClickListener onDrawableClickListener;

    public EndDrawableTextView(Context context) {
        super(context);
        setupUI();
    }

    public EndDrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public EndDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }

    public void setOnDrawableClickListener(OnDrawableClickListener onDrawableClickListener) {
        this.onDrawableClickListener = onDrawableClickListener;
    }

    private void setupUI() {
        this.setOnTouchListener(new OnTouchListener() {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int leftEdgeRightDrawable = EndDrawableTextView.this.getRight() - EndDrawableTextView.this.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    if (event.getRawX() >= leftEdgeRightDrawable) {
                        if (EndDrawableTextView.this.onDrawableClickListener != null) {
                            EndDrawableTextView.this.onDrawableClickListener.onEndDrawableClick();
                            return true;
                        }
                    }
                }

                return true;
            }
        });
    }

    public interface OnDrawableClickListener {
        void onEndDrawableClick();
    }

}
