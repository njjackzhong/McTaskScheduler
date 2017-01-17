package vcdn.core;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import vcdn.scheduler.VCDNServerApp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 *
 * 配置参数管理类
 *
 * Created by jack on 2017/1/12.
 */
public class AppConfig implements ServletContextListener {

    //log4j日志对象
    public static Logger logger = Logger.getRootLogger();


    /**
     * 转码服务  并发任务数
     */
    private static int mcMaxTaskNum =2;


    /**
     * 转码任务状态URL
     */
    private static String mcStatusUrl = "http://127.0.0.1:19819/mc/stats";


    /**
     * 转码任务以及任务控制访问URL
     */
    private static String mcTransCodeUrl  = "http://127.0.0.1:19819/mc/transcoder";

    /**
     * 实现ServletContextListener接口  服务启动时执行此方法
     * @param servletContextEvent  事件
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String cfgPath = servletContextEvent.getServletContext().getRealPath("WEB-INF/conf");
        init(cfgPath);

        //Start  Server App
        VCDNServerApp.start();
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
     * 初始化配置
     * @return  true or false
     */
    public  boolean init(String cfgPath){
        //TODO:读取log4j.properties
        PropertyConfigurator.configureAndWatch(FilenameUtils.concat(cfgPath,"log4j.properties"));
        logger.error(String.format("成功读取log4j.properties配置文件, 路径等于：%s...",FilenameUtils.concat(cfgPath,"log4j.properties")));

        //TODO: 读取数据库Tbl_VCDN_Config

        //TODO:读取配置文件 xml

        logger.error("成功读取xml配置文件...");

        logger.error("成功初始化程序...");



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
        AppConfig.mcMaxTaskNum = mcMaxTaskNum;
    }

    public static String getMcStatusUrl() {
        return mcStatusUrl;
    }

    public static void setMcStatusUrl(String mcStatusUrl) {
        AppConfig.mcStatusUrl = mcStatusUrl;
    }

    public static String getMcTransCodeUrl() {
        return mcTransCodeUrl;
    }

    public static void setMcTransCodeUrl(String mcTransCodeUrl) {
        AppConfig.mcTransCodeUrl = mcTransCodeUrl;
    }

}
