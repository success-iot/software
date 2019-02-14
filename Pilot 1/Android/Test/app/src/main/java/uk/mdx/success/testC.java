package uk.mdx.success;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Josegines1 on 25/10/2018.
 */

public class testC {

    public testC(){
        try{


            URL url = new URL("https://10.12.102.61/success/test.php");

            /***************************************/

            url = new URL("https://10.12.102.61/success/test.php");
            /***************/
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = new BufferedInputStream(Main.getAppContext().getAssets().open("pilotIP.cer"));
            Certificate ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            /*****************/

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();


            conn.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession sessionssl) {
                    Log.v("RestUtilImpl", "Approving certificate for " + hostname);
                    return true;
                }
            });
            // SSLContext ssl = Connection.setUpHttpsConnection();
            SSLContext ssl = SSLContext.getInstance("TLS");
            ssl.init(null, tmf.getTrustManagers(), new SecureRandom());

            conn.setDefaultSSLSocketFactory(ssl.getSocketFactory());
            /***************************************/




            conn.setInstanceFollowRedirects(false);
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
String jsonResult="";
            while ((line = reader.readLine()) != null) {
                jsonResult += line;
            }
            Log.v("RESPONSE :", jsonResult);
            conn.getOutputStream();
            OutputStream output = conn.getOutputStream();
            output.write(conn.getResponseCode());

        }catch(Exception Mierda){
Mierda.printStackTrace();
        }finally {
            return;
        }
    }

    public static final HostnameVerifier DUMMY_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            Log.i("RestUtilImpl", "Approving certificate for " + hostname);
            return true;
        }
    };

    public static SSLContext setUpHttpsConnection()
    {
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
}