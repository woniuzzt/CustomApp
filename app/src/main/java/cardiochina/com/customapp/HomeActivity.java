package cardiochina.com.customapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import cardiochina.com.customapp.customedittext.EditTextActivity;
import cardiochina.com.customapp.seekbar.SeekbarViewActivity;

/**
 * Created by yx on 2019/1/21.
 * Description
 */
public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void ggk(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void seekbar(View view) {
        Intent intent = new Intent(this, SeekbarViewActivity.class);
        startActivity(intent);
    }

    public void edittext(View v) {
        Intent intent = new Intent(this, EditTextActivity.class);
        startActivity(intent);
    }
}
