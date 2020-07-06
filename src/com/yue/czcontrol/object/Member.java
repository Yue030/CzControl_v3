package com.yue.czcontrol.object;

public class Member {

    /**
     * Member ID.
     */
    private final String id;
    /**
     * Member Name.
     */
    private final String name;
    /**
     * Member Rank.
     */
    private final String rank;
    /**
     * Member Active.
     */
    private final String active;
    /**
     * Member Active All
     */
    private final String active_all;
    /**
     * Member Handler.
     */
    private final String handler;

    /**
     * Member Object.
     * @param uid Member ID
     * @param uName Member Name
     * @param uRank Member Rank
     * @param uActive Member Active
     * @param uHandler Member Handler
     */
    public Member(final String uid, final String uName,
                  final String uRank, final String uActive,
                  final String uActive_all, final String uHandler) {
        this.id = uid;
        this.name = uName;
        this.rank = uRank;
        this.active = uActive;
        this.active_all = uActive_all;
        this.handler = uHandler;
    }


    /**
     * Get Member ID.
     * @return id
     */
    public String getId() {
        return id;
    }
    /**
     * Get Member Name.
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Get Member Rank.
     * @return rank
     */
    public String getRank() {
        return rank;
    }
    /**
     * Get Member Active.
     * @return active
     */
    public String getActive() {
        return active;
    }
    /**
     * Get Member Active all
     * @return ActiveAll
     */
    public String getActive_all(){
        return active_all;
    }
    /**
     * Get Member Handler.
     * @return handler
     */
    public String getHandler() {
        return handler;
    }
}
