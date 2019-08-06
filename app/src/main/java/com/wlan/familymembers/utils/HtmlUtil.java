package com.wlan.familymembers.utils;

import com.rwq.framworkapp.net.HttpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 类作用：富文本内容修复
 * Created by Administrator on 2018/10/9.
 */

public class HtmlUtil {

    /**
     * 修复图片路径
     *
     * @param html html 内容
     * @param url  baseUrl eg. http:localhost:8080
     * @return html
     */
    public static String imgFix(String html, String url) {
        Document doc = Jsoup.parse(html);
        Elements imgs = doc.select("img");
        for (Element img : imgs) {
            String src = img.attr("src");
            String srcFix = url + src;
            img.attr("src",srcFix);
        }
        return doc.toString();
    }

    /**
     * 添加html头尾
     * @param htmlContent 后台富文本编辑器内容
     * @return
     */
    public static String htmlContentFix( String htmlContent){
        String head = "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  </head>\n" +
                "  <body>";

        String tail = "</body>\n" +
                "</html>";

        return head + htmlContent + tail;
    }

    /**
     * 处理后台富文本内容 <br />
     * 1. img 路径
     * 2. html 头尾添加
     * @param htmlContent 后台富文本数据【已转码为：<p>hello</p>】
     * @return
     */
    public static String fixEditorHtml( String htmlContent){
        return htmlContentFix(imgFix(htmlContent,HttpUtils.BASE_URL));
    }
}
