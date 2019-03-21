package planner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Nikola
 */
public class ConstsAndPaths {
        
    public static final String YT_OPEN_PATH="C:\\Users\\Nikola\\Desktop\\IS1 Projekat Apps\\YouTubeOpen\\YouTubeOpen.jar";
    public static final String JAVA_PATH="java";
    public static final int ID_PLANNER=3;
    public static final int ID_ALARM=2;
    public static final int ID_SONGPLAYER=1;
    public static final int ID_USER= 0;
    public static final String DEFAULT_SONG="I need a dollar";
    public static final String API_KEY="AIzaSyCPfFsAroF2YwnSgE6fmL4FoHcCthTMMUk";
    public static final String API_BASE="https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&";
    public static final String GEO_DB_PATH="C:\\Users\\Nikola\\Desktop\\GeoLite2-City.mmdb";
    public static final String MY_LOCATION="etf beograd";
}
class Actions{
    public static final String listSongs="LIST_SONGS";
    public static final String playSong="PLAY_SONG";
    public static final String stop="STOP";
    public static final String setAlarm="SET_ALARM";
    public static final String changeAlarmRingtone="CHANGE_ALARM_RINGTONE";
    public static final String listEvents="LIST_EVENTS";
    public static final String addEvent="ADD_EVENT";
    public static final String removeEvent="REMOVE_EVENT";
    public static final String modifyEvent="MODIFY_EVENT";
    public static final String timeFromAToB="TIME_FROM_A_TO_B";
    public static final String timeFromMyPosition="TIME_FROM_MY_POSITION";
}
