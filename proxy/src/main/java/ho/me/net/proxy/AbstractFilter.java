package ho.me.net.proxy;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;

public abstract class AbstractFilter extends ZuulFilter {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Value("${unauthorized.url.redirect:http://localhost:80}")
    private String redirect;

//    protected boolean _isAuthorizedRequest(HttpServletRequest request) {
//
//        Enumeration<String> headers =  request.getHeaderNames();
//
//        while (headers.hasMoreElements()) {
//            String header = headers.nextElement();
//            if ("host".equalsIgnoreCase(header)) {
//                String hostVal = request.getHeader(header);
//                if (hostVal != null && hostVal.contains(BLOCKED)) {
//                    log.debug(" -- Blocking " + hostVal + " redirecting to " + redirect);
//                    return false;
//                }
//            }
//        }

//        return true;
//    }

    protected abstract boolean isAuthorizedRequest(HttpServletRequest request);


    protected void setRouteHost(RequestContext ctx) throws MalformedURLException {

        String urlS = ctx.getRequest().getRequestURL().toString();
        URL url = new URL(urlS);
        String protocol = url.getProtocol();
        String rootHost = url.getHost();
        int port = url.getPort();
        String portS = (port > 0 ? ":" + port : "");
        ctx.setRouteHost(new URL(protocol + "://" + rootHost + portS));

    }

    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();

        try {
            if (isAuthorizedRequest(ctx.getRequest())) {
                setRouteHost(ctx); //   fwd
            } else {
                log.debug(" -- !! BLOCKING "+ctx.getRequest().getHeader("host"));
                ctx.setRouteHost(new URL(redirect)); // block
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
