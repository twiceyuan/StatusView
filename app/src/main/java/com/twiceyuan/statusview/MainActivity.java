package com.twiceyuan.statusview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] activeTexts = {"已报名", "已确认", "已帮忙", "已完成"};
        final String[] inactiveTexts = {"未报名", "未确认", "还未帮忙", "未完成"};
        final LinearLayout statusBar = (LinearLayout) findViewById(R.id.statusBar);
        final StatusHelper statusHelper = StatusHelper.with(statusBar, activeTexts, inactiveTexts);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        assert seekBar != null;
        seekBar.setMax(4);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                statusHelper.refreshStatus(progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
