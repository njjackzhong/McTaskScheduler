package vcdn.model;



/**
 * Created by zhghl on 2017/1/10.
 */
public class VCDNConfig
{

    private int Id;


    private String Key;


    private String Value;

    private Integer Enabled;

    private String Description;

    private String Module;

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
    }

    public String getKey()
    {
        return Key;
    }

    public void setKey(String key)
    {
        Key = key;
    }

    public String getValue()
    {
        return Value;
    }

    public void setValue(String value)
    {
        Value = value;
    }

    public Integer getEnabled()
    {
        return Enabled;
    }

    public void setEnabled(Integer enabled)
    {
        Enabled = enabled;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description)
    {
        Description = description;
    }

    public String getModule()
    {
        return Module;
    }

    public void setModule(String module)
    {
        Module = module;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        return Id == ((VCDNConfig) other).getId();
    }
}
