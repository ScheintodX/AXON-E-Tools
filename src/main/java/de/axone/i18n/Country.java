package de.axone.i18n;

import java.util.Currency;


public interface Country {
	
	public String getCommonName();

	public String getIso2();

	public String getIso3();

	public int getIsoN();

	public String getCcTld();

	public Currency getCurrency();

}