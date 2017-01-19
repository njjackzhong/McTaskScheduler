package vcdn.model;

/**
 * Media Coder Server  Trans  Task  Status
 *
 * Created by jack on 2017/1/11.
 */
public interface MCStatus {
    /**
     * MediaCoder Job Status Ready
     */
    String MC_JS_READY = "ready";
    /**
     * MediaCoder Job Status Encoding
     */
    String MC_JS_PROCESSING = "encoding";
    /**
     * MediaCoder Job Status Stop
     */
    String MC_JS_STOP = "stopping";
    /**
     * MediaCoder Job Status Paused
     */
    String MC_JS_PAUSED = "paused";
    /**
     * MediaCoder Job Status done
     */
    String MC_JS_DONE = "done";

    /**
     *  MediaCoder Job Status unknown
     *
     *  default value
     *
     */
    String MC_JS_UNKNOWN="unknown";
}
