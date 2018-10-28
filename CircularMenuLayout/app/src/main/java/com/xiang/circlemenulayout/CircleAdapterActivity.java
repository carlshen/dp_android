package com.xiang.circlemenulayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CircleAdapterActivity extends AppCompatActivity {
    private String[] mItemTexts = new String[]{"安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡"};
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal};

    private List<MenuItemBean> menuItemBeanList = new ArrayList<>();
    CircleAdapterMenuLayout circleAdapterMenuLayout;
    CircleMenuAdapter circleMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_adapter_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();
        circleAdapterMenuLayout = (CircleAdapterMenuLayout) findViewById(R.id.id_circle_adaptor_menu);
        circleMenuAdapter = new CircleMenuAdapter(menuItemBeanList);
        circleAdapterMenuLayout.setAdapter(circleMenuAdapter);
        circleAdapterMenuLayout.setOnItemClickListener(new CircleAdapterMenuLayout.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                Toast.makeText(CircleAdapterActivity.this, mItemTexts[position], Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initData() {
        if (menuItemBeanList == null) {
            menuItemBeanList = new ArrayList<>();
        }
        for (int i = 0; i < 6; i++) {
            menuItemBeanList.add(new MenuItemBean(mItemTexts[i], mItemImgs[i]));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
