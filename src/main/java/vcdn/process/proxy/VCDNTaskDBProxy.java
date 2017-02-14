package vcdn.process.proxy;

import vcdn.core.VCDNServerApp;
import vcdn.model.MCStatus;
import vcdn.model.MCTranscodeTask;
import vcdn.process.MCTaskProcessCenter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
     * 获取转码任务
     *
     * @return 返回待转码任务
     */
    public static void getWillTranscodeTask() {
        String dirName = VCDNServerApp.getMediaSourceDir();
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
                    if (str.equals(".mkv") || str.equals(".ts") || str.equals(".mp4") || str.equals(".wmv") || str.equals(".avi")|| str.equals(".dav")) {
                        //match file name
//                        if(name.contains("Assassin.mkv"))
                        if (name.contains("home.mkv"))

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
            mcTranscodeTask.setPreset(VCDNServerApp.getMcCfgXmlName(suffix));
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


        //TODO:从Restful API从服务获取
        //TODO:同时添加条件
        //task = "{\"/worker\":2,\"input\":\"d:\\Program\\Video\\FTP\\home.mkv\",\"output\":\"d:\\Program\\FTP\\MS\\home.mp4\",\"begin\":0,\"end\":10,\"state\":\"encoding\"}";
//        MCTranscodeTask mcTranscodeTask = new MCTranscodeTask();
//        mcTranscodeTask.setInput("e:\\FTP\\Assassin.720p.mkv");
//        mcTranscodeTask.setOutput("e:\\FTP\\MS\\Assassin.720p.mp4");
//        mcTranscodeTask.setPreset("e:\\GPU.xml");
//
//        mcTranscodeTask.setTaskId(2);
//        mcTranscodeTask.setState(MCStatus.MC_JS_PROCESSING);
//        MCTaskProcessCenter.getInstance().addTask(mcTranscodeTask);



//        MCTranscodeTask secondmcTranscodeTask = new MCTranscodeTask();
//        secondmcTranscodeTask.setInput("d:\\Program\\Video\\FTP\\home1.mkv");
//        secondmcTranscodeTask.setOutput("d:\\Program\\Video\\FTP\\MS\\home1.mp4");
//        secondmcTranscodeTask.setTaskId(2);
//        secondmcTranscodeTask.setState(MCStatus.MC_JS_PROCESSING);
//        MCTaskProcessCenter.getInstance().addTask(secondmcTranscodeTask);

    }




}
