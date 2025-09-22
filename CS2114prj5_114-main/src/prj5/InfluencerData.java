package prj5;
//Virginia Tech Honor Code Pledge:
//
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal,
//nor will I accept the actions of those who do.
//Arshia Saeidifar arshias@vt.edu
//Prince princeg@vt.edu
//Sakdipong Rodphong Sakdipong@vt.edu
//Zaybish  Tariq Zaybish@vt.edu




/**
 * Represents one month's worth of data for a social media influencer.
 * 
 * @author Arshia Saeidifar
 * @version Apr 22, 2025
 * @author Sakdipong Rodphong
 * @version 04.24.2025
 */
public class InfluencerData {

    // Fields
    private String month;
    private int likes;
    private int posts;
    private int followers;
    private int comments;
    private int views;

    /**
     * Constructor for InfluencerData
     * 
     * @param month
     *            the month the data is from
     * @param likes
     *            number of likes
     * @param posts
     *            number of posts
     * @param followers
     *            number of followers
     * @param comments
     *            number of comments
     * @param views
     *            number of views
     */
    public InfluencerData(
        String month,
        int likes,
        int posts,
        int followers,
        int comments,
        int views) {
        this.month = month;
        this.likes = likes;
        this.posts = posts;
        this.followers = followers;
        this.comments = comments;
        this.views = views;
    }


    /**
     * Return month
     * 
     * @return month
     */
    public String getMonth() {
        return month;
    }


    /**
     * Return total likes in selected month
     * 
     * @return likes
     */
    public int getLikes() {
        return likes;
    }


    /**
     * Return the number of posts in selected month
     * 
     * @return posts
     */
    public int getPosts() {
        return posts;
    }


    /**
     * Reuturn the number of followers
     * 
     * @return followers
     */
    public int getFollowers() {
        return followers;
    }


    /**
     * Return the number of comments for selected month
     * 
     * @return comments
     */
    public int getComments() {
        return comments;
    }


    /**
     * /**
     * Return the number of views for selected month
     * 
     * @return views
     */
    public int getViews() {
        return views;
    }


    @Override
    public String toString() {
        return "InfluencerData{" + "month='" + month + '\'' + ", likes=" + likes
            + ", posts=" + posts + ", followers=" + followers + ", comments="
            + comments + ", views=" + views + '}';
    }
}
