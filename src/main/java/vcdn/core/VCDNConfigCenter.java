package vcdn.core;

import com.alibaba.fastjson.JSON;
import vcdn.model.MCXmlPreset;
import vcdn.model.VCDNConfig;
import vcdn.process.proxy.VCDNTaskDBProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by JackLee on 2017/2/14.
 *
 * 配置中心类
 *
 * 职责:
 * (1) 存储、提供全局配置信息
 * (2) 定期同步数据库配置信息
 *
 */
public class VCDNConfigCenter {

    /**
     * 转码服务  并发任务数
     */
    private static int mcMaxTaskNum = 1;


    /**
     * 转码任务状态URL
     */
    private static String mcStatusUrl = "http://127.0.0.1:19819/mc/stats/";

    //版本号
    protected static final AtomicReference<String> version = new AtomicReference<>("v1.0.1.2017-01-17");


    //转码服务状态同步周期，默认值1000，单位ms
    private static int workerStateSyncCycle = 5000;


    /**
     * 转码任务以及任务控制访问URL
     */
    private static String mcTransCodeUrl = "http://127.0.0.1:19819/mc/transcoder/";

    /**
     * 获取转码前后文件信息
     */
    private static String mcMediaInfoUrl = "http://127.0.0.1:19819/mc/job";

    /**
     * 查询服务Url
     */
    private static String queryServiceUrl = "http://192.168.180.32:8080/QueryService/";

    /**
     * 测试目录
     */
    private static String mediaSourceDir = "D:\\Program\\Video\\FTP";


    /**
     * 转码配置文件
     */
    private static String mcConfigXmlPath = "CPU.xml";

    /**
     * 转码配置文件字典
     */
    private static HashMap<String, String> mcCfgXmlMap = new HashMap<>();


    /**
     * 转码目标文件夹
     */
    private static String mediaDestDir = "D://FTP//MS";


    /**
     * 转码预设文件集合
     */
    private static List<MCXmlPreset> mcXmlPresetList = new ArrayList<>();

    public static void init() {

        //TODO:读取配置文件,获取queryServiceUrl   2017-02-14

        String cfgMsg = VCDNTaskDBProxy.getVcdnConfig();
        if (Objects.equals(cfgMsg, "")) {


            return;
        }

        List<VCDNConfig> vcdnConfigList = JSON.parseArray(cfgMsg, VCDNConfig.class);

        for (VCDNConfig vcdnConfig : vcdnConfigList) {
            if (vcdnConfig.getEnabled() == 1) {
                //预设文件
                if (Objects.equals(vcdnConfig.getKey(), "vcdn.cfg.preset")) {
                    mcXmlPresetList = JSON.parseArray(cfgMsg, MCXmlPreset.class);
                }
                //转码目标文件夹
                else if (Objects.equals(vcdnConfig.getKey(), "vcdn.cfg.dstpath")) {
                    mediaDestDir = vcdnConfig.getValue();
                }
            }
        }

    }


    /**
     * 获取转码服务 并发任务数
     *
     * @return 并发任务数
     */
    public static int getMcMaxTaskNum() {
        return mcMaxTaskNum;
    }

    public static void setMcMaxTaskNum(int mcMaxTaskNum) {
        VCDNConfigCenter.mcMaxTaskNum = mcMaxTaskNum;
    }

    public static String getMcStatusUrl() {
        return mcStatusUrl;
    }

    public static void setMcStatusUrl(String mcStatusUrl) {
        VCDNConfigCenter.mcStatusUrl = mcStatusUrl;
    }

    public static String getMcTransCodeUrl() {
        return mcTransCodeUrl;
    }


    public static String getMcMediaInfoUrl() {
        return mcMediaInfoUrl;
    }

    public static void setMcMediaInfoUrl(String mcMediaInfoUrl) {
        VCDNConfigCenter.mcMediaInfoUrl = mcMediaInfoUrl;
    }

    public static void setMcTransCodeUrl(String mcTransCodeUrl) {
        VCDNConfigCenter.mcTransCodeUrl = mcTransCodeUrl;
    }

    public static int getWorkerStateSyncCycle() {
        return workerStateSyncCycle;
    }

    public static void setWorkerStateSyncCycle(int workerStateSyncCyle) {
        VCDNConfigCenter.workerStateSyncCycle = workerStateSyncCyle;
    }

    public static String getMediaSourceDir() {
        return mediaSourceDir;
    }

    public static void setMediaSourceDir(String mediaSourceDir) {
        VCDNConfigCenter.mediaSourceDir = mediaSourceDir;
    }

    public static String getMcConfigXmlPath() {
        return mcConfigXmlPath;
    }

    public static void setMcConfigXmlPath(String mcConfigXmlPath) {
        VCDNConfigCenter.mcConfigXmlPath = mcConfigXmlPath;
    }

    /**
     * 根据传入文件信息选择转码配置文件
     *
     * @param suffix 后缀名或者其他信息
     * @return 配置文件
     */
    public static String getMcCfgXmlName(String suffix) {
        if (mcCfgXmlMap.containsKey(suffix)) {
            return mcCfgXmlMap.get(suffix);
        } else {
            return mcCfgXmlMap.get("*.*");
        }
    }

    /**
     * 获取转码时预设Xml文件名
     *
     * @param suffix 后缀名
     * @param index  索引号
     * @return Preset文件名
     */
    public static String getMCPresetFileName(String suffix, int index) {

        MCXmlPreset mcXmlPreset = mcXmlPresetList.parallelStream().filter(s -> Objects.equals(s.getSuffix(), suffix)).filter(s -> s.getIndex() == index).findFirst().orElse(null);
        if (mcXmlPreset == null) {
            return "";
        } else {
            return mcXmlPreset.getPresetXml();
        }
    }


    public static String getQueryServiceUrl() {
        return queryServiceUrl;
    }

    public static void setQueryServiceUrl(String queryServiceUrl) {
        VCDNConfigCenter.queryServiceUrl = queryServiceUrl;
    }


    public static String getMediaDestDir() {
        return mediaDestDir;
    }

    public static void setMediaDestDir(String mediaDestDir) {
        VCDNConfigCenter.mediaDestDir = mediaDestDir;
    }
}
