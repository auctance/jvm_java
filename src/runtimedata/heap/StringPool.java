package runtimedata.heap;

import runtimedata.Zobject;

import java.util.HashMap;

// 串池
// 这里用一个hashmap来模拟串池 key为class文件中读取到的字符串 value为定义的zobject
public class StringPool {
    public static HashMap<String, Zobject> internedStrings = new HashMap<>();
    public static HashMap<Zobject, String> realInternedStrings = new HashMap<>();

    // 如果串池中存在就直接取出
    public static Zobject jString(ZclassLoader loader, String str){
        if (internedStrings.containsKey(str)){
            return internedStrings.get(str);
        }

        char[] chars = str.toCharArray();
        Zobject jChars = new Zobject(loader.loadClass("[C"), chars,null);
        Zobject jStr = loader.loadClass("java/lang/String").newObject();

        jStr.setRefVar("value","[C",jChars);
        internedStrings.put(str,jStr);
        realInternedStrings.put(jStr,str);
        return jStr;
    }

    // 调用该方法 必定是从上面的常量池中获取来相同的字符串 返回其在jvm中的zobject
    public static String realString(Zobject jStr){
        if (realInternedStrings.containsKey(jStr)){
            return realInternedStrings.get(jStr);
        }
        Zobject ref = jStr.getRefVar("value","[C");
        char[] chars = ref.getChars();
        String realStr = new String(chars);
        realInternedStrings.put(jStr,realStr);
        return realStr;
    }
}
