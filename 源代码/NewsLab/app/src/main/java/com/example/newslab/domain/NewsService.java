package com.example.newslab.domain;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    @GET("bulletin/index")
    Call<NewsDigestInformation> getEverydayNewsDigest(@Query("key") String key);

    @GET("guonei/index")
    Call<NewsDigestInformation> getNationalNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("world/index")
    Call<NewsDigestInformation> getInternationalNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("internet/index")
    Call<NewsDigestInformation> getInternetNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("military/index")
    Call<NewsDigestInformation> getMilitaryNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("social/index")
    Call<NewsDigestInformation> getSocialNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("huabian/index")
    Call<NewsDigestInformation> getEntertainmentNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("tiyu/index")
    Call<NewsDigestInformation> getSportsNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("esports/index")
    Call<NewsDigestInformation> getEsportsNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("health/index")
    Call<NewsDigestInformation> getHealthNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("travel/index")
    Call<NewsDigestInformation> getTravelNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("keji/index")
    Call<NewsDigestInformation> getTechnologyNewsDigest(@Query("key") String key, @Query("num") String num);

    @GET("htmltext/index")
    Call<NewsContentInformation> getNewsContent(@Query("key") String key, @Query("url") String url);
}
