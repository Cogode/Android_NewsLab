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
    public static void refreshNewsDigest(String title, ArrayList<NewsDigest> newsDigestList,
                                         RecyclerView recyclerView, NewsDigestRecyclerViewAdapter adapter) {
        String key = "73796e33c14d95cebfe65179933f9052";
        String num = "50";
        NewsService service = ServiceCreator.create(NewsService.class);
        Call<NewsDigestInformation> callTemp = null;
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
        if(callTemp != null) {
            callTemp.enqueue(new Callback<NewsDigestInformation>() {
                @Override
                public void onResponse(Call<NewsDigestInformation> call, Response<NewsDigestInformation> response) {
                    NewsDigestInformation information = response.body();
                    if(information != null) {
                        if(information.getCode().equals("200")) {
                            List<NewsDigest> newslist = information.getNewslist();
                            newsDigestList.clear();
                            for(int i = 0; i < newslist.size(); i ++) {
                                NewsDigest temp = newslist.get(i);
                                if(temp.getSource() == null || temp.getSource().equals(""))
                                    temp.setSource("网易热点");
                                if(temp.getImgsrc() != null && ! temp.getImgsrc().equals("")) {
                                    if(! temp.getImgsrc().split(":")[0].equals("http") && ! temp.getImgsrc().split(":")[0].equals("https"))
                                        temp.setImgsrc("https:" + temp.getImgsrc());
                                }
                                if(temp.getPicUrl() != null && ! temp.getPicUrl().equals("")) {
                                    if(! temp.getPicUrl().split(":")[0].equals("http") && ! temp.getPicUrl().split(":")[0].equals("https"))
                                        temp.setPicUrl("https:" + temp.getPicUrl());
                                }
                                if(temp.getUrl() != null && ! temp.getUrl().equals("")) {
                                    if(! temp.getUrl().split(":")[0].equals("http") && ! temp.getUrl().split(":")[0].equals("https"))
                                        temp.setUrl("https:" + temp.getUrl());
                                }
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
                            if(newsContent.getPicture() != null && ! newsContent.getPicture().equals("")) {
                                if(! newsContent.getPicture().split(":")[0].equals("http") && ! newsContent.getPicture().split(":")[0].equals("https"))
                                    newsContent.setPicture("https:" + newsContent.getPicture());
                            }
                            newsContent.setContent(NewsUtil.getMainContent(newsContent.getContent()));
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

    public static String getMainContent(String content) {
        boolean isParagraph = false;
        boolean isStartParagraph = true;
        boolean startFiltering = false;
        for(int i = 0; i < content.length(); i ++) {
            char current = content.charAt(i);
            if(current == 'p') {
                int sign = (i + 2) < content.length() ? (i + 2) : i;
                if(content.charAt(i - 1) == '<' && content.charAt(sign) != '<') {
                    isParagraph = true;
                }
            }
            else if(current == '>') {
                if(isParagraph) {
                    if(isStartParagraph)
                        isStartParagraph = false;
                    else
                        content = content.substring(0, i + 1) + "\n\n" + content.substring(i + 1);
                    isParagraph = false;
                }
            }
        }
        for(int i = 0; i < content.length(); i ++) {
            char current = content.charAt(i);
            if(current == '<') {
                startFiltering = true;
                content = content.substring(0, i) + content.substring(i + 1);
                i --;
            }
            else if(current == '>') {
                startFiltering = false;
                content = content.substring(0, i) + content.substring(i + 1);
                i --;
            }
            else {
                if(startFiltering) {
                    content = content.substring(0, i) + content.substring(i + 1);
                    i --;
                }
            }
        }
        return content;
    }
}
