package vcdn.model;

import java.util.Objects;

/**
 * Created by zhghl on 2017/1/11.
 */
public class ResultInfo
{
    private String msg;
    private boolean sucess;
    private Object data;

    public ResultInfo(){

    }

    public ResultInfo(String m, boolean s, Object d)
    {
        this.msg=m;
        this.sucess=s;
        this.data=d;
    }
    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public boolean isSucess()
    {
        return sucess;
    }

    public void setSucess(boolean sucess)
    {
        this.sucess = sucess;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
