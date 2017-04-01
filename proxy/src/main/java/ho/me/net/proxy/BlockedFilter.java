package ho.me.net.proxy;

import com.netflix.zuul.ZuulFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Component("BlockedFilter")
public class BlockedFilter extends AbstractFilter {

    private static final List<String> BLOCKED = Arrays.asList(
            "wired.com",
                "amazon.com"
    );


    @Autowired
    ProxyUtil proxyUtil;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    protected boolean isAuthorizedRequest(HttpServletRequest request) {
        return !BLOCKED.contains(getHostName(request.getHeader("host")));
    }

}
