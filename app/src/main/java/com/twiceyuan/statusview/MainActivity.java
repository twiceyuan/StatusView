package com.twiceyuan.statusview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.twiceyuan.library.StatusView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] activeTexts = {"已报名", "已确认", "已帮忙", "已完成"};
        final String[] inactiveTexts = {"未报名", "未确认", "还未帮忙", "未完成"};
        final StatusView statusBar = (StatusView) findViewById(R.id.statusBar);
        assert statusBar != null;
        statusBar.setActiveTexts(activeTexts);
        statusBar.setInactiveTexts(inactiveTexts);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        assert seekBar != null;
        seekBar.setMax(4);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                statusBar.refreshStatus(progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
