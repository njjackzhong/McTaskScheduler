package vcdn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import vcdn.model.ResultInfo;
import vcdn.model.VCDNContent;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/2/13.
 */
public class MCHttpClientTest {

    String baseUrl = "http://192.168.180.32:8080/QueryService/";

    @Test
    public void testPost() throws Exception {
        //1.查询转码完成
        String convertedTaskList = MCHttpClient.get("","",baseUrl+"convertedContents");
        System.out.printf(convertedTaskList);

        ResultInfo resultInfo = JSON.parseObject(convertedTaskList,ResultInfo.class);
        if(resultInfo.isSucess())
        {
            String vcdnMsgList = JSON.toJSONString(resultInfo.getData());
            List<VCDNContent> vcdnContentList = JSON.parseArray(vcdnMsgList,VCDNContent.class);
//            System.out.printf("111");
        }


        //2.等待转码
        convertedTaskList = MCHttpClient.get("","",baseUrl+"waitingContents");
        System.out.printf(convertedTaskList);

        resultInfo = JSON.parseObject(convertedTaskList,ResultInfo.class);
        if(resultInfo.isSucess())
        {
            String vcdnMsgList = JSON.toJSONString(resultInfo.getData());
            List<VCDNContent> vcdnContentList = JSON.parseArray(vcdnMsgList, VCDNContent.class);
            System.out.printf("111");



            for (VCDNContent vcdnContent: vcdnContentList){
                if(vcdnContent.TaskId==3){
                    vcdnContent.Process = 100;
                    vcdnContent.ProcessState=2;
                    vcdnContent.ProcessStateMsg="转码成功";
                    vcdnContent.VCDNNetUrl = "/MS/20170109/Ocean/ocean.mp4";
                    vcdnContent.VCDNConfigKey="vcdn_url";
                    vcdnContent.TaskUpdateTime = Calendar.getInstance().getTime();
                    MCHttpClient.put(JSON.toJSONString(vcdnContent),"",baseUrl+"updateContent");
                }
            }

        }





    }

    @Test
    public void testGet() throws Exception {

        //MCHttpClient.post()
    }
}