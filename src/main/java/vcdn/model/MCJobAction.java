package vcdn.model;

/**
 *
 * Media Coder Server  Trans  Task   Action
 *
 * Created by jack on 2017/1/11.
 */
public interface MCJobAction {
    /**
     * MediaCoder Job Action Encoding
     */
    String MC_JA_ENCODE = "encoding";
    /**
     * MediaCoder Job Action Stop
     */
    String MC_JA_STOP = "stopping";
    /**
     * MediaCoder Job Action Paused
     */
    String MC_JA_PAUSE = "paused";
}
