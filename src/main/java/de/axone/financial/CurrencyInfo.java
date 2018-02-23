package de.axone.financial;

import java.util.Currency;
import java.util.Locale;

public abstract class CurrencyInfo {

	public static final Currency EUR = Currency.getInstance( "EUR" );
	public static final Currency GBP = Currency.getInstance( "GBP" );
	public static final Currency USD = Currency.getInstance( "USD" );
	public static final Currency CAD = Currency.getInstance( "CAD" );

	public static boolean isSymbolRight( Currency currency, Locale locale ){

		if( "EUR".equals( currency.getCurrencyCode() ) ){

			if(
					Locale.ITALIAN.equals( locale ) ||
					Locale.ITALY.equals( locale ) ||
					Locale.US.equals( locale ) ||
					Locale.JAPAN.equals( locale ) ||
					Locale.JAPANESE.equals( locale )
			){
				return false;
			} else {
    			return true;
			}

		} else {
			return false;
		}
	}

	public static boolean isSymbolLeft( Currency currency, Locale locale ){

		return ! isSymbolRight( currency, locale );
	}

	public static boolean hasSymbolSpace( Currency currency, Locale locale ){

		if( "GBP".equals( currency.getCurrencyCode() ) ){
			return false;
		} else if( "USD".equals( currency.getCurrencyCode() ) ){
    		return false;
		} else {
			return true;
		}
	}

	public static String asHtml( Currency currency ){

		if( currency.getCurrencyCode().equals( "EUR" ) ){
			return "€";
		} else if( currency.getCurrencyCode().equals( "GBP" ) ){
			return "£";
		} else if( currency.getCurrencyCode().equals( "USD" ) ){
			return "$";
		} else if( currency.getCurrencyCode().equals( "JPY" ) ){
			return "¥";
		} else {
			return currency.getCurrencyCode();
		}
	}
}
