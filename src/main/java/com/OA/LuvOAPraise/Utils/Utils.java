package com.OA.LuvOAPraise.Utils;

import java.io.IOException;
import java.net.URLDecoder;

public class Utils {

    public static String GetDecodeString(String resData)
    {
        String strResult = null;
        try{
            strResult = URLDecoder.decode(resData,"UTF-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return strResult;
    }


    public static String GetDecodeString(String resData, String strFormat)
    {
        String strResult = null;

        try
        {
            strResult = URLDecoder.decode(resData, strFormat);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return strResult;
    }
}
