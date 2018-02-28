package com.lovecoding.yangying.ImageDetail;

/**
 * Created by yangying on 18/2/26.
 */

public class CommentsInfo {
    private int commentId = 0;
    private int userId;
    private int imageId;
    private int replyToComment;
    private String content;
    private String time;
    private int hostComment = 0;

    public CommentsInfo(int commentId, int userId, int imageId, int replyToComment, String content, String time, int hostComment) {
        this.commentId = commentId;
        this.userId = userId;
        this.imageId = imageId;
        this.replyToComment = replyToComment;
        this.content = content;
        this.time = time;
        this.hostComment = hostComment;
    }

    public CommentsInfo(int userId, int imageId, int replyToComment, String content, String time, int hostComment) {
        this.userId = userId;
        this.imageId = imageId;
        this.replyToComment = replyToComment;
        this.content = content;
        this.time = time;
        this.hostComment = hostComment;
    }

    public CommentsInfo() {}

    public void setCommentId (int commentId) {this.commentId = commentId; }
    public void setImageId (int imageId) {this.imageId = imageId; }
    public void setUserId (int userId) {this.userId = userId; }
    public void setReplyToComment (int replyToComment) {this.replyToComment = replyToComment; }
    public void setContent (String content) {this.content = content;}
    public void setTime (String time) {this.time = time;}
    public void setHostComment (int hostComment) {this.hostComment = hostComment;}
    public int getCommentId() {return this.commentId;}
    public int getImageId() {return this.imageId;}
    public int getUserId() {return this.userId;}
    public int getReplyToComment() {return this.replyToComment;}
    public String getContent() {return this.content;}
    public String getTime() {return this.time;}
    public int getHostComment() {return this.hostComment;}
}
