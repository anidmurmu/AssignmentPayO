package in.example.ui.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.LayoutRes;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;


public class DynamicViewHandler {

    private View visibleView;
    private ViewGroup mParent;

    public DynamicViewHandler(Activity activity) {
        try {
            this.mParent = (ViewGroup) activity.getWindow().getDecorView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DynamicViewHandler(Fragment fragment) {
        mParent = (ViewGroup) fragment.getView();
        try {
            if (mParent instanceof ScrollView || mParent instanceof NestedScrollView) {
                mParent = (ViewGroup) mParent.getChildAt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DynamicViewHandler(View view) {
        mParent = (ViewGroup) view;
    }

    public View show(@LayoutRes int layoutId) {
        if (visibleView != null) {
            hide();
        }
        inflateView(layoutId);
        if (mParent != null) {
            mParent.addView(visibleView, 0);
            mParent.bringChildToFront(visibleView);
            visibleView.requestFocus();
        }
        return visibleView;
    }

    public void hide() {
        if (mParent != null) {
            mParent.removeView(visibleView);
            if (visibleView != null) {
                visibleView.clearFocus();
            }
        }
        visibleView = null;
    }

    private void inflateView(@LayoutRes int layout) {
        final LayoutInflater inflater = LayoutInflater.from(mParent.getContext());
        visibleView = inflater.inflate(layout, mParent, false);
    }

}
