package vcdn.core;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import vcdn.process.MCTaskProcessCenter;
import vcdn.process.proxy.VCDNTaskDBProxy;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;


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


        //TODO:读取任务表
//        mcCfgXmlMap.put(".dav",FilenameUtils.concat(cfgPath,"dav.xml"));
//        mcCfgXmlMap.put("*.*",FilenameUtils.concat(cfgPath,"GPU.xml"));

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
        MCTaskProcessCenter.getInstance().stop();
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
        logger.error(String.format("（1）版本号:%s，成功读取log4j.properties配置文件，路径等于：%s", VCDNConfigCenter.version.get(),FilenameUtils.concat(cfgPath,"log4j.properties")));

        //TODO: 读取数据库Tbl_VCDN_Config

        //TODO:读取配置文件 xml

        logger.error("（2）成功读取xml配置文件");

        logger.error("（3）读取数据库配置表");

        VCDNConfigCenter.init();



        logger.error("（4）成功初始化程序");



        return true;
    }


}
