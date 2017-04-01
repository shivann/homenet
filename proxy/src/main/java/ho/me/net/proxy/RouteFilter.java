package ho.me.net.proxy;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class RouteFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(RouteFilter.class);

    @Value("${unauthorized.url.redirect:http://google.com}")
    private String redirect;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        return true;
    }

    private boolean isAuthorizedRequest(HttpServletRequest request) {
        return true;
    }

    private void setRouteHost(RequestContext ctx) throws MalformedURLException {

        String urlS = ctx.getRequest().getRequestURL().toString();
        URL url = new URL(urlS);
        String protocol = url.getProtocol();
        String rootHost = url.getHost();
        int port = url.getPort();
        String portS = (port > 0 ? ":" + port : "");
        ctx.setRouteHost(new URL(protocol + "://" + rootHost + portS));

    }

    public Object run() {

        log.debug(" -- Intercepted request");

        RequestContext ctx = RequestContext.getCurrentContext();

        try {
            if (isAuthorizedRequest(ctx.getRequest())) {
                setRouteHost(ctx); //   fwd
            } else {
                ctx.setRouteHost(new URL(redirect)); // block
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
