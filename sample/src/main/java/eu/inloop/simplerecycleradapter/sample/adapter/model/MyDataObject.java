package eu.inloop.simplerecycleradapter.sample.adapter.model;

import java.util.concurrent.atomic.AtomicInteger;

public class MyDataObject {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger();

    private final String mTitle;
    private final String mText;
    private final int mId;

    public MyDataObject(String title, String text) {
        this.mTitle = title;
        this.mText = text;
        this.mId = sNextGeneratedId.getAndIncrement();
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }
}