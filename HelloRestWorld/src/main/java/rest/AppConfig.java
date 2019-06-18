package rest;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Class is the foundation for the REST implementation
 */
@ApplicationPath("/rest")
public class AppConfig extends ResourceConfig {
}
