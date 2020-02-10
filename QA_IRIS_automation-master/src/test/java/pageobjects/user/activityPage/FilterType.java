package pageobjects.user.activityPage;

import java.util.logging.Filter;

/**
 * Created by noelc on 2016-11-17.
 */

/**
 * Modified by Shardul Bansal on 2019-05-15
 * Changed the iconClass strings to match those on the Activity Page. Check comment on bottom
 */
public enum FilterType {
    NOTE("q4i-note-4pt",false),
    PHONE("q4i-phone-4pt",false),
    EMAIL("q4i-mail-4pt",false),
    MEETING("q4i-meeting-4pt",false),
    ROADHSHOW("q4i-roadshow-4pt",false),
    CONFERENCE("q4i-conference-4pt",false),
    EARNINGS("q4i-earnings-4pt",false),
    NONE("",false);

    private final String iconClass;
    private boolean checked;

    FilterType(String iconClass,boolean checked){
        this.iconClass=iconClass;
        this.checked= checked;
    }


    public String iconClass(){
        return this.iconClass;
    }

    public void setChecked(boolean value){
        this.checked=value;
    }

    public boolean isChecked() {
        return checked;
    }

    public FilterType returnType (String cssClass){ //from NoteDetailsPage

        switch(cssClass){
            case "cell-category_icon q4i-note-4pt":
                return FilterType.NOTE;
            case "cell-category_icon q4i-phone-4pt":
                return FilterType.PHONE;
            case "cell-category_icon q4i-mail-4pt":
                return FilterType.EMAIL;
            case "cell-category_icon q4i-meeting-4pt":
                return FilterType.MEETING;
            case "cell-category_icon q4i-roadshow-4pt":
                return FilterType.ROADHSHOW;
            case "cell-category_icon q4i-earnings-4pt":
                return FilterType.EARNINGS;
            case "cell-category_icon q4i-conference-4pt" :
                return FilterType.CONFERENCE;
            case "":
                return FilterType.NONE;
            default:
                return null;

        }
    }
    //Please note that the Activity Landing page and the Activity Details page
    //display different iconClasses with
    //Landing page for Note : "q4i-note-4pt"
    //Details page for Note : "q4i-note-2pt" (observed on April 26 2019)


}
