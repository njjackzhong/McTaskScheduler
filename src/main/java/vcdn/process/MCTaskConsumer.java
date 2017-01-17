package vcdn.process;

import vcdn.core.AppConfig;
import vcdn.model.MCTranscodeTask;

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
                VCDNController.createTask(mcTranscodeTask.getTaskContent());
                //TODO:写入数据库
            }
        } catch (Exception e) {
            AppConfig.logger.error(e);
        } finally {
            AppConfig.logger.info("停止MCTaskConsumer");
        }

    }
}
