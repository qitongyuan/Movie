package com.pc.Domain;

import java.util.Date;

public class Comment {
    private Integer CommentId;
    private String CommentNickName;
    private String CommentEmail;
    private String CommentContent;
    private Date CommentDate;
    private Integer MovieId;

    public Date getCommentDate() {
        return CommentDate;
    }

    public void setCommentDate(Date commentDate) {
        CommentDate = commentDate;
    }

    public Integer getCommentId() {
        return CommentId;
    }

    public void setCommentId(Integer commentId) {
        CommentId = commentId;
    }

    public String getCommentNickName() {
        return CommentNickName;
    }

    public void setCommentNickName(String commentNickName) {
        CommentNickName = commentNickName;
    }

    public String getCommentEmail() {
        return CommentEmail;
    }

    public void setCommentEmail(String commentEmail) {
        CommentEmail = commentEmail;
    }

    public String getCommentContent() {
        return CommentContent;
    }

    public void setCommentContent(String commentContent) {
        CommentContent = commentContent;
    }

    public Integer getMovieId() {
        return MovieId;
    }

    public void setMovieId(Integer movieId) {
        MovieId = movieId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "CommentId=" + CommentId +
                ", CommentNickName='" + CommentNickName + '\'' +
                ", CommentEmail='" + CommentEmail + '\'' +
                ", CommentContent='" + CommentContent + '\'' +
                ", MovieId=" + MovieId +
                '}';
    }
}
