package com.nutcunofficial.nutcapps.util;

import androidx.annotation.Nullable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupFinder {

    @Nullable
    public static Element getElementByClass(Document doc, String className) {
        Elements elements = doc.getElementsByClass(className);
        if (elements != null && elements.size() > 0) {
            return elements.get(0);
        } else {
            return null;
        }
    }

    @Nullable
    public static Element getElementByClass(Element element, String className) {
        Elements elements = element.getElementsByClass(className);
        if (elements != null && elements.size() > 0) {
            return elements.get(0);
        } else {
            return null;
        }
    }

    @Nullable
    public static Element getElementById(Document doc,String key){
        Element elements = doc.getElementById(key);
        if(elements!=null)
            return elements;
        else
            return null;
    }

}
