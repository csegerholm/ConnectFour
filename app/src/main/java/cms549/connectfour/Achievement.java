package cms549.connectfour;


import java.io.Serializable;

public class Achievement implements Serializable {

    private String desc;
    private String name;
    private int index;
    private int circleID;

    public Achievement(String n, String d, int ind, int dID){
        name=n;
        desc=d;
        index = ind;
        circleID = dID;
    }

    public String getDesc(){
        return desc;
    }

    public int getIndex(){
        return index;
    }

    public int getCircleID(){
        return circleID;
    }


    public String getName() {
        return name;
    }
}
