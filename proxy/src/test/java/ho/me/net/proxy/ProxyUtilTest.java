package ho.me.net.proxy;

import org.junit.Test;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

public class ProxyUtilTest {

    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    static final String MONDAY = "19/08/1974";
    static final String SUNDAY = "18/08/1974";
    static final String SATURDAY = "17/08/1974";
    static final String FRIDAY = "16/08/1974";

    @Test
    public void test_is_weekend_returns_true_for_weekend() throws ParseException {

        assertThat(ProxyUtil
                .isWeekend(DATE_FORMAT.parse(SATURDAY)))
                .isTrue()
                .withFailMessage("isWeekend should be TRUE for SATURDAY");

        assertThat(ProxyUtil
                .isWeekend(DATE_FORMAT.parse(SUNDAY)))
                .isTrue()
                .withFailMessage("isWeekend should be TRUE for SUNDAY");

    }

    @Test
    public void test_is_weekend_returns_false_for_weekday() throws ParseException {

        assertThat(ProxyUtil
                .isWeekend(DATE_FORMAT.parse(MONDAY)))
                .isFalse()
                .withFailMessage("isWeekend should be FALSE for MONDAY");

        assertThat(ProxyUtil
                .isWeekend(DATE_FORMAT.parse(FRIDAY)))
                .isFalse()
                .withFailMessage("isWeekend should be FALSE for FRIDAY");

    }

    @Test
    public void test_get_host_name() {

        String testUrl = "http://news.ycombinator.com";

        try {
            assertThat(ProxyUtil.getHostName(testUrl).equals("news.ycombinator.com")).isTrue().withFailMessage("hostname incorrect");
        } catch (URISyntaxException e) {
            fail("Should not get an exception " + e.getMessage());
        }

    }

}
