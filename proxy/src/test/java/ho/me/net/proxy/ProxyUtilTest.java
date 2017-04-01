package ho.me.net.proxy;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ProxyUtilTest {

    @Test
    public void test_is_weekend() {
        assertThat(ProxyUtil.isWeekend()).isTrue().withFailMessage("isWeekend should be TRUE");
    }

}
