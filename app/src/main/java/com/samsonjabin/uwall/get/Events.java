package com.samsonjabin.uwall.get;


import com.parse.ParseFile;

/**
 * Created by AravindRaj on 05-04-2015.
 */
public class Events {

    private String eventname,obj,format,date,venue,desc,time;
    private String hostname,timestamp,con;
    private Integer views;
    private ParseFile event_banner;


    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }
    public void sethostname(String hostname) { this.hostname = hostname;
    }
    public String gethostname() {return hostname; }

    public void setViews(Integer views) { this.views=views;
    }
    public Integer getViews() {return views; }
    public String getVenue(){return venue;}

    public String getTimestamp() {

        return timestamp;
    }

    public String getTime() {

        return time;
    }

    public ParseFile getEvent_banner(){
        return event_banner;
    }

    public void setEvent_banner(ParseFile event_banner){
        this.event_banner=event_banner;
    }

    public void setTime(String time) { this.time=time;
    }

    public void setObjectid(String obj) {this.obj=obj;
    }

    public String getObjectId(){return obj;}

    public void setTimestamp(String format) {
        this.format=format;
    }
    public String getContact(){return con;}
    public void setContact(String con){this.con=con;}
    public String getDate(){return date;}
    public void setDate(String date) {
        this.date=date;
    }
    public void setVenue(String venue){
        this.venue = venue;
    }
    public String getDesc(){return desc;}
    public void setDesc(String desc){this.desc=desc;}
}
