package com.nutcunofficial.nutcapps.util;

import org.jsoup.nodes.Document;

import java.util.HashMap;

public class hiddenValueParser {

    private static final String TAG = hiddenValueParser.class.getSimpleName();

    public static final String[] keys = {
            "__VIEWSTATEGENERATOR","__EVENTVALIDATION","__EVENTTARGET","__VIEWSTATE","__EVENTARGUMENT"
    };

    public static HashMap<String,String> hiddenValueParser(Document doc) {
        HashMap<String,String> result = new HashMap<>();
        for(String key : keys){
            try{
                String value = JsoupFinder.getElementById(doc,key).val();
                result.put(key,value);
            }
            catch(NullPointerException e){
                result.put(key,"");
            }
        }
        return result;
    }

}
