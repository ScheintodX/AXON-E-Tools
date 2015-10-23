package de.axone.i18n;

import java.util.Locale;

public interface CountryName {

	public abstract int getSort();
	public abstract String getName();
	public abstract Locale getLocale();

}