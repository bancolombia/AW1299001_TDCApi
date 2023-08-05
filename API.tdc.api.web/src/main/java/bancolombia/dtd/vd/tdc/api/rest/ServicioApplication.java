package bancolombia.dtd.vd.tdc.api.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
public class ServicioApplication extends Application {
    
	 @Override
	    public Set<Class<?>> getClasses() {
	        Set<Class<?>> classes = new HashSet<>();
	        classes.add(ControladorDatosCliente.class);
	        return classes;
	    }
}

