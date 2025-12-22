package fr.mickaelbaron.polldle.service;

import java.util.HashSet;
import java.util.Set;

import fr.mickaelbaron.polldle.CrossDomainFilter;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ApplicationPath("/")
public class PolldleApplication extends Application {

	public PolldleApplication() {
		System.out.println("PolldleApplication.PolldleApplication()");
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(PolldlesResourceImpl.class);
		s.add(VotesResourceImpl.class);
		s.add(CrossDomainFilter.class);
		return s;
	}
}
