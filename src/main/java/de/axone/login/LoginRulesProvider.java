package de.axone.login;

import java.util.Collection;

@FunctionalInterface
public interface LoginRulesProvider {

	public Collection<String> getRules( LoginUser user );
}
