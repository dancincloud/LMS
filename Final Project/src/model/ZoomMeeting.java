package model;

/**
 *
 * @author Ke
 */
public class ZoomMeeting {
    private String zoomMeetingID;
    private String name;
    private String link;
    private long createTime;
    private long updateTime;

    public ZoomMeeting(String zoomMeetingID, String name, String link) {
        this.zoomMeetingID = zoomMeetingID;
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getZoomMeetingID() {
        return zoomMeetingID;
    }

    public void setZoomMeetingID(String zoomMeetingID) {
        this.zoomMeetingID = zoomMeetingID;
    }
}
