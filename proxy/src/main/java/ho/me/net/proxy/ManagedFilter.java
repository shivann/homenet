package ho.me.net.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("ManagedFilter")
public class ManagedFilter extends AbstractFilter {

    @Autowired
    ProxyUtil proxyUtil;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    @Override
    protected boolean isAuthorizedRequest(HttpServletRequest request) {
        return true;
    }

}
