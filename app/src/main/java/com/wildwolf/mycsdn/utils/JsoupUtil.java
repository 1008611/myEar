package com.wildwolf.mycsdn.utils;

import android.util.Log;


import com.wildwolf.mycsdn.constant.Api;
import com.wildwolf.mycsdn.data.EarData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/11/21.
 */
public class JsoupUtil {

    public static List<EarData> parseEar(String s) {
        Document document = Jsoup.parse(s);
//        Elements elements = document.select("div.pic");
        Elements elements = document.getElementsByClass("box");

        List<EarData> list = new ArrayList<>();
        EarData data;
        for (Element element : elements) {
            data = new EarData();

            data.setName(element.select("h2").text());
            data.setImgUrl(element.select("a").select("img").attr("src"));
            data.setReadCount(element.select("p").select("span").text());
            data.setArticleCount(element.select("p").text());
            data.setSubtype(element.select("div.entry").select("p").text());
            data.setHref(element.select("a").attr("href"));

            list.add(data);
        }

        return list;

    }
}


