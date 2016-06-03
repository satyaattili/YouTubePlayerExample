
package in.mobileappdev.models;

import java.util.HashMap;
import java.util.Map;

public class Video {

    private String id;
    private String video_id;
    private String video_title;
    private String video_duration;
    private String cid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The video_id
     */
    public String getVideo_id() {
        return video_id;
    }

    /**
     * 
     * @param video_id
     *     The video_id
     */
    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    /**
     * 
     * @return
     *     The video_title
     */
    public String getVideo_title() {
        return video_title;
    }

    /**
     * 
     * @param video_title
     *     The video_title
     */
    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    /**
     * 
     * @return
     *     The video_duration
     */
    public String getVideo_duration() {
        return video_duration;
    }

    /**
     * 
     * @param video_duration
     *     The video_duration
     */
    public void setVideo_duration(String video_duration) {
        this.video_duration = video_duration;
    }

    /**
     * 
     * @return
     *     The cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * 
     * @param cid
     *     The cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
