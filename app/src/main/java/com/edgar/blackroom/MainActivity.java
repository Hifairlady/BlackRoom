package com.edgar.blackroom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int GET_DATA_SUCCESS = 101;
    private static final int GET_DATA_FAILED = 102;

    private static final String TAG = MainActivity.class.getSimpleName() + "================";

    private static final String BLACK_ROOM_URL = "https://www.bilibili.com/blackroom/ban";
    private String errorString = "";

    private String btnMoreUrl;

    private LinearLayout btnMore;
    private RecyclerView recyclerView;
    private ViewPagerAdapter adapter;
    private ArrayList<BulletinItem> bulletinItems = new ArrayList<>();
    private Handler getBulletinsHandler;
    private ParseHtmlThread parseHtmlThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();

        getBulletinsHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                    case GET_DATA_SUCCESS:
                        Snackbar.make(recyclerView, parseHtmlThread.getResultString(),
                                Snackbar.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        break;

                    case GET_DATA_FAILED:
                        Snackbar.make(recyclerView, parseHtmlThread.getResultString(),
                                Snackbar.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        };

        parseHtmlThread = new ParseHtmlThread(BLACK_ROOM_URL, getBulletinsHandler);
        parseHtmlThread.setDataFromDocument(dataFromDocument);
        parseHtmlThread.start();

    }

    private ParseHtmlThread.DataFromDocument dataFromDocument = new ParseHtmlThread.DataFromDocument() {
        @Override
        public void setData(Document document) {
            Element moreElement = document.selectFirst("a.more");
            btnMoreUrl = moreElement.absUrl("href");
            Log.d(TAG, "setData: " + btnMoreUrl);
            Element noticeBoxElement = document.selectFirst("div.notice-box");
            Elements noticeChildrenElements = noticeBoxElement.children();

            String titleString;
            String urlString;
            String dateString;
            boolean isNew;

            for (Element noticeChildElement : noticeChildrenElements) {
                titleString = noticeChildElement.getElementsByClass("title").first().text();
                urlString = noticeChildElement.getElementsByClass("notice-list clearfix")
                        .first().absUrl("href");
                dateString = noticeChildElement.getElementsByClass("time").first().text();
                isNew = (noticeChildElement.getElementsByClass("new").first() == null);
                BulletinItem bulletinItem = new BulletinItem(titleString, dateString,
                        isNew, urlString);
                Log.d(TAG, "setData: " + bulletinItem.toString());
                bulletinItems.add(bulletinItem);
//                break;
            }
        }
    };

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
//            Snackbar.make(recyclerView, "Network Error!", Snackbar.LENGTH_SHORT).show();
        }
    };

}
