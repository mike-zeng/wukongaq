package site.zeng.wukongaq.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RetJson {
    int code;
    String msg;
    Map data;

    public static RetJson success(){
        RetJson retJson=new RetJson();
        retJson.setCode(0);
        return retJson;
    }
    public static RetJson success(String msg){
        RetJson retJson=new RetJson();
        retJson.setCode(0);
        retJson.setMsg(msg);
        return retJson;
    }

    public static RetJson success(Map map){
        RetJson retJson=new RetJson();
        retJson.setCode(0);
        retJson.setData(map);
        return retJson;
    }


    public static RetJson success(String key,Object value){
        Map<String,Object> map=new HashMap<>();
        map.put(key,value);
        return success(map);
    }


    public static RetJson fail(int code,String msg){
        RetJson retJson=new RetJson();
        retJson.setCode(code);
        retJson.setMsg(msg);
        return retJson;
    }

    @Override
    public String toString() {
        ObjectMapper mapper=new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(this);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }
}