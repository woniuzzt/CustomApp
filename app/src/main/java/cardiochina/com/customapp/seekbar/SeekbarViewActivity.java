package cardiochina.com.customapp.seekbar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import cardiochina.com.customapp.R;

/**
 * Created by yx on 2019/1/25.
 * Description
 */
public class SeekbarViewActivity extends AppCompatActivity {
    private TextView tv;
    private SeekBar sb, sb_douyu;
    private DouYuView dyv_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seekbar_scroll_activity);

        /**************************************************
         * seekbar
         */
        tv = findViewById(R.id.tv);
        sb = findViewById(R.id.sb);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {    //seekbar进度条变化回调
                if (seekBar.getProgress() == seekBar.getMax()) {
                    tv.setVisibility(View.VISIBLE);
                    tv.setTextColor(Color.WHITE);
                    tv.setText("完成验证");
                } else {
                    tv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //seekbar开始触摸回调

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  //seekbar停止触摸回调
                if (seekBar.getProgress() != seekBar.getMax()) {
                    seekBar.setProgress(0);
                    tv.setVisibility(View.VISIBLE);
                    tv.setTextColor(Color.GRAY);
                    tv.setText("请按住滑块，拖动到最右边");
                }
            }
        });

        /******************************************************
         * 仿斗鱼滑动验证view
         */

        sb_douyu = findViewById(R.id.sb_douyu);
        dyv_view = findViewById(R.id.dyv_view);
        sb_douyu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dyv_view.setUintMoveDistance(dyv_view.getAverageDistance(sb_douyu.getMax()) * progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dyv_view.testPuzzle();
            }
        });
        dyv_view.setPuzzleListener(new DouYuView.onPuzzleListener() {
            @Override
            public void success() {
                Toast.makeText(SeekbarViewActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                sb_douyu.setProgress(0);
                dyv_view.reSet();
            }

            @Override
            public void fail() {
                Toast.makeText(SeekbarViewActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                sb_douyu.setProgress(0);
            }
        });
        Bitmap bitmap = dyv_view.getUintBp();
    }
}
