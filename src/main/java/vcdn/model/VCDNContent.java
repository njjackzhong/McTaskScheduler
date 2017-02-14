package vcdn.model;

 

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zhghl on 2017/1/10.
 */ 
public class VCDNContent
{

    public int TaskId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date TaskCreateTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date TaskUpdateTime; 
    public Integer Process; 
    public Integer ProcessState;
    public String ProcessStateMsg; 
    public String VCDNNetUrl; 
    public String VCDNConfigKey; 
    public  String SrcFileUuid;


    public  String SrcFileName;

    public String SrcFileType;

    public String SrcSubFileName;

    public String DstFileUuid;

    public String DstFileName;

    public String DstFileType;

    public String DstSubFileName;

    public String Remark01;

    public String Remark02;

    public String Remark03;

    public String Remark04;

    public String Remark05;

    public String Remark06;


    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        return TaskId == ((VCDNContent) other).TaskId;
    }
}
