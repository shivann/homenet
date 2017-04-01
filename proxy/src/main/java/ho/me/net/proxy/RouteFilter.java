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
import java.util.Enumeration;

@Component
public class RouteFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(RouteFilter.class);

    private static final String BLOCKED = "iol";

    @Value("${unauthorized.url.redirect:http://news.ycombinator.com}")
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

        Enumeration<String> headers =  request.getHeaderNames();

        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            if ("host".equalsIgnoreCase(header)) {
                String hostVal = request.getHeader(header);
                if (hostVal != null && hostVal.contains(BLOCKED)) {
                    log.debug(" -- Blocking " + hostVal + " redirecting to " + redirect);
                    return false;
                }
            }
        }

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
