package uk.mdx.success;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import 	java.net.HttpURLConnection;
import 	java.net.URL;
import java.security.SecureRandom;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import  org.json.JSONObject;
import  java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import android.util.Log;
import android.os.AsyncTask;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;

import android.widget.TextView;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Josegines1 on 15/02/2018.
 */

public class Connection extends AsyncTask<String, Void, String> {

    public static final String POST="POST";
    public static final String GET="GET";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";


    private final String Ip = "https://pilot2.000webhostapp.com/server.php";

    private URL url;

    private HttpsURLConnection conn;
    private String response;
    private String method;
    private String json;
    private String resource;
   private String key;


    public Connection(String method, String resource, String key){
        this.setMethod(method);
        this.setConnection(resource,key);

    }

    public void setConnection(String resource, String key){
        this.resource=resource;
        this.key=key;
        String uri= Ip + "/" + resource;
        if (key!=null){
            uri=uri + "/" + key;
        }
        try {
            this.url = new URL(uri);
        //    Log.v("Connection constructor", this.url.toString());
        }catch(Exception e){
            Log.e("setConnection", e.toString());
        }

    }
    public void setMethod(String method){
        this.method=method;
    }


    private void connect(){
        try {

            SSLContext ssl = setUpHttpsConnection();
            Connection.trustEveryone();
            conn = (HttpsURLConnection) this.url.openConnection();
            conn.setRequestProperty("Cookie", cookies.getCookiesTo());
            conn.setInstanceFollowRedirects(false);
            conn.setUseCaches(false);

           if(this.method==Connection.POST || this.method==Connection.PUT){
                conn.setDoOutput(true);
                setBody();
            }
            conn.setRequestMethod(this.method);

//            conn.setHostnameVerifier(DUMMY_VERIFIER);
//            conn.setSSLSocketFactory(ssl.getSocketFactory());

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

         //   Log.v("Conection getcokies:", cookies.getCookiesTo());

            // Log.v("URL REQUEST",conn.getURL().toString());
            conn.connect();
//            Log.v("Gines Conecction to", conn.getURL().toString());
//            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//            String line;
//            String jsonResult="";
//            while ((line = reader.readLine()) != null) {
//                jsonResult += line;
//            }
//            Log.v("RESPONSE :", jsonResult);
//            conn.getOutputStream();
//            OutputStream output = conn.getOutputStream();
//            output.write(conn.getResponseCode());

            //conn.connect();
         //   cookies.getCokiesFrom();
        } catch(Exception e){
            Log.e("Gines connection", e.toString());
        }
    }


    private void setBody(){
        try {
            //JSONObject jsonParam = new JSONObject(this.arguments);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            writer.write(jsonParam.toString());
            writer.write(this.json);
       //     Log.v("Gines Setting JSON body", this.json);
            writer.flush();
            writer.close();
            os.close();
        }catch(Exception e){
            Log.e("Connection setBody", e.toString());
        }
    }

    private String getResponse(){
        String jsonResult="";
        String code = "400";
        BufferedReader reader=null;
        try{
            code = Integer.toString(conn.getResponseCode());
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                jsonResult += line;
            }
     //       Log.v("Gines getResponse :", jsonResult);

            reader.close();
            return jsonResult;

        }catch(Exception e){
            Log.e(" GetResponse", e.toString());
            return code;
        }finally {
         //   Log.v("Gines RESPONSE CODE", code );
            if(reader!=null){
                try {
                    reader.close();
                }catch (Exception e){
                    Log.e("GetResponse", "Error closing reader." +e.getMessage() );
                }

            }

            return ((jsonResult == "") ? code : jsonResult);

        }

    }

    @Override
    protected String doInBackground(String... params) {
        //SET ARGUMENT TO RESTFUL QUERY
        String json="";
        if(params.length>0) {
           this.json = params[0];
        }

        switch (this.method) {
            case "POST":
          //      Log.v("Gines CONNECTION !!! ", "POST METHOD");
                this.POST();
                break;

            case "GET":
           //     Log.v("Gines CONNECTION !!! ", "GET METHOD");
                this.GET();
                break;

            case "PUT":
            //    Log.v("Gines CONNECTION !!! ", "PUT METHOD");
                this.PUT();
                break;
            case "DELETE":
            //    Log.v("Gines CONNECTION !!! ", "DELETE METHOD");
                this.DELETE();
                break;
            default:
                break;
        }
          return this.response;
    }

    @Override
    protected void onPostExecute(String response) {

        super.onPostExecute(response);

    }


    public void POST(){

        try {
            //this.setBody(json);
            this.connect();
            this.response =  this.getResponse();

            this.conn.disconnect();


        }catch(Exception e){
            Log.e("Gines Post", e.toString());
        }
        return;
    }

    public void  GET(){
            this.connect();
            this.response = this.getResponse();
            this.conn.disconnect();
            return;

    }

    public void PUT(){
        try {

            this.connect();
            this.response =  this.getResponse();
            this.conn.disconnect();
        }
        catch(Exception e){
            Log.e("Gines PUT", e.toString());
        }
        return;
    }

    public void DELETE(){
        this.connect();
        this.response = this.getResponse();
        this.conn.disconnect();
        return;

    }

    // @SuppressLint("SdCardPath")
    public static SSLContext setUpHttpsConnection() {
        try
        {
            // Load CAs from an InputStream
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = new BufferedInputStream(Main.getAppContext().getAssets().open("pilotIP.cer"));
            Certificate ca = cf.generateCertificate(caInput);
           // System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

            /************/
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, wrappedTrustManagers, null);
            /**********/

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            return context;

        }
        catch (Exception ex)
        {
            Log.e("SLL AAA2,", "Failed to establish SSL connection to server: " + ex.toString());
            return null;
        }
    }

    public static final HostnameVerifier DUMMY_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            Log.i("RestUtilImpl", "Approving certificate for " + hostname);
            return true;
        }
    };

    public String getJSONResponse(){
        return this.response;
    }

    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}

