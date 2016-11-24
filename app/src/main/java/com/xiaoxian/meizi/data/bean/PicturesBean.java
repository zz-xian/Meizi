package com.xiaoxian.meizi.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */

public class PicturesBean {
    private boolean error;
    private List<ResultsEntity> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity implements Parcelable {
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        /**
         * 提供一个无参构造方法——javabean标准写法
         */
        public ResultsEntity(){

        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        @Override
        public int describeContents() {
            //直接返回0
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            //写出所有字段
            dest.writeString(this._id);
            dest.writeString(this.createdAt);
            dest.writeString(this.desc);
            dest.writeString(this.publishedAt);
            dest.writeString(this.source);
            dest.writeString(this.type);
            dest.writeString(this.url);
            //1：指代true；0：指代false
            dest.writeByte(this.used ? (byte) 1 : (byte) 0);
            dest.writeString(this.who);
        }

        /**
         *  利用Parcelable.Creator<泛型类>提供一个CREATOR常量
         *  重写createFromParcel()、newArray()
         *  调用readXxx()读取所有字段（顺序同写入时一样）
         */
        public static final Parcelable.Creator<ResultsEntity> CREATOR = new Parcelable.Creator<ResultsEntity>() {
            @Override
            public ResultsEntity createFromParcel(Parcel source) {
                ResultsEntity resultsEntity = new ResultsEntity();
                resultsEntity._id = source.readString();
                resultsEntity.createdAt = source.readString();
                resultsEntity.desc = source.readString();
                resultsEntity.publishedAt = source.readString();
                resultsEntity.source = source.readString();
                resultsEntity.type = source.readString();
                resultsEntity.url = source.readString();
                resultsEntity.used = source.readByte() != 0;
                resultsEntity.who = source.readString();
                return resultsEntity;
            }

            @Override
            public ResultsEntity[] newArray(int size) {
                return new ResultsEntity[size];
            }
        };
    }
}
