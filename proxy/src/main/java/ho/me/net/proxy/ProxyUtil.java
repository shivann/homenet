package ho.me.net.proxy;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class ProxyUtil {

    public static boolean isWeekend() {
        return isWeekend(new Date());
    }

    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day == 6 || day == 7;
    }

}
