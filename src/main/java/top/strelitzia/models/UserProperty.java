package top.strelitzia.models;

/**
 * 更详细的个人信息
 */
public class UserProperty {
    /**
     * 登录id
     */
    private String id;

    /**
     * 拥有的token数量
     */
    private Integer token;

    /**
     * 拥有的下载次数
     */
    private Integer download;

    private String apiKey;


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }
}
