package com.edgar.blackroom.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edgar.blackroom.R;
import com.edgar.blackroom.Utils.ParseHtmlThread;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class BulletinDetailActivity extends AppCompatActivity {

    private static final String TAG = BulletinDetailActivity.class.getSimpleName() + "==============";

    private static final int GET_DATA_SUCCESS = 101;
    private static final int GET_DATA_FAILED = 102;

    private String appbarTitleString = "";
    private LinearLayout bulletinContainer;
    private TextView tvTitle, tvDate;
    private HtmlTextView htmlTextView;

    private Handler getContentHandler;
    private ParseHtmlThread parseThread;
    private String queryUrl;

    private String titleString, dateString;
    private String[] htmlCodeStrings;
    private String htmlCodeString;
    private ParseHtmlThread.DataFromDocument dataFromDocument = new ParseHtmlThread.DataFromDocument() {
        @Override
        public void setData(Document document) {

            Element titleElement = document.selectFirst("div.head-title");

            titleString = titleElement.getElementsByClass("title").first().text();
            dateString = titleElement.getElementsByClass("time").first().text();

            Element textContentElement = document.selectFirst("div.text-content");

            Elements paragraphElements = textContentElement.children();

            htmlCodeString = paragraphElements.html();
//            Log.d(TAG, "setData: " + htmlCodeString);

            htmlCodeStrings = new String[paragraphElements.size()];
            int index = 0;
            for (Element element : paragraphElements) {
                htmlCodeStrings[index++] = element.html();
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_bulletin_detail);

        initUIData();
    }

    @SuppressLint("HandlerLeak")
    private void initUIData() {

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        appbarTitleString = getIntent().getStringExtra("titleString");
        queryUrl = getIntent().getStringExtra("queryUrl");
        toolbar.setTitle(appbarTitleString);
        toolbar.setTitleTextAppearance(this, R.style.BulletinDetailTitle);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bulletinContainer = findViewById(R.id.official_bulletin_container);
        tvDate = findViewById(R.id.official_publish_date);
        tvTitle = findViewById(R.id.official_bulletin_title);
//        htmlTextView = (HtmlTextView)findViewById(R.id.my_html_text_view);
//        htmlTextView.setRemoveFromHtmlSpace(false);

        getContentHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                    case GET_DATA_SUCCESS:
                        tvTitle.setText(titleString);
                        tvDate.setText(dateString);

                        for (int i = 0; i < htmlCodeStrings.length; i++) {
                            HtmlTextView tvHtml = new HtmlTextView(getBaseContext());
                            HtmlHttpImageGetter imageGetter = new HtmlHttpImageGetter(tvHtml,
                                    null, true);
                            tvHtml.setTextSize(16.f);
                            tvHtml.setTextColor(getColor(R.color.primary_text));
                            tvHtml.setLinkTextColor(getColor(R.color.colorPrimary));
                            tvHtml.setHtml(htmlCodeStrings[i], imageGetter);
                            bulletinContainer.addView(tvHtml);
                        }

//                        HtmlHttpImageGetter imageGetter = new HtmlHttpImageGetter(htmlTextView,
//                                null, true);
//                        htmlTextView.setHtml(htmlCodeString, imageGetter);

                        Snackbar.make(bulletinContainer, parseThread.getResultString(),
                                Snackbar.LENGTH_SHORT).show();
                        break;

                    case GET_DATA_FAILED:
                        Snackbar.make(bulletinContainer, parseThread.getResultString(),
                                Snackbar.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        };

        parseThread = new ParseHtmlThread(queryUrl, getContentHandler);
        parseThread.setDataFromDocument(dataFromDocument);
        parseThread.start();


    }


}
