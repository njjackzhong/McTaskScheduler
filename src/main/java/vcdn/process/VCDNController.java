package vcdn.process;

import org.apache.http.conn.HttpHostConnectException;
import org.apache.log4j.Logger;
import vcdn.core.AppConfig;
import vcdn.model.MCJobAction;
import vcdn.util.MCHttpClient;


/**
 * Created by jack on 2017/1/10.
 * <p>
 * https://hc.apache.org/httpcomponents-client-ga/quickstart.html
 */
//@Controller
public class VCDNController {

    //log4j日志对象
    private static Logger logger = Logger.getLogger(VCDNController.class);

    /**
     * @return 创建任务，返回JSON 消息
     */
    public static String createTask(String task) {
        //TODO: 读取配置 URL、参数 转成字典等
        System.out.println("Invoke VCDNController.createTask()");
        //String task = "{\"input\":\"D:\\\\Program\\\\Video\\\\FTP\\\\home.mkv\",\"begin\":0,\"end\":0,\"output\":\"D:\\\\Program\\\\Video\\\\FTP\\\\MS\\\\home.mp4\",\"preset\":\"\",\"state\":\"ready\",\"prefs\":{\"overall\":{\"video\":{\"enabled\":true,\"bitrate\":1000},\"audio\":{\"enabled\":true}}}}";
//        String task;
//        task = "{\"/worker\":2,\"input\":\"d:\\Program\\Video\\FTP\\home.mkv\",\"output\":\"d:\\Program\\FTP\\MS\\home.mp4\",\"begin\":0,\"end\":10,\"state\":\"encoding\"}";
        //String task = "{\"input\":\"D:\\\\Program\\\\Video\\\\FTP\\\\home.mkv\",\"begin\":0,\"end\":0,\"output\":\"D:\\\\Program\\\\Video\\\\FTP\\\\MS\\\\home.mp4\",\"prefs\":{\"overall\":{\"video\":{\"enabled\":true,\"bitrate\":1000},\"audio\":{\"enabled\":true}}}}";
//        String task = "{\"input\":\"D:\\\\Program\\\\Video\\\\FTP\\\\home.mkv\",\"begin\":0,\"end\":0,\"output\":\"D:\\\\Program\\\\Video\\\\FTP\\\\MS\\\\home.mp4\",\"preset\":\"\",\"state\":\"encoding\",\"prefs\":{\"overall\":{\"video\":{\"enabled\":true,\"bitrate\":1000},\"audio\":{\"enabled\":true}}}}";
        String description = "创建转码任务";
        try {
            return MCHttpClient.post(task, description, AppConfig.getMcTransCodeUrl());
        } catch (Exception e) {
            //TODO log4j
//            System.out.println("VCDNController.createTask() get Exception");
            AppConfig.logger.error(String.format("%s %s",description,e));
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
        System.out.println("(1) VCDNController.ctrlTask(),action=" + action);
        //TODO : Join stopTask
        if (!action.equalsIgnoreCase(MCJobAction.MC_JA_ENCODE)
                && !action.equalsIgnoreCase(MCJobAction.MC_JA_PAUSE)
                && !action.equalsIgnoreCase(MCJobAction.MC_JA_STOP)) {
            System.out.println("(2) VCDNController.ctrlTask(),action Invalid");
            return "{\"result\":\"failed\",\"error\":\"Invalid action\"}";
        }
        String task = String.format("{\"state\":\"%s\"}", action);
        //TODO: Dictionary 翻译任务
        String description = action;
        try {
            return MCHttpClient.post(task, description, AppConfig.getMcTransCodeUrl());
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
        logger.info(String.format("请求Worker状态，workerId等于%d", workerId));
        String worker = String.format("{\"worker\":%d}", workerId);
        String description = "查询转码任务状态";
        String result;
        try {
            result = MCHttpClient.post(worker, description, AppConfig.getMcStatusUrl());
        }
        catch (HttpHostConnectException e){
            logger.error(String.format("处理Worker状态请求异常,%s", e));
            result = "{\"result\":\"fail\",\"error\":\"无法连接转码服务\"}";
        }
        //TODO:考虑result中是否包含异常原因？
        catch (Exception e) {
            logger.error(String.format("处理Worker状态请求异常,%s", e));
            result = "{\"result\":\"fail\",\"error\":\"处理Worker状态请求异常\"}";
        }
        logger.info(String.format("返回Worker状态消息，消息内容=%s", result));
        return result;
    }


    /**
     * 测试样例
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        //1.start a MCTask
//         System.out.println(VCDNController.createTask());

        //2.resume a MCTask
//        System.out.println(VCDNController.ctrlTask(MCJobAction.MC_JA_ENCODE));
//        System.out.println(VCDNController.getTaskStatus());
//
//        try {
//            Thread.sleep(15500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


//        System.out.println(VCDNController.getTaskStatus());

        //3.pause a MCTask
        System.out.println(VCDNController.ctrlTask(MCJobAction.MC_JA_PAUSE));

        //4.stop a MCTask
//        System.out.println(VCDNController.ctrlTask(MCJobAction.MC_JA_STOP));


        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
//            e.printStackTrace();
            } finally {
                //3.get  a MCWorker state
//                System.out.println(VCDNController.getTaskStatus(1));
                logger.info(VCDNController.getTaskStatus(0));
            }
        }
    }
}
