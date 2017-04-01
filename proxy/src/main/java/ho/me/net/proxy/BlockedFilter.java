package ho.me.net.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Component("BlockedFilter")
public class BlockedFilter extends AbstractFilter {

    private static final List<String> BLOCKED = Arrays.asList(
            "youporn",
                 "xhamster",
                 "pornhub"
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

        try {
            String host = proxyUtil.getHostName(request.getHeader("host"));
            return !blocked(host);
        } catch (URISyntaxException e) {
            log.debug(" -- Error parsing host ", e);
        } catch (Exception e) {
            log.debug(" -- Error " + e);
        }

        return false;
    }

    private boolean blocked(String host) {
        boolean isBlocked = false;

        if (host != null) {
            for (String block: BLOCKED) {
                isBlocked = isBlocked || host.toLowerCase().contains(block.toLowerCase());
            }
        }

        return isBlocked;
    }

}
