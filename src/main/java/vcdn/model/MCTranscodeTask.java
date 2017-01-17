package vcdn.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 转码任务
 * <p>
 * Created by jack on 2017/1/10.
 */
public class MCTranscodeTask {

    /**
     * vcdn唯一编码  Tbl_VCDN_Content 视频资源表  主键 TaskId
     */

    @JSONField(serialize = false)
    private int taskId;

    /**
     * worker  编号
     */
    private int worker;

    /**
     * 转码参数： 预设文件  默认值是空
     */
    private String preset;

    /**
     * 原始文件绝对路径
     */
    private String input;

    /**
     * 目标文件绝对路径
     */
    private String output;

    /**
     * 原始文起始位置（单位 ?）  默认值等于0
     */
    private int begin;

    /**
     * 原始文件结束位置（单位 ?） 默认值等于0
     */
    private int end;

    /**
     * 转码状态
     */
    private String state;

    /**
     * 转码参数： 微调参数
     */
    private String prefs;


    /**
     * 获取转码任务的请求消息
     * @return  MCTranscodeTask JSON请求
     */
    public String getTaskContent() {
        return JSON.toJSONString(this);
    }

    public int getWorker() {
        return worker;
    }

    public void setWorker(int worker) {
        this.worker = worker;
    }

    public static void main(String[] args) {
        MCTranscodeTask transCodeTaskMC = new MCTranscodeTask();
        transCodeTaskMC.setWorker(100);
        System.out.println(JSON.toJSONString(transCodeTaskMC));
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrefs() {
        return prefs;
    }

    public void setPrefs(String prefs) {
        this.prefs = prefs;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
