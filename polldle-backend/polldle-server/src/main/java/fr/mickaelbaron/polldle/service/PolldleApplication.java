package fr.mickaelbaron.polldle.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import fr.mickaelbaron.polldle.CrossDomainFilter;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ApplicationPath("/")
public class PolldleApplication extends javax.ws.rs.core.Application {

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
