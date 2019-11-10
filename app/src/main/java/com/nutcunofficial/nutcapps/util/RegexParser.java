package com.nutcunofficial.nutcapps.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegexParser {

    public static Pattern CDATARegex = Pattern.compile("alert\\(.+\\)");
    public static Pattern ActiveLinkMatcher = Pattern.compile("http(s|):\\/\\/ais\\.nutc\\.edu\\.tw\\/student\\/Verifyv2\\.aspx\\?Ticket=[A-Za-z0-9]+");
    public static Pattern ImageUrlMatcher = Pattern.compile("\\/student\\/photos\\/.*.jpg");
    public static Pattern NoticeMatcher = Pattern.compile("return view\\([0-9]+\\)\\;");
    public static Pattern ClsJsonMatcher = Pattern.compile("var g_ClsTime = \\{.+\\}");

    public static String AlertSource(String html){
        Matcher matcher = CDATARegex.matcher(html);
        if(matcher!=null&&matcher.find()){
            String StrTrim = matcher.group(0);
            StrTrim = StrTrim.replace("alert","");
            StrTrim = StrTrim.substring(
                    StrTrim.indexOf("'") + 1,
                    StrTrim.indexOf(")") - 1
            );
            return StrTrim;
        }
        else
            return null;
    }

    public static String urlFilter(String HTML){
        Matcher matcher = ActiveLinkMatcher.matcher(HTML);
        if(matcher!=null && matcher.find()){
            return matcher.group(0);
        }
        else
            return null;
    }

    public static String backgroundGetter(String imgSource){
        Matcher matcher = ImageUrlMatcher.matcher(imgSource);
        if(matcher!=null && matcher.find()){
            return matcher.group(0);
        }
        else
            return null;
    }

    public static int NoticeMatcher(String aSource){
        Matcher matcher = NoticeMatcher.matcher(aSource);
        if(matcher!=null&&matcher.find()){
            String elem = matcher.group(0);
            elem = elem.substring(elem.indexOf('(') + 1,elem.indexOf(')'));
            return Integer.valueOf(elem);
        }
        return 0;
    }

    public static HashMap<String, String> JsonParser(String html){
        Matcher matcher = ClsJsonMatcher.matcher(html);
        if(matcher!=null&&matcher.find()){
            String JsonVariable = matcher.group(0);
            JsonVariable = JsonVariable.substring(
                JsonVariable.indexOf("{"),
                JsonVariable.indexOf("}") + 1
            );
            Gson gson = new Gson();
            Type listType = new TypeToken<HashMap<String, String>>(){}.getType();
            return gson.fromJson(JsonVariable, listType);
        }
        return null;
    }
}
