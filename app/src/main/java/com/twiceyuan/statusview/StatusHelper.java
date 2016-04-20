package com.twiceyuan.statusview;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by twiceYuan on 4/20/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 */
public class StatusHelper {

    private LinearLayout mStatusBarContainer;

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;

    private View view1;
    private View view2;
    private View view3;

    private TextView textLabel1;
    private TextView textLabel2;
    private TextView textLabel3;
    private TextView textLabel4;

    private View     views[];
    private TextView labels[];

    private String activeTexts[];
    private String inactiveTexts[];

    private int textPosition[] = {0, 2, 4, 6};

    public StatusHelper(LinearLayout statusBarContainer, String[] activeTexts, String[] inactiveTexts) {
        mStatusBarContainer = statusBarContainer;

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

        this.activeTexts = activeTexts;
        this.inactiveTexts = inactiveTexts;

        views = new View[]{text1, view1, text2, view2, text3, view3, text4};
        labels = new TextView[]{textLabel1, textLabel2, textLabel3, textLabel4};

        // 测试数据 Stub!
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(inactiveTexts[i]);
        }
    }

    public void refreshStatus(int status) {
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

    private void activeView(View view) {
        if (view == null) return;
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), R.color.statusBarActive));
        } else {
            view.setBackgroundResource(R.color.statusBarActive);
        }
    }

    private void inactiveView(View view) {
        if (view == null) return;
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), R.color.statusBarNormal));
        } else {
            view.setBackgroundResource(R.color.statusBarNormal);
        }
    }

    public static StatusHelper with(LinearLayout statusBarContainer, String[] activeTexts, String[] inactiveTexts) {
        return new StatusHelper(statusBarContainer, activeTexts, inactiveTexts);
    }

    public <T extends View> T $(int id) {
        //noinspection unchecked
        return (T) mStatusBarContainer.findViewById(id);
    }
}
