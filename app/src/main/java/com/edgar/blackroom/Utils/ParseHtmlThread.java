package com.edgar.blackroom.Utils;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Edgar on 2018/4/9.
 */

public class ParseHtmlThread extends Thread {

    private static final int GET_DATA_SUCCESS = 101;
    private static final int GET_DATA_FAILED = 102;
    private DataFromDocument dataFromDocument;

    private String queryURL;
    private Handler jsoupHandler;

    private String resultString;

    public ParseHtmlThread(String queryURL, Handler jsoupHandler) {
        this.queryURL = queryURL;
        this.jsoupHandler = jsoupHandler;
    }

    @Override
    public void run() {
        super.run();
        Message message = Message.obtain();
        try {
            Document document = Jsoup.connect(queryURL).get();
            dataFromDocument.setData(document);

            message.what = GET_DATA_SUCCESS;
            jsoupHandler.sendMessage(message);
            resultString = "Get Data Success";

        } catch (IOException e) {
            e.printStackTrace();
            message.what = GET_DATA_FAILED;
            jsoupHandler.sendMessage(message);
            resultString = "Failed: IOException";

        } catch (NullPointerException npe) {
            npe.printStackTrace();
            message.what = GET_DATA_FAILED;
            jsoupHandler.sendMessage(message);
            resultString = "Failed: NullPointerException";
        }
    }

    public void setDataFromDocument(DataFromDocument dataFromDocument) {
        this.dataFromDocument = dataFromDocument;
    }

    public String getResultString() {
        return resultString;
    }

    public interface DataFromDocument {
        void setData(Document document);
    }
}
