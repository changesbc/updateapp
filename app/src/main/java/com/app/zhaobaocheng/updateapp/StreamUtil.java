package com.app.zhaobaocheng.updateapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * Created by ZhaoBaocheng on 2016/10/10.
 */
public class StreamUtil {

    public static String parserStreamUtil(InputStream in) {
        //读取流
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        //写入流
        StringWriter sw=new StringWriter();
        String str=null;
        try {
            while ((str=br.readLine())!=null){
                sw.write(str);
            }

            //关闭流
            sw.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }
}
