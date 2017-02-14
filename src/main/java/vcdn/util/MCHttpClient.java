package vcdn.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * MCHttpClient   MCHttp访问代理  提供POST
 *
 * Created by jack on 2017/1/11.
 */
public class MCHttpClient {

    /**
     * post方式访问HttpServer转码任务
     * @param task  任务参数
     * @param taskDescription  任务描述
     * @param url  Http  URL  地址
     * @return 返回JSON字符串
     * @throws Exception  异常
     */
    public static String post(String task,String taskDescription, String url) throws Exception{
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost;
        httpPost = new HttpPost(url);

        //1.Define Post Json Http entity
        StringEntity taskEntity = new StringEntity(task, ContentType.APPLICATION_JSON);
        taskEntity.toString();

        //2.set httpPost entity
        httpPost.setEntity(taskEntity);

        //3.post json entity 2 server
        HttpResponse httpResponse = httpClient.execute(httpPost);

        if(httpResponse.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
            throw new RuntimeException(taskDescription+"，错误码 : " + httpResponse.getStatusLine().getStatusCode());
        }

        //4. resolve httpResponse
        HttpEntity httpResponseEntity  = httpResponse.getEntity();
        if(httpResponseEntity!=null){
            result =  EntityUtils.toString(httpResponseEntity);
        }
        httpClient.close();
        return result;
    }

    /**
     * get方式访问HttpServer转码服务
     * @param task  任务参数
     * @param taskDescription  任务描述
     * @param url  Http  URL  地址
     * @return 返回JSON字符串
     * @throws Exception  异常
     */
    public static String get(String task,String taskDescription, String url) throws Exception{
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if(task.isEmpty()){

        }
        HttpGet httpGet = new HttpGet(url);


        //1.get json response 2 server
        HttpResponse httpResponse = httpClient.execute(httpGet);

        if(httpResponse.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
            throw new RuntimeException(taskDescription+"，错误码 : " + httpResponse.getStatusLine().getStatusCode());
        }

        //2. resolve httpResponse
        HttpEntity httpResponseEntity  = httpResponse.getEntity();
        if(httpResponseEntity!=null){
            result =  EntityUtils.toString(httpResponseEntity);
        }
        httpClient.close();
        return result;
    }



    /**
     * put方式访问HttpServer转码任务
     * @param task  任务参数
     * @param taskDescription  任务描述
     * @param url  Http  URL  地址
     * @return 返回JSON字符串
     * @throws Exception  异常
     */
    public static String put(String task,String taskDescription, String url) throws Exception{
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPut httpPut;
        httpPut = new HttpPut(url);

        //1.Define Post Json Http entity
        StringEntity taskEntity = new StringEntity(task, ContentType.APPLICATION_JSON);
        //taskEntity.toString();

        taskEntity.setContentEncoding("UTF-8");
        //2.set httpPost entity
        httpPut.setEntity(taskEntity);

        //3.post json entity 2 server
        HttpResponse httpResponse = httpClient.execute(httpPut);

        if(httpResponse.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
            throw new RuntimeException(taskDescription+"，错误码 : " + httpResponse.getStatusLine().getStatusCode());
        }

        //4. resolve httpResponse
        HttpEntity httpResponseEntity  = httpResponse.getEntity();
        if(httpResponseEntity!=null){
            result =  EntityUtils.toString(httpResponseEntity);
        }
        httpClient.close();
        return result;
    }

}
