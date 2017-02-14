package vcdn.process.proxy;

import org.apache.http.conn.HttpHostConnectException;
import org.apache.log4j.Logger;
import vcdn.core.VCDNConfigCenter;
import vcdn.model.MCJobAction;
import vcdn.util.MCHttpClient;


/**
 * Created by jack on 2017/1/10.
 * <p>
 * https://hc.apache.org/httpcomponents-client-ga/quickstart.html
 *
 * MediaCoderServer 转码服务的访问代理
 *
 */
//@Controller
public class MCServerProxy {

    //log4j日志对象
    private static Logger logger = Logger.getLogger(MCServerProxy.class);

    /**
     * @return 创建任务，返回JSON 消息
     */
    public static String createTask(String task,int workerId) {
        //TODO: 读取配置 URL、参数 转成字典等
//        System.out.println("Invoke MCServerProxy.createTask()");
        //String task = "{\"input\":\"D:\\\\Program\\\\Video\\\\FTP\\\\home.mkv\",\"begin\":0,\"end\":0,\"output\":\"D:\\\\Program\\\\Video\\\\FTP\\\\MS\\\\home.mp4\",\"preset\":\"\",\"state\":\"ready\",\"prefs\":{\"overall\":{\"video\":{\"enabled\":true,\"bitrate\":1000},\"audio\":{\"enabled\":true}}}}";
//        String task;
//        task = "{\"/worker\":2,\"input\":\"d:\\Program\\Video\\FTP\\home.mkv\",\"output\":\"d:\\Program\\FTP\\MS\\home.mp4\",\"begin\":0,\"end\":10,\"state\":\"encoding\"}";
        //String task = "{\"input\":\"D:\\\\Program\\\\Video\\\\FTP\\\\home.mkv\",\"begin\":0,\"end\":0,\"output\":\"D:\\\\Program\\\\Video\\\\FTP\\\\MS\\\\home.mp4\",\"prefs\":{\"overall\":{\"video\":{\"enabled\":true,\"bitrate\":1000},\"audio\":{\"enabled\":true}}}}";
//        String task = "{\"input\":\"D:\\\\Program\\\\Video\\\\FTP\\\\home.mkv\",\"begin\":0,\"end\":0,\"output\":\"D:\\\\Program\\\\Video\\\\FTP\\\\MS\\\\home.mp4\",\"preset\":\"\",\"state\":\"encoding\",\"prefs\":{\"overall\":{\"video\":{\"enabled\":true,\"bitrate\":1000},\"audio\":{\"enabled\":true}}}}";
        String description = "创建转码任务";
        logger.info(String.format("%s，task=[%s]",description,task));
        try {
//            return MCHttpClient.post(task, description, VCDNServerApp.getMcTransCodeUrl());
            return MCHttpClient.post(task, description, VCDNConfigCenter.getMcTransCodeUrl()+workerId);
        } catch (Exception e) {
            //TODO log4j
//            System.out.println("MCServerProxy.createTask() get Exception");
            logger.error(String.format("%s %s",description,e));
            return String.format("{\"result\":\"fail\",\"error\":\"%s\"}", e.getMessage());
        }
    }

    /**
     * 设置转码任务状态
     * <p>
     * 启用、恢复、暂停
     *
     * @return 返回JSON格式
     */
    public static String ctrlTask(String action) {
//        System.out.println("(1) MCServerProxy.ctrlTask(),action=" + action);
//        VCDNServerApp.logger.info(String.format("%s，task=[%s]",description,task));
        //TODO : Join stopTask
        if (!action.equalsIgnoreCase(MCJobAction.MC_JA_ENCODE)
                && !action.equalsIgnoreCase(MCJobAction.MC_JA_PAUSE)
                && !action.equalsIgnoreCase(MCJobAction.MC_JA_STOP)) {
            System.out.println("(2) MCServerProxy.ctrlTask(),action Invalid");
            return "{\"result\":\"failed\",\"error\":\"Invalid action\"}";
        }
        String task = String.format("{\"state\":\"%s\"}", action);
        //TODO: Dictionary 翻译任务
        try {
            return MCHttpClient.post(task, action, VCDNConfigCenter.getMcTransCodeUrl());
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    /**
     * 获取任务状态
     *
     * @return 返回JSON格式
     */
    public static String getTaskStatus(int workerId) {
        logger.debug(String.format("请求Worker状态，workerId等于%d", workerId));
        //String worker = String.format("{\"worker\":%d}", workerId);
        String description = "查询转码任务状态";
        String result;
        try {
//            result = MCHttpClient.post(worker, description, VCDNServerApp.getMcStatusUrl());
            result = MCHttpClient.post("", description, VCDNConfigCenter.getMcStatusUrl()+workerId);

        }
        catch (HttpHostConnectException e){
            logger.error(String.format("获取Worker[workerId=%d]状态请求异常,异常消息:[%s]", workerId,e));
            result = "{\"result\":\"fail\",\"error\":\"无法连接转码服务\"}";
        }
        //TODO:考虑result中是否包含异常原因？
        catch (Exception e) {
            logger.error(String.format("处理Worker状态请求异常,%s", e));
            result = "{\"result\":\"fail\",\"error\":\"处理Worker状态请求异常\"}";
        }
        logger.debug(String.format("返回Worker状态消息，消息内容=%s", result));
        return result;
    }

    /**
     * 获取转码前后文件信息
     *
     * @return 返回JSON格式
     */
    public static String getMediaInfo() {
        //logger.info(String.format("请求Worker状态，workerId等于%d", workerId));
        //String worker = String.format("{\"worker\":%d}", workerId);
        String description = "查询转码文件信息";
        String result;
        try {
//            result = MCHttpClient.post(worker, description, VCDNServerApp.getMcStatusUrl());
            result = MCHttpClient.post("", description, VCDNConfigCenter.getMcMediaInfoUrl());

        }
        catch (HttpHostConnectException e){
            logger.error(String.format("查询文件信息异常,异常消息:[%s]", e));
            result = "{\"result\":\"fail\",\"error\":\"无法连接转码服务\"}";
        }
        //TODO:考虑result中是否包含异常原因？
        catch (Exception e) {
            logger.error(String.format("查询文件信息异常,%s", e));
            result = "{\"result\":\"fail\",\"error\":\"查询文件信息异常\"}";
        }
        logger.info(String.format("返回文件信息，消息内容=%s", result));
        return result;
    }


    /**
     * 测试样例
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        //1.start a MCTask
//         System.out.println(MCServerProxy.createTask());

        //2.resume a MCTask
//        System.out.println(MCServerProxy.ctrlTask(MCJobAction.MC_JA_ENCODE));
//        System.out.println(MCServerProxy.getTaskStatus());
//
//        try {
//            Thread.sleep(15500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


//        System.out.println(MCServerProxy.getTaskStatus());

        //3.pause a MCTask
        System.out.println(MCServerProxy.ctrlTask(MCJobAction.MC_JA_PAUSE));

        //4.stop a MCTask
//        System.out.println(MCServerProxy.ctrlTask(MCJobAction.MC_JA_STOP));


//        while (true) {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
////            e.printStackTrace();
//            } finally {
//                //3.get  a MCWorker state
////                System.out.println(MCServerProxy.getTaskStatus(1));
//                logger.info(MCServerProxy.getTaskStatus(0));
//            }
//        }
    }
}
