package app.web;

import com.jcabi.aspects.Tv;
import org.takes.HttpException;

import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Created by alexr on 01.05.2017.
 */
final class Destination {

    /**
     * Destination URI.
     */
    private final transient URI uri;

    /**
     * Ctor.
     * @param dst Destination URI (full)
     */
    Destination(final URI dst) {
        this.uri = dst;
    }

    /**
     * Build destination path.
     * @return Destination path
     * @throws HttpException If fails
     */
    public String path() throws HttpException {
        if (!this.uri.isAbsolute()) {
            throw new HttpException(
                HttpURLConnection.HTTP_BAD_REQUEST,
                String.format("URI \"%s\" is not absolute", this.uri)
            );
        }
        final String protocol = this.uri.getScheme();
        if (!"https".equals(protocol) && !"http".equals(protocol)) {
            throw new HttpException(
                HttpURLConnection.HTTP_BAD_REQUEST,
                String.format(
                    "protocol must be either HTTP or HTTPS at \"%s\"",
                    this.uri
                )
            );
        }
        if (this.uri.getHost() == null) {
            throw new HttpException(
                HttpURLConnection.HTTP_BAD_REQUEST,
                String.format("URI \"%s\" doesn't have a host", this.uri)
            );
        }
        final StringBuilder path = new StringBuilder(Tv.HUNDRED);
        if (this.uri.getPath().isEmpty()) {
            path.append('/');
        } else {
            path.append(this.uri.getPath());
        }
        if (this.uri.getQuery() != null) {
            path.append('?').append(this.uri.getQuery());
        }
        if (this.uri.getFragment() != null) {
            path.append('#').append(this.uri.getFragment());
        }
        return path.toString();
    }

}