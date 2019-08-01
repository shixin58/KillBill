package com.bride.baselib.net;

/**
 * <p>Created by shixin on 2019/4/2.
 */
public interface Urls {
    interface Images {
        String LOGO = "http://goo.gl/gEgYUd";
        String LOGO_REDIRECT = "https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300";
        String BEAUTY = "http://img1.3lian.com/2015/a2/204/d/15.jpg";
        String LADY = "https://www.nanrenwo.net/uploads/171009/8478-1G009160016215.jpg";
    }

    String JUHE_HOST = "http://apis.juhe.cn";
    String JUHE_MOBILE = JUHE_HOST + "/mobile/get";
    String JUHE_KEY = "9a4329bdf84fa69d193ce601c22b949d";

    String POSTMAN_HOST = "https://postman-echo.com";
    String POSTMAN_GET = POSTMAN_HOST+"/get";
    String POSTMAN_POST = POSTMAN_HOST+"/post";

    String RONG_GET_TOKEN = "http://api-cn.ronghub.com/user/getToken.json";
    String RONG_APP_KEY = "bmdehs6pbg9es";
    String RONG_APP_SECRET = "5RDjPuah6stFeY";
}
