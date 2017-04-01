package ho.me.net.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

@Component
public class ProxyUtil {

    protected static Logger log = LoggerFactory.getLogger(ProxyUtil.class);

    public static boolean isWeekend() {
        return isWeekend(new Date());
    }

    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day == 1 || day == 7;
    }

    public static String getHostName(String host) throws URISyntaxException {
        URI uri = new URI(host);
        return uri.getHost();
    }

}
