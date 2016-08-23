package com.widevision.numeral_concidepieces.util;

import android.content.Context;
import android.widget.Toast;

import org.cocos2d.types.CGSize;

public final class CommonUtil
{
    static
    {
        basePath = "images/hdpi/";
    }

    private static String     basePath;

    private static CommonUtil commonUtil        = null;
    public static String      IMG_PATH_XHIGH_RES = "images/drawable-xxhdpi/";
//    public static String      IMG_PATH_TVHIGH_RES = "images/tvdpi/";
    public static String      IMG_PATH_HIGH_RES = "images/drawable-xhdpi/";
    public static String      IMG_PATH_MED_RES  = "images/drawable-hdpi/";
    public static String      IMG_PATH_LOW_RES  = "images/drawable-mdpi/";

    private CommonUtil()
    {
    }

    public static CommonUtil getInstance()
    {
        if ( null == commonUtil )
        {
            commonUtil = new CommonUtil();
        }

        return commonUtil;
    }

    public static void showToast( String message, Context context )
    {
        Toast pieceToast = Toast.makeText( context, message, Toast.LENGTH_SHORT );
        pieceToast.show();
    }

    public static void showWorkInProgressToast( Context context )
    {
        showToast( "We're working to improve this. This feature will be available in future versions of the product",
                context );
    }

    /**
     * This is to identify the image directories. cocos2d doesn't detect res
     * directory. We need to hold all images used in game in 'assets' folder.
     * Based on screen size we need to choose either assets/hdpi, assets/mdpi
     * etc
     * 
     * @param paramCGSize
     * @return
     */
    public static String getBasePath( CGSize paramCGSize )
    {

        if ( ( paramCGSize.width <= 480.0F ))
        {
            return IMG_PATH_LOW_RES;
        }
        
        if ( ( paramCGSize.width <= 960.0F ))
        {
        	return IMG_PATH_MED_RES;
        }
        else if( ( paramCGSize.width <= 1280.0F ))
        {
            return  IMG_PATH_HIGH_RES ;
        }
       /* else if( ( paramCGSize.width <= 600.0F ) && ( paramCGSize.width <= 800.0F ) )
        {
            return  IMG_PATH_XHIGH_RES;
        }*/
        else 
        {
        	 return  IMG_PATH_XHIGH_RES;
		}
    }
    public static String getBasePath()
    {
        return basePath;
    }

    public static void setBasePath( String basePath )
    {
        CommonUtil.basePath = basePath;
    }

    public static String getImagePath( String imageKey, CGSize winSize )
    {
        return getBasePath(winSize) + imageKey;
    }
    

}
