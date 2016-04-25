package com.twiceyuan.library.statusView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by twiceYuan on 4/21/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 * <p>
 * 状态进度
 */
public class StatusView extends LinearLayout {

    private LinearLayout mStatusBarContainer;

    private View     views[];
    private TextView labels[];

    private String activeTexts[]   = {"测试数据", "测试数据", "测试数据", "测试数据", "测试数据"};
    private String inactiveTexts[] = {"测试数据", "测试数据", "测试数据", "测试数据", "测试数据"};

    private int textPosition[] = {0, 2, 4, 6, 8};

    private int mStatus = 0;

    private int mActiveColor;
    private int mNormalColor;

    public StatusView(Context context) {
        super(context);
        init(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (isInEditMode()) {
            TextView textView = new TextView(context);
            textView.setText(getClass().getSimpleName());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
            addView(textView);
            return;
        }

        mActiveColor = ContextCompat.getColor(context, R.color.statusBarActive);
        mNormalColor = ContextCompat.getColor(context, R.color.statusBarNormal);

        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.StatusView);

            mActiveColor = attributes.getColor(R.styleable.StatusView_activeColor, mActiveColor);
            mNormalColor = attributes.getColor(R.styleable.StatusView_normalColor, mNormalColor);

            attributes.recycle();
        }

        mStatusBarContainer = (LinearLayout) LinearLayout.inflate(context, R.layout.view_status_bar, null);
        addView(mStatusBarContainer);

        AppCompatTextView text1 = $(R.id.text1);
        AppCompatTextView text2 = $(R.id.text2);
        AppCompatTextView text3 = $(R.id.text3);
        AppCompatTextView text4 = $(R.id.text4);
        AppCompatTextView text5 = $(R.id.text5);

        TextView textLabel1 = $(R.id.tv_label1);
        TextView textLabel2 = $(R.id.tv_label2);
        TextView textLabel3 = $(R.id.tv_label3);
        TextView textLabel4 = $(R.id.tv_label4);
        TextView textLabel5 = $(R.id.tv_label5);

        View view1 = $(R.id.view1);
        View view2 = $(R.id.view2);
        View view3 = $(R.id.view3);
        View view4 = $(R.id.view4);

        views = new View[]{text1, view1, text2, view2, text3, view3, text4, view4, text5};
        labels = new TextView[]{textLabel1, textLabel2, textLabel3, textLabel4, textLabel5};

        // 测试数据 Stub!
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(inactiveTexts[i]);
        }
    }

    private void activeView(View view) {
        if (view == null) return;
        if (view instanceof AppCompatTextView) {
            ((TextView) view).setTextColor(mActiveColor);
            ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(mActiveColor));
        } else {
            view.setBackgroundColor(mActiveColor);
        }
    }

    private void inactiveView(View view) {
        if (view == null) return;
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(mNormalColor);
            ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(mNormalColor));
        } else {
            view.setBackgroundColor(mNormalColor);
        }
    }

    public void refreshStatus(int status) {
        mStatus = status;
        if (status < 0 && status > 5) throw new IllegalStateException("状态值不正确, 取值范围 1~4");
        int activeViewPosition;
        if (status == 0) {
            activeViewPosition = -1;
        } else {
            activeViewPosition = textPosition[status - 1];
        }
        for (int i = 0; i < views.length; i++) {
            if (i <= activeViewPosition) {
                activeView(views[i]);
            } else {
                inactiveView(views[i]);
            }
        }
        for (int i = 0; i < labels.length; i++) {
            if (i < status) {
                activeView(labels[i]);
                labels[i].setText(activeTexts[i]);
            } else {
                inactiveView(labels[i]);
                labels[i].setText(inactiveTexts[i]);
            }
        }
    }

    public void setActiveTexts(String[] activeTexts) {
        this.activeTexts = activeTexts;
        refreshStatus(mStatus);
    }

    public void setInactiveTexts(String[] inactiveTexts) {
        this.inactiveTexts = inactiveTexts;
        refreshStatus(mStatus);
    }

    public <T extends View> T $(int id) {
        //noinspection unchecked
        return (T) mStatusBarContainer.findViewById(id);
    }
}
