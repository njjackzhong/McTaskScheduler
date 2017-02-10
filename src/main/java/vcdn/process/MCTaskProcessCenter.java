package vcdn.process;

import com.alibaba.fastjson.JSONObject;
import vcdn.core.VCDNServerApp;
import vcdn.model.MCStatus;
import vcdn.model.MCTranscodeTask;
import vcdn.model.MCWorkerState;
import vcdn.process.proxy.MCServerProxy;
import vcdn.process.proxy.VCDNTaskDBProxy;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * MC 转码任务处理中心类
 * <p>
 * 职责：
 * (1)
 * 依赖关系：
 * <p>
 * (a) VCDNServerApp 调用此类
 * <p>
 * <p>
 * <p>
 * Created by jack on 2017/1/12.
 */
public class MCTaskProcessCenter {


    /**
     * 待转码任务队列
     */
    private BlockingDeque<MCTranscodeTask> willProcTaskQueue = new LinkedBlockingDeque<>();

    //TODO:定义Tbl_VCND_Content 字典  key:TaskId  value:Tbl_VCND_Content对象
    // 记录

//    private BlockingDeque<Integer> tokenQueue = new LinkedBlockingDeque<>();

    private ExecutorService procExecutorPool;

    private ConcurrentHashMap<Integer, MCTaskConsumer> taskProcQueue = new ConcurrentHashMap<>();

    /**
     * 转码服务状态映射表
     * <p>
     * Key: Worker ID  or   Tbl_VCND_Content  的TaskId
     * <p>
     * Value: 转码状态
     */
    private ConcurrentHashMap<Integer, MCWorkerState> mcWorkerStateConcurrentHashMap = new ConcurrentHashMap<>();


    /**
     * 单例
     */
    private volatile static MCTaskProcessCenter instance;

    public static MCTaskProcessCenter getInstance() {
        if (instance == null) {
            synchronized (MCTaskProcessCenter.class) {
                if (instance == null) {
                    instance = new MCTaskProcessCenter();
                }
            }
        }
        return instance;
    }

    /**
     * 添加转码任务
     *
     * @param task 转码任务
     */
    public void addTask(MCTranscodeTask task) {
        for (MCTranscodeTask t : willProcTaskQueue) {
            if (t.getTaskId() == task.getTaskId()) {
                return;
            }
        }
        willProcTaskQueue.offer(task);
    }

    /**
     * 返回当前待转码任务数目
     *
     * @return 任务数目
     */
    public int getTaskCount() {
        return willProcTaskQueue.size();
    }

    /**
     * 获取转码任务
     *
     * @return 返回转码任务
     */
    public MCTranscodeTask getTask() {
        return willProcTaskQueue.poll();
    }

//    public void addToken(int)

    private MCTaskProcessCenter() {

        //初始化线程池
        procExecutorPool = Executors.newCachedThreadPool();





        /*
            转码状态轮询定时器
         */
        Timer mcWorkStateSyncTimer = new Timer(true);
        mcWorkStateSyncTimer.schedule(new MCWorkerStateSyncTask(""), 1000, VCDNServerApp.getWorkerStateSyncCycle());


    }

    /**
     * 向MCServer提交转码任务
     *
     * @param workerId Worker 编号
     */
    void ExecuteMCTranscodeTask(int workerId) {
        //TODO:日志
        procExecutorPool.execute(new MCTaskConsumer(workerId));
    }

}

/**
 * Media Coder 状态同步以及Worker任务调度
 */
class MCWorkerStateSyncTask extends TimerTask {
    private String info = "";

    MCWorkerStateSyncTask(String info) {
        this.info = info;
    }


    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        try {

            //MCServerProxy.getMediaInfo();
            //TODO: 修改逻辑，读取所有状态，根据正在运行的任务数目与maxTaskNum，判断是否可以提交新任务
            int maxTaskNum = VCDNServerApp.getMcMaxTaskNum();
            for (int workerId = 0; workerId < maxTaskNum; workerId++) {

                //TODO: START  处理流程，全部移植到MCTaskConsumer里
                String workerState = MCServerProxy.getTaskStatus(workerId);
                //JSONObject
                MCWorkerState mcWorkerState = JSONObject.parseObject(workerState, MCWorkerState.class);

                //VCDNServerApp.logger.info(JSONObject.toJSONString(mcWorkerState));

                //如果转码状态是ready
                String state = mcWorkerState.getState();

                VCDNServerApp.logger.info(workerState);


                if (state.equalsIgnoreCase(MCStatus.MC_JS_READY)) {
                    if (MCTaskProcessCenter.getInstance().getTaskCount() == 0) {
                        VCDNServerApp.logger.info(String.format("[workerID=%d]当前等待转码的队列是空,无法创建新任务", workerId));
                    }
                    if (mcWorkerState.getPercent() == -1) {
                        MCTaskProcessCenter.getInstance().ExecuteMCTranscodeTask(workerId);
                    }
                } else if (state.equalsIgnoreCase(MCStatus.MC_JS_DONE)) {
                    if (MCTaskProcessCenter.getInstance().getTaskCount() == 0) {
                        VCDNServerApp.logger.info(String.format("[workerID=%d]当前等待转码的队列是空,无法创建新任务", workerId));
                    }
                    if (mcWorkerState.getPercent() == 100) {
                        //TODO:判断状态写入数据库
                        MCTaskProcessCenter.getInstance().ExecuteMCTranscodeTask(workerId);
                    }
                } else if (state.equalsIgnoreCase(MCStatus.MC_JS_ERROR)) {
                    if (MCTaskProcessCenter.getInstance().getTaskCount() == 0) {
                        VCDNServerApp.logger.info(String.format("[workerID=%d]当前等待转码的队列是空,无法创建新任务", workerId));
                    } else {
                        //TODO:判断状态写入数据库
                        MCTaskProcessCenter.getInstance().ExecuteMCTranscodeTask(workerId);
                    }
                } else if (state.equalsIgnoreCase(MCStatus.MC_JS_PROCESSING) || state.equalsIgnoreCase(MCStatus.MC_JS_STOP)) {
                    //TODO:判断状态写入数据库
//                    VCDNServerApp.logger.info(workerState);

                }
                //TODO:分析 是否存在其他情况需要处理

                //TODO: END  处理流程，全部移植到MCTaskConsumer里

            }

        } catch (Exception e) {
            VCDNServerApp.logger.error(e);
        } finally {
            ;
        }
    }
}
