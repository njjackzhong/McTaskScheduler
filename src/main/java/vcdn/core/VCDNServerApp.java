package vcdn.core;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import vcdn.process.MCTaskProcessCenter;
import vcdn.process.proxy.VCDNTaskDBProxy;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;


/**
 *
 *  程序主类以及入口： VCDNServerApp
 *
 * 职责：
 *
 * 1. 读取以及保存程序全局配置信息
 *
 * 2. 创建、运行转码任务处理类  MCTaskProcessCenter
 *
 * Created by jack on 2017/1/12.
 */
public class VCDNServerApp implements ServletContextListener {

    //log4j日志对象
    public static Logger logger = Logger.getRootLogger();


    /**
     * 转码服务  并发任务数
     */
    private static int mcMaxTaskNum = 1;


    /**
     * 转码任务状态URL
     */
    private static String mcStatusUrl = "http://127.0.0.1:19819/mc/stats/";

    //版本号
    private static final AtomicReference<String> version = new AtomicReference<>("v1.0.1.2017-01-17");


    //转码服务状态同步周期，默认值1000，单位ms
    private static int workerStateSyncCycle = 5000;


    /**
     * 转码任务以及任务控制访问URL
     */
    private static String mcTransCodeUrl = "http://127.0.0.1:19819/mc/transcoder/";




    /**
     * 获取转码前后文件信息
     */
    private static String mcMediaInfoUrl = "http://127.0.0.1:19819/mc/job";


    /**
     * 测试目录
     */
    private static String mediaSourceDir = "E:\\FTP\\Source";


    /**
     * 转码配置文件
     */

    private static String mcConfigXmlPath = "e:\\MediaCoderXml\\GPU_auto.xml";


    /**
     * 实现ServletContextListener接口  服务启动时执行此方法
     * @param servletContextEvent  事件
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //1.读取配置信息
        String cfgPath = servletContextEvent.getServletContext().getRealPath("WEB-INF/conf");
        init(cfgPath);

        //2.创建处理中心
        MCTaskProcessCenter.getInstance();


        //3.读取任务
        //TODO:启用定时器查询入库转码任务  2017-02-08   现在是测试MediaCoder接口阶段
        //TODO: Id  大于多少的 未完成任务
        VCDNTaskDBProxy.getWillTranscodeTask();
    }

    /**
     *  实现ServletContextListener接口  服务关闭时执行此方法
     * @param servletContextEvent  事件
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //TODO:注销相关子线程
        logger.error("退出程序，注销相关服务");
    }

    /**
     * 测试类调用此方法初始化配置信息
     *
     * Create: Jack      Time: 2017-01-19 08:23
     *
     *
     *
     */
    public  void initTest(){
        String confPath = new File("web/WEB-INF/conf").getPath();
        init(confPath);
    }


    /**
     * 初始化配置
     * @return  true or false
     */
    public  boolean init(String cfgPath){
        //TODO:读取log4j.properties
        PropertyConfigurator.configureAndWatch(FilenameUtils.concat(cfgPath,"log4j.properties"),1);
        logger.error(String.format("（1）版本号:%s，成功读取log4j.properties配置文件，路径等于：%s", version.get(),FilenameUtils.concat(cfgPath,"log4j.properties")));

        //TODO: 读取数据库Tbl_VCDN_Config

        //TODO:读取配置文件 xml

        logger.error("（2）成功读取xml配置文件");

        logger.error("（3）成功初始化程序");



        return true;
    }


    /**
     * 获取转码服务 并发任务数
     * @return  并发任务数
     */
    public static int getMcMaxTaskNum() {
        return mcMaxTaskNum;
    }

    public static void setMcMaxTaskNum(int mcMaxTaskNum) {
        VCDNServerApp.mcMaxTaskNum = mcMaxTaskNum;
    }

    public static String getMcStatusUrl() {
        return mcStatusUrl;
    }

    public static void setMcStatusUrl(String mcStatusUrl) {
        VCDNServerApp.mcStatusUrl = mcStatusUrl;
    }

    public static String getMcTransCodeUrl() {
        return mcTransCodeUrl;
    }


    public static String getMcMediaInfoUrl() {
        return mcMediaInfoUrl;
    }

    public static void setMcMediaInfoUrl(String mcMediaInfoUrl) {
        VCDNServerApp.mcMediaInfoUrl = mcMediaInfoUrl;
    }

    public static void setMcTransCodeUrl(String mcTransCodeUrl) {
        VCDNServerApp.mcTransCodeUrl = mcTransCodeUrl;
    }

    public static int getWorkerStateSyncCycle() {
        return workerStateSyncCycle;
    }

    public static void setWorkerStateSyncCycle(int workerStateSyncCyle) {
        VCDNServerApp.workerStateSyncCycle = workerStateSyncCyle;
    }

    public static String getMediaSourceDir() {
        return mediaSourceDir;
    }

    public static void setMediaSourceDir(String mediaSourceDir) {
        VCDNServerApp.mediaSourceDir = mediaSourceDir;
    }

    public static String getMcConfigXmlPath() {
        return mcConfigXmlPath;
    }

    public static void setMcConfigXmlPath(String mcConfigXmlPath) {
        VCDNServerApp.mcConfigXmlPath = mcConfigXmlPath;
    }


}
