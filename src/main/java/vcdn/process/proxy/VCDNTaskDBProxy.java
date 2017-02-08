package vcdn.process.proxy;

import vcdn.core.VCDNServerApp;
import vcdn.model.MCStatus;
import vcdn.model.MCTranscodeTask;
import vcdn.process.MCTaskProcessCenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2017/1/17.
 *
 * VCDN转码任务数据层访问代理: VCDNTaskDBProxy
 * 职责：
 * 1.VCDN转码任务的读取
 * 2.VCDN转码任务的进度、状态 保存
 */
public class VCDNTaskDBProxy {
    /**
     * 获取转码任务
     *
     * @return 返回待转码任务
     */
    public static void getWillTranscodeTask() {
        //TODO:从Restful API从服务获取
        //TODO:同时添加条件
        //task = "{\"/worker\":2,\"input\":\"d:\\Program\\Video\\FTP\\home.mkv\",\"output\":\"d:\\Program\\FTP\\MS\\home.mp4\",\"begin\":0,\"end\":10,\"state\":\"encoding\"}";
        MCTranscodeTask mcTranscodeTask = new MCTranscodeTask();
        mcTranscodeTask.setInput("e:\\FTP\\Home.mkv");
        mcTranscodeTask.setOutput("e:\\FTP\\MS\\Home.mp4");
        mcTranscodeTask.setTaskId(2);
        mcTranscodeTask.setState(MCStatus.MC_JS_PROCESSING);
        MCTaskProcessCenter.getInstance().addTask(mcTranscodeTask);



//        MCTranscodeTask secondmcTranscodeTask = new MCTranscodeTask();
//        secondmcTranscodeTask.setInput("d:\\Program\\Video\\FTP\\home1.mkv");
//        secondmcTranscodeTask.setOutput("d:\\Program\\Video\\FTP\\MS\\home1.mp4");
//        secondmcTranscodeTask.setTaskId(2);
//        secondmcTranscodeTask.setState(MCStatus.MC_JS_PROCESSING);
//        MCTaskProcessCenter.getInstance().addTask(secondmcTranscodeTask);

    }




}
