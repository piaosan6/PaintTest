package com.pjm.painttest.MaskFilterTest;

import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.pjm.painttest.MaskFilterTest.customView.EmBossView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmbossMaskTestActivity extends AppCompatActivity {


    @BindView(R.id.sb1)
    SeekBar sb1;
    @BindView(R.id.sb2)
    SeekBar sb2;
    @BindView(R.id.sb3)
    SeekBar sb3;
    @BindView(R.id.emBossView)
    EmBossView emBossView;
    @BindView(R.id.sb4)
    SeekBar sb4;
    @BindView(R.id.sb5)
    SeekBar sb5;
    @BindView(R.id.sb6)
    SeekBar sb6;
    private EmbossMaskFilter filter;
    private float[] direction;
    private float ambient;
    private float specular;
    private float blurRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emboss_mask_test);
        ButterKnife.bind(this);
        init();

        /**
         * direction表示x,y,z的三个方向，
         * ambient表示的是光的强度，范围为[0-1]，
         * specular是反射亮度的系数，
         * blurRadius是指模糊的半径，
         */

        //filter = new EmbossMaskFilter(new float[]{1f, 1f, 1f}, 1f, 0.5f, 10f);
    }

    private void init() {
        direction = new float[3];
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                direction[0] = progress;
                emBossView.setFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                direction[1] = progress;
                emBossView.setFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                direction[2] = progress;
                emBossView.setFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ambient = progress / 100f;
                emBossView.setFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                specular = progress;
                emBossView.setFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blurRadius = progress;
                emBossView.setFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }






}
