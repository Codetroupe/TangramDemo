package com.codetroupe.tangramdemo.example.data;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.codetroupe.tangramdemo.R;
import com.codetroupe.tangramdemo.example.support.SampleScrollSupport;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;

/**
 * Created by villadora on 15/8/24.
 */
public class TestView extends FrameLayout implements ITangramViewLifeCycle, SampleScrollSupport.IScrollListener {
    private TextView textView;
    private BaseCell cell;

    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item, this);
        textView = (TextView) findViewById(R.id.title);
    }

    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
        this.cell = cell;
        if (cell.serviceManager != null) {
            SampleScrollSupport scrollSupport = cell.serviceManager.getService(SampleScrollSupport.class);
            scrollSupport.register(this);
        }
    }

    @Override
    public void postBindView(BaseCell cell) {
        int pos = cell.pos;
        String parent = "";
        if (cell.parent != null) {
            parent = cell.parent.getClass().getSimpleName();
        }
        textView.setText(
                cell.id + " pos: " + pos + " " + parent + " " + cell
                        .optParam("msg"));

        if (pos > 57) {
            textView.setBackgroundColor(0x66cccf00 + (pos - 50) * 128);
        } else if (pos % 2 == 0) {
            textView.setBackgroundColor(0xaaaaff55);
        } else {
            textView.setBackgroundColor(0xcceeeeee);
        }
    }

    @Override
    public void postUnBindView(BaseCell cell) {
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        Log.i("TestView", "onScrollStateChanged: ");
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        Log.i("TestView", "onScrolled: ");
    }
}
