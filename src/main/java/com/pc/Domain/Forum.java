package com.pc.Domain;

public class Forum {
    private Integer forumID;
    private String userName;
    private String Avater;
    private String Content;

    @Override
    public String toString() {
        return "Forum{" +
                "forumID=" + forumID +
                ", userName='" + userName + '\'' +
                ", Avater='" + Avater + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }

    public Forum() {
    }

    public Forum(Integer forumID, String userName, String avater, String content) {
        this.forumID = forumID;
        this.userName = userName;
        Avater = avater;
        Content = content;
    }

    public Integer getForumID() {
        return forumID;
    }

    public void setForumID(Integer forumID) {
        this.forumID = forumID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvater() {
        return Avater;
    }

    public void setAvater(String avater) {
        Avater = avater;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
