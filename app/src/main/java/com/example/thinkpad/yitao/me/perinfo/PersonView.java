package com.example.thinkpad.yitao.me.perinfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public interface PersonView extends MvpView {

    void showPrb();

    void hidePrb();

    void showMsg(String msg);

    //用来更新头像
    void updataAvatar(String url);
}

