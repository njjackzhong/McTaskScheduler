package vcdn.process;

import com.alibaba.fastjson.JSON;
import vcdn.core.VCDNServerApp;
import vcdn.model.MCTranscodeTask;
import vcdn.process.proxy.MCServerProxy;

/**
 * 转码任务处理线程
 * <p>
 * Created by jack on 2017/1/12.
 */
public class MCTaskConsumer implements Runnable {

    private int workerId;

    /**
     * 标记位  是否允许
     */
    private volatile boolean running = true;

    public MCTaskConsumer(int workerId) {
        this.workerId = workerId;
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        try {
            MCTranscodeTask mcTranscodeTask = MCTaskProcessCenter.getInstance().getTask();
            if(mcTranscodeTask!=null){
                //设置workerId
                mcTranscodeTask.setWorker(workerId);
                String taskContent = JSON.toJSONString(mcTranscodeTask);
                String taskState = MCServerProxy.createTask(taskContent,workerId);
                //TODO:状态写入数据库
                 VCDNServerApp.logger.error(String.format("创建新转码任务成功，返回信息[%s]",taskContent));
            }
            else{
                VCDNServerApp.logger.error(String.format("MCTaskConsumer [workerId=%d]，等待转码的任务列表是空",workerId));
            }
        } catch (Exception e) {
            VCDNServerApp.logger.error(e);
        } finally {
            VCDNServerApp.logger.info("停止MCTaskConsumer");
        }

    }
}
