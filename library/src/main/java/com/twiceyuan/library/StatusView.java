package com.twiceyuan.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
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
 * <p/>
 * 状态进度
 */
public class StatusView extends LinearLayout {

    private LinearLayout mStatusBarContainer;

    private AppCompatTextView text1;
    private AppCompatTextView text2;
    private AppCompatTextView text3;
    private AppCompatTextView text4;

    private View view1;
    private View view2;
    private View view3;

    private TextView textLabel1;
    private TextView textLabel2;
    private TextView textLabel3;
    private TextView textLabel4;

    private View     views[];
    private TextView labels[];

    private String activeTexts[]   = {"测试数据", "测试数据", "测试数据", "测试数据"};
    private String inactiveTexts[] = {"测试数据", "测试数据", "测试数据", "测试数据"};

    private int textPosition[] = {0, 2, 4, 6};
    private int mStatus = 0;

    public StatusView(Context context) {
        super(context);
        init(context);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("SetTextI18n") private void init(Context context) {
        if (isInEditMode()) {
            TextView textView = new TextView(context);
            textView.setText(getClass().getSimpleName());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
            addView(textView);
            return;
        }

        mStatusBarContainer = (LinearLayout) LinearLayout.inflate(context, R.layout.view_status_bar, null);
        addView(mStatusBarContainer);
        text1 = $(R.id.text1);
        text2 = $(R.id.text2);
        text3 = $(R.id.text3);
        text4 = $(R.id.text4);

        textLabel1 = $(R.id.tv_label1);
        textLabel2 = $(R.id.tv_label2);
        textLabel3 = $(R.id.tv_label3);
        textLabel4 = $(R.id.tv_label4);

        View view1 = $(R.id.view1);
        View view2 = $(R.id.view2);
        View view3 = $(R.id.view3);

        views = new View[]{text1, view1, text2, view2, text3, view3, text4};
        labels = new TextView[]{textLabel1, textLabel2, textLabel3, textLabel4};

        // 测试数据 Stub!
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(inactiveTexts[i]);
        }
    }

    private void activeView(View view) {
        if (view == null) return;
        if (view instanceof AppCompatTextView) {
            ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), R.color.statusBarActive));
            ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.statusBarActive)));
        } else {
            view.setBackgroundResource(R.color.statusBarActive);
        }
    }

    private void inactiveView(View view) {
        if (view == null) return;
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), R.color.statusBarNormal));
            ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.statusBarNormal)));
        } else {
            view.setBackgroundResource(R.color.statusBarNormal);
        }
    }

    public void refreshStatus(int status) {
        mStatus = status;
        if (status < 0 && status > 4) throw new IllegalStateException("状态值不正确, 取值范围 1~4");
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
        for (int i = 0;i < labels.length;i++) {
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
