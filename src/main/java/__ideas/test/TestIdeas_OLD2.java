package __ideas.test;

import jssc.*;
import org.takes.Take;
import org.takes.http.*;
import org.takes.tk.TkFixed;
import __ideas.threads.ThreadSensors;
import web.tk.TkWebApp;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.*;

public class TestIdeas_OLD2 {

    public static void main1(String[] args) throws SerialPortException, InterruptedException {
        ThreadSensors tSensors = new ThreadSensors();
        new Thread(tSensors).start();
    }

    public static void main2(String[] args) throws IOException {
        //new FtBasic(new TkCoreGateway(), 8081).start(Exit.NEVER);
        new FtBasic(new TkWebApp(), 8081).start(Exit.NEVER);
    }

    public static void main3(String[] args) throws IOException {
        final String file = "localhost.key";
        //this.getClass().getResource("/org/takes/http/keystore").getFile();
        final String password = "abc123";
        System.setProperty("javax.net.ssl.keyStore", file);
        System.setProperty("javax.net.ssl.keyStorePassword", password);

        System.setProperty("javax.net.ssl.trustStore", file);
        System.setProperty("javax.net.ssl.trustStorePassword", password);
        Take take = new TkFixed("hello, world");
        //new FtBasic(take, 8082).start(Exit.NEVER);
        new FtSecure(take, 8088).start(Exit.NEVER);;



/*
        //FtSecureTest.secure(new ).exec(
                new FtRemote.Script() {
                    @Override
                    public void exec(final URI home) throws IOException {
                        new JdkRequest(home)
                                .fetch()
                                .as(RestResponse.class)
                                .assertStatus(HttpURLConnection.HTTP_OK)
                                .assertBody(Matchers.startsWith("hello"));
                    }
                }
        );
*/

/*
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, kp);
        KeyManager km[] = kmf.getKeyManagers();

        SSLContext sslContext = SSLContext.getDefault();
        sslContext.init(km, null, new SecureRandom());

        SSLServerSocketFactory socketFactory = sslContext.getServerSocketFactory();
        ServerSocket serverSocket = socketFactory.createServerSocket(443);

        new FtSecureExplained(new TkWebApp(), serverSocket).start(Exit.NEVER);
*/

    }

    public static void main4(String[] args) {
        try
        {
            final String file = "localhost.key";
            final String password = "abc123";

            System.setProperty("javax.net.ssl.keyStore",file);
            System.setProperty("javax.net.ssl.keyStorePassword",password);
            System.setProperty("javax.net.ssl.trustStore", file);
            System.setProperty("javax.net.ssl.trustStorePassword", password);
            System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

            SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(9001);
            ss.setEnabledProtocols( new String[]{"TLSv1","TLSv1.1","TLSv1.2"} );

            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }

                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, trustAllCerts, new SecureRandom());

            SSLServerSocket ssl = (SSLServerSocket) sc.getServerSocketFactory().createServerSocket(
                    9002);
            // Got rid of:
            ssl.setEnabledCipherSuites(sc.getServerSocketFactory().getSupportedCipherSuites());
            ssl.setEnabledProtocols(new String[] {"TLSv1", "TLSv1.1", "TLSv1.2", "SSLv3"});

                String line = null;

            while (true)
            {
                Socket so = ssl.accept();

                OutputStream out = so.getOutputStream();
                BufferedReader bin = new BufferedReader(new InputStreamReader(so.getInputStream()));

                //String line = null;

                while (true)
                {
                    line = bin.readLine();
                    System.out.println(line);
                    if ((line == null) || ("".equals(line)))
                        break;
                }


//                while (((line = in.readLine()) != null) && (!("".equals(line))))
//                {
//                    System.out.println(line);
//                }

                if( line != null )
                {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("<HTML><HEAD><TITLE>HTTPS Server</TITLE></HEAD>\n");
                    buffer.append("<BODY>\n<H1>Success!</H1></BODY></HTML>\n");

                    String string = buffer.toString();
                    byte[] data = string.getBytes();

                    out.write("HTTP/1.0 200 OK\n".getBytes());
                    out.write(new String("Content-Length: " + data.length + "\n").getBytes());
                    out.write("Content-Type: text/html\n\n".getBytes());
                    out.write(data);
                    out.flush();
                }

                out.close();
                so.close();
            }

        }
        catch (Throwable thrown)
        {
            thrown.printStackTrace();
        }
    }

}
