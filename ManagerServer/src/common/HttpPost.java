package common;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Admin on 2016/9/5.
 */
public class HttpPost
{
    public static String post(String actionUrl,Map<String, Object> params) throws IOException
    {
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setReadTimeout(10 * 1000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);//设置不使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Content-Type",MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);
        conn.connect();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\""
                    + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }

        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        outStream.flush();

        InputStream in = null;
        int res = conn.getResponseCode();
        String sb2 = "";
        if (res == 200)
        {
            in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";

            for (line = br.readLine(); line != null; line = br.readLine())
            {
                sb2 = sb2 + line;
            }
        }
        outStream.close();
        conn.disconnect();
        return sb2;
    }


}
