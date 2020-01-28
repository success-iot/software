package uk.mdx.success;

/**
 * Created by Josegines1 on 16/03/2018.
 */


import android.text.TextUtils;
import android.util.Log;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.List;

    public class cookies {

        /** The Constant URL_STRING. */

       // private final static String URL_STRING = "http://www.google.com";
             static CookieManager cookieManager=null;
             static String SID;
             static String userid;

        public static void initCokies(){

            if (cookieManager==null) {
                cookieManager = new CookieManager();
                CookieHandler.setDefault(cookieManager);

            }

        }

        public static void getCokiesFrom(){
            Log.v("TOCANDO COOKIES", "*************************************************************");
            CookieStore cookieStore = cookieManager.getCookieStore();
            List<HttpCookie> cookieList = cookieStore.getCookies();

            for (HttpCookie cookie : cookieList)
            {
                Log.v("COOOKIESSS: ", cookie.getName() +":"+ cookie.getValue());
                if(cookie.getName()=="SID"){
                    SID=cookie.getValue();
                }
                if(cookie.getName()=="userid"){
                    userid=cookie.getValue();
                }
            }

        }

        public static String getCookiesTo(){
                return TextUtils.join(";",  cookieManager.getCookieStore().getCookies());
        }

        public static Boolean isCookieInit(){
                return cookieManager!=null;
        }


    }

