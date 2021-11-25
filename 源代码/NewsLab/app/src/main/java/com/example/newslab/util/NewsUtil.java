package com.example.newslab.util;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newslab.adapter.NewsDigestRecyclerViewAdapter;
import com.example.newslab.domain.NewsDigest;
import com.example.newslab.domain.NewsDigestInformation;
import com.example.newslab.domain.NewsService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsUtil {
    public static boolean refreshNewsDigest(String title, ArrayList<NewsDigest> newsDigestList, RecyclerView recyclerView, NewsDigestRecyclerViewAdapter adapter) {
        String key = "73796e33c14d95cebfe65179933f9052";
        String num = "50";
        NewsService service = NewsServiceCreator.create(NewsService.class);
        Call<NewsDigestInformation> callTemp;
        if(title.equals("每日"))
            callTemp = service.getEverydayNewsDigest(key);
        else if(title.equals("国内"))
            callTemp = service.getNationalNewsDigest(key, num);
        else if(title.equals("国际"))
            callTemp = service.getInternationalNewsDigest(key, num);
        else if(title.equals("互联网"))
            callTemp = service.getInternetNewsDigest(key, num);
        else if(title.equals("军事"))
            callTemp = service.getMilitaryNewsDigest(key, num);
        else if(title.equals("社会"))
            callTemp = service.getSocialNewsDigest(key, num);
        else if(title.equals("娱乐"))
            callTemp = service.getEntertainmentNewsDigest(key, num);
        else if(title.equals("体育"))
            callTemp = service.getSportsNewsDigest(key, num);
        else if(title.equals("电竞"))
            callTemp = service.getEsportsNewsDigest(key, num);
        else if(title.equals("健康"))
            callTemp = service.getHealthNewsDigest(key, num);
        else if(title.equals("旅游"))
            callTemp = service.getTravelNewsDigest(key, num);
        else if(title.equals("科技"))
            callTemp = service.getTechnologyNewsDigest(key, num);
        else
            return false;
        callTemp.enqueue(new Callback<NewsDigestInformation>() {
            @Override
            public void onResponse(Call<NewsDigestInformation> call, Response<NewsDigestInformation> response) {
                NewsDigestInformation information = response.body();
                if(information != null) {
                    if(information.getCode().equals("200")) {
                        List<NewsDigest> newslist = information.getNewslist();
                        for(int i = 0; i < newslist.size(); i ++) {
                            if(newslist.get(i).getSource() == null || newslist.get(i).getSource().equals(""))
                                newslist.get(i).setSource("网易热点");
                            newsDigestList.add(newslist.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsDigestInformation> call, Throwable t) {
                Log.i("TAG", "failure");
            }
        });
        return true;
    }
}
