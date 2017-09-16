package framework.tool;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Admin on 2016/4/28.
 */
public class EncodeTool
{
    public static String convertStreamToString(InputStream is)
    {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[1024];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }
}
