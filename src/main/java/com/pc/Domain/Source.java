package com.pc.Domain;


public class Source {
    private Integer SourceId;
    private String SourceName;
    private String SourceUrl;
    private Integer ChannelId;

    public Integer getSourceId() {
        return SourceId;
    }

    public void setSourceId(Integer sourceId) {
        SourceId = sourceId;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String sourceName) {
        SourceName = sourceName;
    }

    public String getSourceUrl() {
        return SourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        SourceUrl = sourceUrl;
    }

    public Integer getChannelId() {
        return ChannelId;
    }

    public void setChannelId(Integer channelId) {
        ChannelId = channelId;
    }

    @Override
    public String toString() {
        return "Source{" +
                "SourceId=" + SourceId +
                ", SourceName='" + SourceName + '\'' +
                ", SourceUrl='" + SourceUrl + '\'' +
                ", ChannelId=" + ChannelId +
                '}';
    }
}
