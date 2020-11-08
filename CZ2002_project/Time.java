import java.time.DayOfWeek;
import java.util.Date;

enum Week
{
	ODD, EVEN, BOTH;
}

public class Time
{
	Week week;
	DayOfWeek[] day;
	// 1230 for 12:30 pm, store as [[1230, 1330], [1430, 1530]]
	int[] time;
}