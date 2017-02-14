package vcdn.model;

/**
 * Created by zhghl on 2017/1/10.
 */
public class VCDNMap
{
    private String configname;

    private String localpath;

    private String netpath;

    public String getConfigname()
    {
        return configname;
    }

    public void setConfigname(String configname)
    {
        this.configname = configname;
    }

    public String getLocalpath()
    {
        return localpath;
    }

    public void setLocalpath(String localpath)
    {
        this.localpath = localpath;
    }

    public String getNetpath()
    {
        return netpath;
    }

    public void setNetpath(String netpath)
    {
        this.netpath = netpath;
    }

    @Override
    public int hashCode() {
        return configname.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        return configname.equals(((VCDNMap) other).getConfigname());
    }
}
