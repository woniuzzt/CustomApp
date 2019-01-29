package cardiochina.com.customapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import cardiochina.com.customapp.customview.GGKView;

/**
 * Created by yx on 2019/1/21.
 * Description
 */
public class MainActivity extends AppCompatActivity {
    private Button button;
    private GGKView ggkView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        ggkView = findViewById(R.id.GGKView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ggkView.setDefault();
            }
        });
    }
}
