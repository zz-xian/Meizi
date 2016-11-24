package com.xiaoxian.meizi.data.source;

import com.xiaoxian.meizi.data.source.local.LocalPicturesDataSource;
import com.xiaoxian.meizi.data.source.remote.RemotePicturesDataSource;

/**
 * Created by Administrator on 2016/10/21.
 * 定义一个图片存储库，用于存储从本地/远程服务器获得图片
 */

public class PicturesRepository implements PicturesDataSource {
    private LocalPicturesDataSource mLocalPicturesDataSource;
    private RemotePicturesDataSource mRemotePicturesDataSource;

    public PicturesRepository() {
        mLocalPicturesDataSource = new LocalPicturesDataSource();
        mRemotePicturesDataSource = new RemotePicturesDataSource();
    }

    @Override
    public void getPictures(int page, int size, LoadPicturesCallback callback) {
        mRemotePicturesDataSource.getPictures(page, size, callback);
    }

    @Override
    public void getPicture(LoadPicturesCallback callback) {
        mRemotePicturesDataSource.getPicture(callback);
    }
}
