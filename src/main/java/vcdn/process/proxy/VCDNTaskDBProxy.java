package vcdn.process.proxy;

import vcdn.model.MCTranscodeTask;

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
    public static List<MCTranscodeTask> getWillTranscodeTask() {
        //TODO:从Restful API从服务获取
        //task = "{\"/worker\":2,\"input\":\"d:\\Program\\Video\\FTP\\home.mkv\",\"output\":\"d:\\Program\\FTP\\MS\\home.mp4\",\"begin\":0,\"end\":10,\"state\":\"encoding\"}";
        MCTranscodeTask mcTranscodeTask = new MCTranscodeTask();
        mcTranscodeTask.setInput("d:\\Program\\Video\\FTP\\home.mkv");
        mcTranscodeTask.setOutput("d:\\Program\\FTP\\MS\\home.mp4");
        mcTranscodeTask.setTaskId(1);
//        mcTranscodeTask.setState();

        List<MCTranscodeTask> willProcTask = new ArrayList<>();
        willProcTask.add(0, mcTranscodeTask);
        return willProcTask;

    }




}
