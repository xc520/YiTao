package com.example.thinkpad.yitao.me.perinfo;

import com.example.thinkpad.yitao.monder.CachePreferences;
import com.example.thinkpad.yitao.monder.User;
import com.example.thinkpad.yitao.monder.UserResult;
import com.example.thinkpad.yitao.network.EasyShopClient;
import com.example.thinkpad.yitao.network.UICAllback;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created by cjw on 2016/11/23.
 */

public class Personprester extends MvpNullObjectBasePresenter<PersonView> {
    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null){
            call.cancel();
        }
    }

    //上传头像
    public void updataAvatar(File file){
        getView().showPrb();
        call= EasyShopClient.getInstance().uploadAvatar(file);
        call.enqueue(new UICAllback() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                getView().hidePrb();
                UserResult userResult = new Gson().fromJson(body,UserResult.class);
                if (userResult == null){
                    getView().showMsg("未知错误");
                }else if (userResult.getCode() != 1){
                    getView().showMsg(userResult.getMessage());
                    return;
                }

                User user = userResult.getData();
                CachePreferences.setUser(user);
                //调用activity里的头像更新方法，把url传过去
             getView().updataAvatar(userResult.getData().getHead_image());

                // TODO: 2016/11/24 0024 环信更新用户头像
            }
        });
    }
}
