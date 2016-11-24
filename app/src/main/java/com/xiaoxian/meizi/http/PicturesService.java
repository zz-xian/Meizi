package com.xiaoxian.meizi.http;

import com.xiaoxian.meizi.data.bean.PicturesBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/19.
 */

public interface PicturesService {
    /**
     * 与OkHttp不同的是，Retrofit根据API新建一个接口，返回Call对象
     * API数据：http://gank.io/api/data/数据类型/请求个数/第几页
     * 使用Java注解描述API
     * @GET/@POST...请求方式
     * @Path/@Query/@QueryMap 参数注解（后面两个用键值对形式）
     */
    @GET("api/data/{type}/{count}/{page}")
    //此处返回的是Observable<T>，而非常规Call<T>，配合RxJava使用
    Observable<PicturesBean> getPictures(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );
}
