package ho.me.net.proxy;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ProxyImpl {

    private static Logger log = LoggerFactory.getLogger(ProxyImpl.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(ProxyImpl.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    @Bean("ManagedFilter")
    public ZuulFilter routeFilter() {
        return new ManagedFilter();
    }

    @Bean("BlockedFilter")
    public ZuulFilter blockedFilter() {
        return new BlockedFilter();
    }


}
