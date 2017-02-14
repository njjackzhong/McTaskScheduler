package vcdn.process.proxy;

import com.alibaba.fastjson.JSON;
import vcdn.core.VCDNConfigCenter;
import vcdn.core.VCDNServerApp;
import vcdn.model.MCStatus;
import vcdn.model.MCTranscodeTask;
import vcdn.model.ResultInfo;
import vcdn.model.VCDNContent;
import vcdn.process.MCTaskProcessCenter;
import vcdn.util.MCHttpClient;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by jack on 2017/1/17.
 *
 * VCDN转码任务数据层访问代理: VCDNTaskDBProxy
 * 职责：
 * 1.VCDN转码任务的读取
 * 2.VCDN转码任务的进度、状态 保存
 */
public class VCDNTaskDBProxy {
    /**
     * 获取转码任务信息 返回待转码任务-根据本地配置信息
     *
     */
    public static void getWillTranscodeTaskTest() {
        String dirName = VCDNConfigCenter.getMediaSourceDir();
        File dir = new File(dirName);
        if (!dir.exists()) {
            VCDNServerApp.logger.error("不存在目录" + dirName);
            return;
        }

//        FileFilter filter = new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                return pathname.isFile();
//            }
//        };


        FilenameFilter fileNameFilter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.lastIndexOf('.') > 0) {
                    // get last index for '.' char
                    int lastIndex = name.lastIndexOf('.');

                    // get extension
                    String str = name.substring(lastIndex);

                    // match path name extension
                    if (str.equalsIgnoreCase(".flv")||str.equalsIgnoreCase(".dav")||str.equals(".mkv") || str.equals(".ts") || str.equals(".mp4") || str.equals(".wmv") || str.equals(".avi")|| str.equals(".dav")) {
                        //match file name
//                        if(name.contains("Assassin.mkv"))
                       // if (name.contains("home.mkv"))

                            return true;
                    }
                }
                return false;
            }
        };


        File[] files = dir.listFiles(fileNameFilter);

        if (files == null) {
            return;
        }

        int index = 0;

        for (File path : files) {
            if (path.isDirectory()) {
                continue;
            }

            String preFileName = path.getName().substring(0, path.getName().lastIndexOf("."));//
            String suffix = path.getName().substring(path.getName().lastIndexOf("."));
            Path msFilePath = Paths.get(dirName, "MS", preFileName + ".mp4");
            System.out.println(path);


            MCTranscodeTask mcTranscodeTask = new MCTranscodeTask();
            mcTranscodeTask.setInput(path.getAbsolutePath());
            mcTranscodeTask.setOutput(msFilePath.toString());
            mcTranscodeTask.setPreset(VCDNConfigCenter.getMcCfgXmlName(suffix));
            mcTranscodeTask.setBegin(0);
            mcTranscodeTask.setEnd(60000);
//            mcTranscodeTask.setPrefs("\"overall\":{\"video\":{\"enabled\": true,\"bitrate\": 4000,},\"audio\": {\"enabled\": false}}");
            //"overall":{"video":{"enabled": true,"bitrate": 4000,},"audio": {"enabled": true}}
            mcTranscodeTask.setTaskId(index);
            mcTranscodeTask.setState(MCStatus.MC_JS_PROCESSING);
//            mcTranscodeTask.setState(MCStatus.MC_JS_READY);
            MCTaskProcessCenter.getInstance().addTask(mcTranscodeTask);

            index++;
        }
    }



    public static void getWillTranscodeTask() {
        //1.等待转码
        VCDNServerApp.logger.info("获取待转码任务");

        String convertedTaskList  = null;
        try {
            convertedTaskList = MCHttpClient.get("","", VCDNConfigCenter.getQueryServiceUrl() + "waitingContents");
        } catch (Exception e) {
            //TODO:日志
            e.printStackTrace();

        }
        VCDNServerApp.logger.info("获取待转码任务，任务内容："+convertedTaskList);
        ResultInfo resultInfo = JSON.parseObject(convertedTaskList,ResultInfo.class);
        assert resultInfo != null;
        if(resultInfo.isSucess())
        {
            String vcdnMsgList = JSON.toJSONString(resultInfo.getData());
            List<VCDNContent> vcdnContentList = JSON.parseArray(vcdnMsgList, VCDNContent.class);
            for (VCDNContent vcdnContent: vcdnContentList) {
                String srcFileName = vcdnContent.SrcFileName;
                String preFileName = srcFileName.substring(0, srcFileName.lastIndexOf("."));//
                String suffix = srcFileName.substring(srcFileName.lastIndexOf("."));

                //TODO:验证文件合法性,是否存在

                MCTranscodeTask mcTranscodeTask = new MCTranscodeTask();
                mcTranscodeTask.setVcdnContent(vcdnContent);
                mcTranscodeTask.setPreset(VCDNConfigCenter.getMcCfgXmlName(suffix));
                mcTranscodeTask.setInput(vcdnContent.SrcFileName);
                mcTranscodeTask.setOutput(Paths.get(VCDNConfigCenter.getMediaDestDir(),preFileName,".mp4").toString());
                mcTranscodeTask.setState(MCStatus.MC_JS_PROCESSING);
                MCTaskProcessCenter.getInstance().addTask(mcTranscodeTask);
            }


//            for (VCDNContent vcdnContent: vcdnContentList){
//                if(vcdnContent.TaskId==3){
//                    vcdnContent.Process = 100;
//                    vcdnContent.ProcessState=2;
//                    vcdnContent.ProcessStateMsg="转码成功";
//                    vcdnContent.VCDNNetUrl = "/MS/20170109/Ocean/ocean.mp4";
//                    vcdnContent.VCDNConfigKey="vcdn_url";
//                    vcdnContent.TaskUpdateTime = Calendar.getInstance().getTime();
//                    MCHttpClient.put(JSON.toJSONString(vcdnContent),"",baseUrl+"updateContent");
//                }
//            }

        }
    }

    public static String getVcdnConfig(){
        //1.等待转码
        VCDNServerApp.logger.info("准备读取服务端配置项");

        String cfgmsg  = "";
        try {
            cfgmsg = MCHttpClient.get("","", VCDNConfigCenter.getQueryServiceUrl() + "configs");
        } catch (Exception e) {
            //TODO:日志
            e.printStackTrace();
            VCDNServerApp.logger.info("读取服务端配置项出错"+e);
        }

        VCDNServerApp.logger.info("完成读取服务端配置项");
        return cfgmsg;
    }


}
