package vcdn.model;

/**
 * MediaCoder 转码服务 滴 转码任务状态
 * <p>
 * Created by jack on 2017/1/13.
 */
public class MCWorkerState {
    //region  查询转码任务成功时，包含的属性值

    //转码状态，默认值是"unknown"表示是未知状态
    private String state=MCStatus.MC_JS_UNKNOWN;
    //转码完成比例：单位百分之几
    private int percent;
    //剩余时间：单位秒
    private int remainTime;
    //实际时间：单位秒
    private int elapsedTime;
    //
    private int throughput;
    //转码倍数
    private int speed;
    //音频码率
    private int audioBitrate;
    private int audioData;
    //视频时间
    private int duration;
    private int estimatedSize;
    //转码速度：每秒处理帧数
    private int fps;
    //已经转码帧数??? 视频???
    private int frames;
    //实时处理数据位置：单位秒
    private int pos;
    //视频码率
    private int videoBitrate;
    private int videoData;

    //endregion


    //region 查询转码任务失败时，包含的属性值
    // result  Worker状态查询是否成功，默认值是成功，在发生异常时，设置为"error"
    private String result = "success";
    //error  Worker状态状态查询的错误消息 例如："无法连接转码服务" 等
    private String error = "";
    //endregion


    //region 属性的getter & setter
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getThroughput() {
        return throughput;
    }

    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAudioBitrate() {
        return audioBitrate;
    }

    public void setAudioBitrate(int audioBitrate) {
        this.audioBitrate = audioBitrate;
    }

    public int getAudioData() {
        return audioData;
    }

    public void setAudioData(int audioData) {
        this.audioData = audioData;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(int estimatedSize) {
        this.estimatedSize = estimatedSize;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getVideoBitrate() {
        return videoBitrate;
    }

    public void setVideoBitrate(int videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public int getVideoData() {
        return videoData;
    }

    public void setVideoData(int videoData) {
        this.videoData = videoData;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    //endregion
}
