package ho.me.net.proxy;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ProxyImpl {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ProxyImpl.class).bannerMode(Banner.Mode.OFF).run(args);
    }

}
