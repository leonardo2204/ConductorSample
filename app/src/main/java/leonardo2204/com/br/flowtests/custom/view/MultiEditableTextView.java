package leonardo2204.com.br.flowtests.custom.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

import leonardo2204.com.br.flowtests.R;

/**
 * Created by Leonardo on 19/03/2016.
 */
public class MultiEditableTextView extends LinearLayout {

    private Drawable editDrawable;
    private Drawable dividerDrawable;
    private List<String> fieldList = new LinkedList<>();

    public MultiEditableTextView(Context context) {
        super(context);
        init(context);
    }

    public MultiEditableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiEditableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.dividerDrawable = ContextCompat.getDrawable(context, R.drawable.linear_divider);
        this.editDrawable = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_edit);
        setOrientation(VERTICAL);
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        setDividerDrawable(dividerDrawable);
    }

    public void addItem(@NonNull String item) {
        this.fieldList.add(item);
        addLayoutItem(item);
    }

    public void addItems(@NonNull List<String> items) {
        for (String item : items)
            addItem(item);
    }

    private void addLayoutItem(String item) {
        EndDrawableTextView endDrawableTextView = new EndDrawableTextView(getContext());
        endDrawableTextView.setGravity(Gravity.CENTER_VERTICAL);
        endDrawableTextView.setTextSize(18f);
        endDrawableTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, editDrawable, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        endDrawableTextView.setPadding(convertPxToDp(16), convertPxToDp(16), convertPxToDp(16), convertPxToDp(16));
        endDrawableTextView.setLayoutParams(params);
        endDrawableTextView.setText(item);
        this.addView(endDrawableTextView);
    }

    private int convertPxToDp(float dipValue) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

}
