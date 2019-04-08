package com.pc.Domain;

public class Movie {
    private Integer MovieId;
    private Integer ChannelId;
    private String MovieName;
    private String MovieContent;
    private String MoviePicture;
    private String PlayUrl;
    private String MovieDirector;
    private String MovieActors;
    private String MovieTime;
    private Integer PlayedCount;
    private Integer IsRecommend;

    public Integer getMovieId() {
        return MovieId;
    }

    public void setMovieId(Integer movieId) {
        MovieId = movieId;
    }

    public Integer getChannelId() {
        return ChannelId;
    }

    public void setChannelId(Integer channelId) {
        ChannelId = channelId;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getMovieContent() {
        return MovieContent;
    }

    public void setMovieContent(String movieContent) {
        MovieContent = movieContent;
    }

    public String getMoviePicture() {
        return MoviePicture;
    }

    public void setMoviePicture(String moviePicture) {
        MoviePicture = moviePicture;
    }

    public String getPlayUrl() {
        return PlayUrl;
    }

    public void setPlayUrl(String playUrl) {
        PlayUrl = playUrl;
    }

    public String getMovieDirector() {
        return MovieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        MovieDirector = movieDirector;
    }

    public String getMovieActors() {
        return MovieActors;
    }

    public void setMovieActors(String movieActors) {
        MovieActors = movieActors;
    }

    public String getMovieTime() {
        return MovieTime;
    }

    public void setMovieTime(String movieTime) {
        MovieTime = movieTime;
    }

    public Integer getPlayedCount() {
        return PlayedCount;
    }

    public void setPlayedCount(Integer playedCount) {
        PlayedCount = playedCount;
    }

    public Integer getIsRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        IsRecommend = isRecommend;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "MovieId=" + MovieId +
                ", ChannelId=" + ChannelId +
                ", MovieName='" + MovieName + '\'' +
                ", MovieContent='" + MovieContent + '\'' +
                ", MoviePicture='" + MoviePicture + '\'' +
                ", PlayUrl='" + PlayUrl + '\'' +
                ", MovieDirector='" + MovieDirector + '\'' +
                ", MovieActors='" + MovieActors + '\'' +
                ", MovieTime='" + MovieTime + '\'' +
                ", PlayedCount=" + PlayedCount +
                ", IsRecommend=" + IsRecommend +
                '}';
    }
}
