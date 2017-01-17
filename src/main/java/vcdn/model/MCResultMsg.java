package vcdn.model;

/**
 * 场景:
 *
 *  1.创建任务返回消息格式
 *  2.任务控制返回消息格式
 *  3.监控任务返回的异常消息
 *
 * Created by jack on 2017/1/13.
 *
 *
 *
 */
public class MCResultMsg {
    private String result;
    private String error;

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
}
