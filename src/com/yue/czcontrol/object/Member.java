package com.yue.czcontrol.object;

public class Member {

    private String id;
    private String name;
    private String rank;
    private String active;
    private String handler;

    public Member(){
        this.id = "";
        this.name = "";
        this.rank = "";
        this.active = "";
        this.handler = "";
    }

    public Member(String id, String name, String rank, String active, String handler){
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.active = active;
        this.handler = handler;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
