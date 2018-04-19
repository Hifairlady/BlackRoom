package com.edgar.blackroom;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int GET_DATA_SUCCESS = 101;
    private static final int GET_DATA_FAILED = 102;

    private static final String BLACK_ROOM_URL = "https://www.bilibili.com/blackroom/ban";

    private LinearLayout btnMore;
    private RecyclerView recyclerView;
    private ViewPagerAdapter adapter;
    private ArrayList<BulletinItem> bulletinItems = new ArrayList<>();
    private Handler getBulletinsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();
        setData();

        getBulletinsHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                    case GET_DATA_SUCCESS:
                        break;

                    case GET_DATA_FAILED:
                        break;

                    default:
                        break;
                }
            }
        };

    }

    private void setData() {
    }

    private void setUI() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        btnMore = (LinearLayout)findViewById(R.id.btn_bulletin_more);
        btnMore.setOnClickListener(mOnClickListener);

        recyclerView = (RecyclerView)findViewById(R.id.bulletin_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ViewPagerAdapter(MainActivity.this, bulletinItems);
        recyclerView.setAdapter(adapter);

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(recyclerView, "Network Error!", Snackbar.LENGTH_SHORT).show();
        }
    };

    private class GetBulletinsThread extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                Document document = Jsoup.connect(BLACK_ROOM_URL).get();
                String pageTitle = document.title();
                if (pageTitle == null) {
                    Snackbar.make(recyclerView, "Network Error!", Snackbar.LENGTH_SHORT);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
