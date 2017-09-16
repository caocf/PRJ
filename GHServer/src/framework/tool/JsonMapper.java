package framework.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by Admin on 2016/5/4.
 */
public class JsonMapper extends ObjectMapper
{
    public JsonMapper()
    {
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }
}
