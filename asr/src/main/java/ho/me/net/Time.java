package ho.me.net;

import java.util.Date;

public class Time {

    private Waqt waqt;
    private Date start;
    private Date end;

    public Time(Waqt waqt) {
        this.waqt = waqt;
    }

    public Time(Waqt waqt, Date start, Date end) {
        this.waqt = waqt;
        this.start = start;
        this.end = end;
    }

    public Waqt getWaqt() {
        return waqt;
    }

    public void setWaqt(Waqt waqt) {
        this.waqt = waqt;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
