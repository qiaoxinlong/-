/**
 *
 */
package SmallApp.BaseUtil;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * @author cuixiaohui
 *
 */
public class MyHttpDelete extends HttpEntityEnclosingRequestBase {

    public static final String METHOD_NAME = "DELETE";

    /* (non-Javadoc)
     * @see org.apache.http.client.methods.HttpRequestBase#getMethod()
     */
    @Override
    public String getMethod() {
        // TODO Auto-generated method stub
        return METHOD_NAME;
    }

    public MyHttpDelete(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public MyHttpDelete(final URI uri) {
        super();
        setURI(uri);
    }

    public MyHttpDelete() {
        super();
    }
}
