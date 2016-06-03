
package in.mobileappdev.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideosResponse {

    private List<Video> videos = new ArrayList<Video>();
    private Integer success;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The videos
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     * 
     * @param videos
     *     The videos
     */
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    /**
     * 
     * @return
     *     The success
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
