package com.demo.scrapper;

public class YoutubeVideo {

    public String download_link;
    public Integer quality;

    public String getVideoLink() {
        return videoLink;
    }

    private final String videoLink;
    public String video_id;
    public String title;

    public YoutubeVideo(String video_id) {
        this.video_id = video_id;
        this.videoLink = "https://www.youtube.com/watch?v=" + video_id;
    }
}
