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
 * This class stores username, channelName, counry, mainTopic, and add them to
 * the dataList.
 * 
 * 
 * @author Arshia Saeidifar
 * @version Apr 22, 2025
 * @author Sakdipong Rodphong
 * @version 04.24.2025
 */
public class Influencer {

    // Fields
    private String username;
    private String channelName;
    private String country;
    private String mainTopic;
    private SinglyLinkedList<InfluencerData> dataList;

    /**
     * Constructor for Influencer
     * 
     * @param username
     *            the username of the influencer
     * @param channelName
     *            the channel name of the influencer
     * @param country
     *            the country of the influencer
     * @param mainTopic
     *            the influencer's main topic content
     */
    public Influencer(
        String username,
        String channelName,
        String country,
        String mainTopic) {
        this.username = username;
        this.channelName = channelName;
        this.country = country;
        this.mainTopic = mainTopic;

        this.dataList = new SinglyLinkedList<>();
    }


    /**
     * Return influencer's username
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Return influencer's channel name
     * 
     * @return channelName
     */
    public String getChannelName() {
        return channelName;
    }


    /**
     * Return influencer's country origin
     * 
     * @return country
     */
    public String getCountry() {
        return country;
    }


    /**
     * Return influencer's main content topic
     * 
     * @return mainTopic
     */
    public String getMainTopic() {
        return mainTopic;
    }


    /**
     * Return the linked list that contains all.
     * * @return dataList
     */
    public SinglyLinkedList<InfluencerData> getDataList() {
        return dataList;
    }


    /**
     * Adds a new InfluencerData entry to this influencer's data list
     * 
     * @param data
     *            the InfluencerData object to add
     */
    public void addData(InfluencerData data) {
        dataList.add(data);
    }


    @Override
    public String toString() {
        return "Influencer{" + "username='" + username + '\''
            + ", channelName='" + channelName + '\'' + ", country='" + country
            + '\'' + ", data count=" + dataList.size() + '}';
    }

}
