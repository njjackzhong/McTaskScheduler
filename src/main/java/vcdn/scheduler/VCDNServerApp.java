package vcdn.scheduler;

import vcdn.process.MCTaskProcessCenter;

/**
 * 程序主类： 转码任务调度管理器
 *
 * 职责：
 *
 * 1. 调用初始化类AppConfig，读取配置信息
 *
 * 2. 注册信号量
 *
 * 3. 创建、运行转码任务处理类  MCTaskProcessCenter
 *
 * Created by jack on 2017/1/12.
 */
public class VCDNServerApp {
    /**
     *
     * 程序主入口
     *
     */
    public static void start(){

        //TODO: code

        //1. 初始化参数
        //AppConfig.init();

        //TODO 2.注册信号量


        //3.
        MCTaskProcessCenter.getInstance();

    }



}
