package top.strelitzia.models;

/**
 * 登录信息封装
 */
public class UserInfo {
    /**
     * 登录id
     */
    private String id;
    /**
     * 登录密码
     */
    private String pwd;
    /**
     * 是否是注册用户
     */
    private Integer isRegister;
    /**
     * 是否是Bot用户
     */
    private Integer isBot;
    /**
     * 是否是管理员
     */
    private Integer isAdmin;

    private String name;

    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(Integer isRegister) {
        this.isRegister = isRegister;
    }

    public Integer getIsBot() {
        return isBot;
    }

    public void setIsBot(Integer isBot) {
        this.isBot = isBot;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
