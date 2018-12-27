package com.weenta.a21demorecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.weenta.a21demorecyclerview.rec.ForGridViewActivity;
import com.weenta.a21demorecyclerview.rec.ForListViewActivity;
import com.weenta.a21demorecyclerview.rec.ForWaterfallActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt_list,txt_grid,txt_waterfall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initEvent() {
        txt_list.setOnClickListener(this);
        txt_grid.setOnClickListener(this);
        txt_waterfall.setOnClickListener(this);
    }

    private void initView() {
        txt_list = findViewById(R.id.txt_for_list);
        txt_grid = findViewById(R.id.txt_for_grid);
        txt_waterfall = findViewById(R.id.txt_for_waterfall);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_for_list:
                startActivity(new Intent(this, ForListViewActivity.class));
                break;
            case R.id.txt_for_grid:
                startActivity(new Intent(this, ForGridViewActivity.class));
                break;
            case R.id.txt_for_waterfall:
                startActivity(new Intent(this, ForWaterfallActivity.class));
                break;
        }
    }
}
