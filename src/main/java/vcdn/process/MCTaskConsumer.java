package vcdn.process;

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
                MCServerProxy.createTask(mcTranscodeTask.getTaskContent());
                //TODO:写入数据库
            }
        } catch (Exception e) {
            VCDNServerApp.logger.error(e);
        } finally {
            VCDNServerApp.logger.info("停止MCTaskConsumer");
        }

    }
}
