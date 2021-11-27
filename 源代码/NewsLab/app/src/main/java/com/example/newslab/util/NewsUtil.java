package com.example.newslab.util;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newslab.adapter.NewsDigestRecyclerViewAdapter;
import com.example.newslab.domain.NewsContent;
import com.example.newslab.domain.NewsContentInformation;
import com.example.newslab.domain.NewsDigest;
import com.example.newslab.domain.NewsDigestInformation;
import com.example.newslab.domain.NewsService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsUtil {
    public static void refreshNewsDigest(String title, ArrayList<NewsDigest> newsDigestList, RecyclerView recyclerView, NewsDigestRecyclerViewAdapter adapter) {
        String key = "73796e33c14d95cebfe65179933f9052";
        String num = "50";
        NewsService service = ServiceCreator.create(NewsService.class);
        Call<NewsDigestInformation> callTemp = null;
        /*if(title.equals("每日"))
            callTemp = service.getEverydayNewsDigest(key);
        else if(title.equals("国内"))
            callTemp = service.getNationalNewsDigest(key, num);
        else if(title.equals("国际"))
            callTemp = service.getInternationalNewsDigest(key, num);
        else*/ if(title.equals("互联网"))
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
        if(callTemp != null) {
            callTemp.enqueue(new Callback<NewsDigestInformation>() {
                @Override
                public void onResponse(Call<NewsDigestInformation> call, Response<NewsDigestInformation> response) {
                    NewsDigestInformation information = response.body();
                    if(information != null) {
                        if(information.getCode().equals("200")) {
                            List<NewsDigest> newslist = information.getNewslist();
                            for(int i = 0; i < newslist.size(); i++) {
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
        }
    }

    public static void refreshNewsContent(TextView titleTextView, TextView sourceTextView, TextView timeTextView,
                                          ImageView imageView, TextView contentTextView, NewsDigest newsDigest) {
        String key = "73796e33c14d95cebfe65179933f9052";
        String url = newsDigest.getUrl();
        if(url != null && ! url.equals("")) {
            ServiceCreator.create(NewsService.class).getNewsContent(key, url).enqueue(new Callback<NewsContentInformation>() {
                @Override
                public void onResponse(Call<NewsContentInformation> call, Response<NewsContentInformation> response) {
                    NewsContentInformation information = response.body();
                    if(information != null) {
                        if(information.getCode().equals("200")) {
                            List<NewsContent> newslist = information.getNewslist();
                            NewsContent newsContent = newslist.get(0);
                            if(newsContent.getPicture() != null)
                                Glide.with(titleTextView.getContext()).load(newsContent.getPicture()).into(imageView);
                            titleTextView.setText(newsContent.getTitle());
                            sourceTextView.setText(newsDigest.getSource());
                            timeTextView.setText(newsContent.getCtime());
                            contentTextView.setText(newsContent.getContent());
                        }
                    }
                }

                @Override
                public void onFailure(Call<NewsContentInformation> call, Throwable t) {
                    Log.i("TAG", "failure");
                }
            });
        }
        else {
            if(newsDigest.getImgsrc() != null && ! newsDigest.getImgsrc().equals(""))
                Glide.with(titleTextView.getContext()).load(newsDigest.getImgsrc()).into(imageView);
            else {
                if(newsDigest.getPicUrl() != null && ! newsDigest.getPicUrl().equals(""))
                    Glide.with(titleTextView.getContext()).load(newsDigest.getPicUrl()).into(imageView);
            }
            titleTextView.setText(newsDigest.getTitle());
            sourceTextView.setText(newsDigest.getSource());
            if(newsDigest.getCtime() != null && ! newsDigest.getCtime().equals(""))
                timeTextView.setText(newsDigest.getCtime());
            else {
                if(newsDigest.getMtime() != null && ! newsDigest.getMtime().equals(""))
                    timeTextView.setText(newsDigest.getMtime());
            }
            contentTextView.setText(newsDigest.getDigest());
        }
    }
}
