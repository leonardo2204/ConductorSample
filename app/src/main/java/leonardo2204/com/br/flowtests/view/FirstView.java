package leonardo2204.com.br.flowtests.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Switch;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import flow.Flow;
import leonardo2204.com.br.flowtests.R;
import leonardo2204.com.br.flowtests.di.DaggerService;
import leonardo2204.com.br.flowtests.di.component.FirstScreenComponent;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.presenter.FirstScreenPresenter;
import mortar.MortarScope;

/**
 * Created by Leonardo on 04/03/2016.
 */
public class FirstView extends FrameLayout {

    @Bind(R.id.contacts_rv)
    public RecyclerView contacts_rv;
    @Inject
    protected FirstScreenPresenter presenter;
    LinearLayoutManager linearLayoutManager;
    boolean mustHaveNumber;

    public FirstView(Context context) {
        super(context);
        inject(context);
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inject(context);
    }

    public FirstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inject(context);
    }

    private void inject(Context context) {
        MortarScope scope = Flow.getService(Flow.getKey(this).getClass().getName(), context);
        ((FirstScreenComponent)scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        //DaggerService.<FirstScreenComponent>getDaggerComponent(context).inject(this);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();

        SavedState ss = new SavedState(parcelable);
        ss.mustHaveNumber = this.mustHaveNumber;

        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        this.mustHaveNumber = ss.mustHaveNumber;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
        presenter.fetchContacts(mustHaveNumber);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        this.contacts_rv.setLayoutManager(linearLayoutManager);
    }

    @OnCheckedChanged(R.id.switch_only_numbers)
    public void onlyNumbersSwitched(Switch v, boolean isChecked) {
        this.presenter.fetchContacts(isChecked);
        this.mustHaveNumber = isChecked;
    }

    public interface ContactListener {
        void onClick(Contact contact);
    }

    static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel source) {
                        return new SavedState(source);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
        boolean mustHaveNumber;

        public SavedState(Parcel source) {
            super(source);
            this.mustHaveNumber = source.readInt() == 1;
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.mustHaveNumber ? 1 : 0);
        }
    }

}

