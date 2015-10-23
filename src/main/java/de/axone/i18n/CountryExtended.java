package de.axone.i18n;

import java.beans.Transient;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface CountryExtended extends Country {

	public Set<? extends CountryName> getNames();
	
	public boolean isInEu();
	public boolean isInEurozone();

	public boolean isIncludeVat();
	public boolean isShowVat();

	public String getName( Locale locale );
	public CountryName getCountryName( Locale locale );

	public Locale getDefaultLocale();

	public String getPostalcode();
	public boolean isPostalcodeRequired();

	public String getLanguagesIso2();
	public List<String> getLanguageListIso2();
	
	@Transient
	/**
	 * Returns the name for which the locals
	 * know the country. That is "deutschland", "francais", ...
	 * 
	 * @return The country name localized
	 */
	public String getLocalName();
}
