package net.sourceforge.UI.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chain.wallet.spd.R;

import net.sourceforge.base.ActivityBase;

import butterknife.ButterKnife;

/**
 * Created by terry.c on 06/03/2018.
 */

public class ActivitySample extends ActivityBase {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sample);
        ButterKnife.bind(this);
        initTitle();
        initViews();
    }

    private void initTitle() {
    }

    private void initViews() {
    }

}
