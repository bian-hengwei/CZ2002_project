import java.time.DayOfWeek;

enum Week {
	ODD, EVEN, BOTH;
}

public class Time {
	Week week;
	DayOfWeek day;
	int starttime;
    int endtime;

    public Time(Week wk, DayOfWeek dow, int st, int et) {
        week = wk;
        day = dow;
        starttime = st;
        endtime = et;
    }

    public boolean crash(Time other) {
        if ((week == Week.ODD && other.week == Week.EVEN) ||
            (week == Week.EVEN && other.week == Week.ODD) ||
            (day != other.day))
            return false;
        return ((other.starttime < starttime && starttime < other.endtime) ||
            (starttime < other.starttime && other.starttime < endtime));
    }

}
