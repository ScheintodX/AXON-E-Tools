package de.axone.i18n;

import static de.axone.i18n.Language.*;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

import de.axone.tools.E;

/**
 * Note, that this easily deprecates!
 * 
 * Only use if you don't expect stable or accurate results or know what you are doing
 * 
 * @author flo
 */
public enum StaticCountries implements Country {
	
	// -- START GENERATED --
	AF( "Afghanistan", "AF", "AFG", 4, "af", "AFN", null , fa, en ),
	AX( "Åland Islands", "AX", "ALA", 248, "ax", "EUR", "nnnnn" , sv, en ),
	AL( "Albania", "AL", "ALB", 8, "al", "ALL", "nnnn" , sq, en ),
	DZ( "Algeria", "DZ", "DZA", 12, "dz", "DZD", "nnnnn" , ar, fr ),
	AS( "American Samoa", "AS", "ASM", 16, "as", "USD", "us" , sm, en ),
	AD( "Andorra", "AD", "AND", 20, "ad", "EUR", "ADnnn" , ca, es, fr ),
	AO( "Angola", "AO", "AGO", 24, "ao", "AOA", null  ),
	AI( "Anguilla", "AI", "AIA", 660, "ai", "XCD", "AI-2640"  ),
	AG( "Antigua and Barbuda", "AG", "ATG", 28, "ag", "XCD", null  ),
	AR( "Argentina", "AR", "ARG", 32, "ar", "ARS", "annnnaaa" , en ),
	AM( "Armenia", "AM", "ARM", 51, "am", "AMD", "nnnn"  ),
	AW( "Aruba", "AW", "ABW", 533, "aw", "ANG", null  ),
	AU( "Australia", "AU", "AUS", 36, "au", "AUD", "nnnn" , en ),
	AT( "Austria", "AT", "AUT", 40, "at", "EUR", "nnnn" , de ),
	AZ( "Azerbaijan", "AZ", "AZE", 31, "az", "AZN", "AZnnnn"  ),
	BS( "Bahamas", "BS", "BHS", 44, "bs", "BSD", null  ),
	BH( "Bahrain", "BH", "BHR", 48, "bh", "BHD", "999(9)?"  ),
	BD( "Bangladesh", "BD", "BGD", 50, "bd", "BDT", "nnnn"  ),
	BB( "Barbados", "BB", "BRB", 52, "bb", "BBD", "BBnnnnn"  ),
	BY( "Belarus", "BY", "BLR", 112, "by", "BYR", "nnnnnn" , be, ru, en ),
	BE( "Belgium", "BE", "BEL", 56, "be", "EUR", "nnnn" , nl, fr, de ),
	BZ( "Belize", "BZ", "BLZ", 84, "bz", "BZD", null  ),
	BJ( "Benin", "BJ", "BEN", 204, "bj", "XOF", null  ),
	BM( "Bermuda", "BM", "BMU", 60, "bm", "BMD", "aa(nn|aa)"  ),
	BT( "Bhutan", "BT", "BTN", 64, "bt", "BTN", null  ),
	BO( "Bolivia", "BO", "BOL", 68, "bo", "BOB", "nnnn"  ),
	BA( "Bosnia and Herzegovina", "BA", "BIH", 70, "ba", "EUR", "nnnnn" , bs, hr, sr, de, en ),
	BW( "Botswana", "BW", "BWA", 72, "bw", "BWP", null  ),
	BV( "Bouvet Island", "BV", "BVT", 74, "bv", "NOK", null  ),
	BR( "Brazil", "BR", "BRA", 76, "br", "BRL", "nnnnn(-nnn)?" , en ),
	IO( "British Indian Ocean Territory", "IO", "IOT", 86, "io", "USD", "BBND 1ZZ"  ),
	BN( "Brunei Darussalam", "BN", "BRN", 96, "bn", "BND", "aannnn"  ),
	BG( "Bulgaria", "BG", "BGR", 100, "bg", "BGN", "nnnn" , bg, de, en ),
	BF( "Burkina Faso", "BF", "BFA", 854, "bf", "XOF", null  ),
	BI( "Burundi", "BI", "BDI", 108, "bi", "BIF", null  ),
	KH( "Cambodia", "KH", "KHM", 116, "kh", "KHR", "nnnnn"  ),
	CM( "Cameroon", "CM", "CMR", 120, "cm", "XAF", null  ),
	CA( "Canada", "CA", "CAN", 124, "ca", "CAD", "ana nan" , en, fr ),
	CV( "Cape Verde", "CV", "CPV", 132, "cv", "CVE", "nnnn"  ),
	KY( "Cayman Islands", "KY", "CYM", 136, "ky", "KYD", "KYn-nnnn"  ),
	CF( "Central African Republic", "CF", "CAF", 140, "cf", "XAF", null  ),
	TD( "Chad", "TD", "TCD", 148, "td", "XAF", "nnnnn"  ),
	CL( "Chile", "CL", "CHL", 152, "cl", "CLP", "nnnnnnn" , en ),
	CN( "China", "CN", "CHN", 156, "cn", "CNY", "nnnnnn"  ),
	CX( "Christmas Island", "CX", "CXR", 162, "cx", "AUD", "nnnn"  ),
	CC( "Cocos (Keeling) Islands", "CC", "CCK", 166, "cc", "AUD", "nnnn"  ),
	CO( "Colombia", "CO", "COL", 170, "co", "COP", "nnnnnn" , en ),
	KM( "Comoros", "KM", null, 174, "km", "KMF", null  ),
	CG( "Congo", "CG", "COG", 178, "cg", "XAF", null  ),
	CD( "Congo, Democratic Republic of the", "CD", "COD", 180, "cd", "CDF", null  ),
	CK( "Cook Islands", "CK", "COK", 184, "ck", "NZD", null  ),
	CR( "Costa Rica", "CR", "CRI", 188, "cr", "CRC", "nnnnn"  ),
	HR( "Croatia", "HR", "HRV", 191, "hr", "HRK", "nnnnn" , sr, de, en ),
	CU( "Cuba", "CU", "CUB", 192, "cu", "CUP", "nnnnn"  ),
	CY( "Cyprus", "CY", "CYP", 196, "cy", "EUR", "nnnn" , el, tr, en ),
	CZ( "Czech Republic", "CZ", "CZE", 203, "cz", "CZK", "nnn nn" , cs ),
	DK( "Denmark", "DK", "DNK", 208, "dk", "DKK", "nnnn" , da, en ),
	DJ( "Djibouti", "DJ", "DJI", 262, "dj", "DJF", null  ),
	DM( "Dominica", "DM", "DMA", 212, "dm", "XCD", null  ),
	DO( "Dominican Republic", "DO", "DOM", 214, "do", "DOP", "nnnnn"  ),
	EC( "Ecuador", "EC", "ECU", 218, "ec", "USD", "ECnnnnnn" , en ),
	EG( "Egypt", "EG", "EGY", 818, "eg", "EGP", "nnnnn" , en ),
	SV( "El Salvador", "SV", "SLV", 222, "sv", "SVC", "nnnn"  ),
	GQ( "Equatorial Guinea", "GQ", "GNQ", 226, "gq", "XAF", null  ),
	ER( "Eritrea", "ER", "ERI", 232, "er", "ERN", null  ),
	EE( "Estonia", "EE", "EST", 233, "ee", "EUR", "nnnnn" , et, en, de ),
	ET( "Ethiopia", "ET", "ETH", 231, "et", "ETB", "nnnn"  ),
	FK( "Falkland Islands (Malvinas)", "FK", "FLK", 238, "fk", "FKP", "FIQQ 1ZZ"  ),
	FO( "Faroe Islands", "FO", "FRO", 234, "fo", "DKK", "nnn" , fo, da, en ),
	FJ( "Fiji", "FJ", "FJI", 242, "fj", "FJD", null  ),
	FI( "Finland", "FI", "FIN", 246, "fi", "EUR", "nnnnn" , fi, sv, en ),
	FR( "France", "FR", "FRA", 250, "fr", "EUR", "fr" , fr ),
	GF( "French Guiana", "GF", "GUF", 254, "gf", "EUR", "fr"  ),
	PF( "French Polynesia", "PF", "PYF", 258, "pf", "XPF", "fr"  ),
	TF( "French Southern Territories", "TF", "ATF", 260, "tf", "EUR", null  ),
	GA( "Gabon", "GA", "GAB", 266, "ga", "XAF", "nn nn"  ),
	GM( "Gambia", "GM", "GMB", 270, "gm", "GMD", null  ),
	GE( "Georgia", "GE", "GEO", 268, "ge", "GEL", "nnnn"  ),
	DE( "Germany", "DE", "DEU", 276, "de", "EUR", "nnnnn" , de ),
	GH( "Ghana", "GH", "GHA", 288, "gh", "GHC", null  ),
	GI( "Gibraltar", "GI", "GIB", 292, "gi", "GIP", null , en, es ),
	GR( "Greece", "GR", "GRC", 300, "gr", "EUR", "nnnnn" , el, en ),
	GL( "Greenland", "GL", "GRL", 304, "gl", "DKK", "nnnn" , en ),
	GD( "Grenada", "GD", "GRD", 308, "gd", "XCD", null  ),
	GP( "Guadeloupe", "GP", "GLP", 312, "gp", "EUR", "fr"  ),
	GU( "Guam", "GU", "GUM", 316, "gu", "USD", "us"  ),
	GT( "Guatemala", "GT", "GTM", 320, "gt", "GTQ", "nnnnn"  ),
	GG( "Guernsey, Bailiwick of", "GG", "GGY", 831, "gg", "GBP", "uk{GY}" , en ),
	GN( "Guinea", "GN", "GIN", 324, "gn", "GNF", null  ),
	GW( "Guinea-Bissau", "GW", "GNB", 624, "gw", "XOF", "nnnn"  ),
	GY( "Guyana", "GY", "GUY", 328, "gy", "GYD", null  ),
	HT( "Haiti", "HT", "HTI", 332, "ht", "USD", "nnnn"  ),
	HM( "Heard and Mc Donald Islands", "HM", "HMD", 334, "hm", "AUD", "nnnn"  ),
	VA( "Vatican City State", "VA", "VAT", 336, "va", "EUR", "120" , en ),
	HN( "Honduras", "HN", "HND", 340, "hn", "HNL", "nnnnn"  ),
	HK( "Hong Kong", "HK", "HKG", 344, "hk", "HNL", "999077"  ),
	HU( "Hungary", "HU", null, 348, "hu", "HUF", "nnnn" , hu, de, en ),
	IS( "Iceland", "IS", "ISL", 352, "is", "ISK", "nnn" , is, en, da ),
	IN( "India", "IN", "IND", 356, "in", "INR", "nnn nnn" , en ),
	ID( "Indonesia", "ID", "IDN", 360, "id", "IDR", "nnnnn"  ),
	IR( "Iran", "IR", "IRN", 364, "ir", "IRR", "nnnnn nnnnn"  ),
	IQ( "Iraq", "IQ", "IRQ", 368, "iq", "IQD", "nnnnn"  ),
	IE( "Ireland", "IE", "IRL", 372, "ie", "EUR", "xnx xxxx" , en ),
	IM( "Isle of Man", "IM", "IMN", 833, "im", "GBP", "uk{IM}" , en ),
	IL( "Israel", "IL", "ISR", 376, "il", "ILS", "nnnnn" , en ),
	IT( "Italy", "IT", "ITA", 380, "it", "EUR", "nnnnn" , it ),
	CI( "Îvory Coast", "CI", "CIV", 384, "ci", "XOF", null  ),
	JM( "Jamaica", "JM", "JAM", 388, "jm", "JMD", "n?n?"  ),
	JP( "Japan", "JP", "JPN", 392, "jp", "JPY", "nnn-nnnn" , en ),
	JE( "Jersey", "JE", "JEY", 832, "je", "GBP", "uk{JE}" , en ),
	JO( "Jordan", "JO", "JOR", 400, "jo", "JOD", "nnnnn"  ),
	KZ( "Kazakhstan", "KZ", "KAZ", 398, "kz", "KZT", "nnnnnn"  ),
	KE( "Kenya", "KE", "KEN", 404, "ke", "KES", "nnnnn"  ),
	KI( "Kiribati", "KI", "KIR", 296, "ki", "AUD", null  ),
	KP( "Korea North", "KP", "PRK", 408, "kp", "KPW", null  ),
	KR( "Korea South", "KR", "KOR", 410, "kr", "KRW", "nnn-nnn" , en ),
	KW( "Kuwait", "KW", "KWT", 414, "kw", "KWD", "nnnnn"  ),
	KG( "Kyrgyzstan", "KG", "KGZ", 417, "kg", "KGS", "nnnnnn"  ),
	LA( "Lao People's Democratic Republic", "LA", "LAO", 418, "la", "LAK", "nnnnn"  ),
	LV( "Latvia", "LV", "LVA", 428, "lv", "LVL", "nnnn" , lv, de, en ),
	LB( "Lebanon", "LB", "LBN", 422, "lb", "LBP", "nnnn( nnnn)?"  ),
	LS( "Lesotho", "LS", "LSO", 426, "ls", "LSL", "nnn"  ),
	LR( "Liberia", "LR", "LBR", 430, "lr", "LRD", "nnnn"  ),
	LY( "Libyan Arab Jamahiriya", "LY", "LBY", 434, "ly", "LYD", "nnnnn"  ),
	LI( "Liechtenstein", "LI", "LIE", 438, "li", "CHF", "nnnn" , de ),
	LT( "Lithuania", "LT", "LTU", 440, "lt", "LTL", "nnnnn" , lt, ru, en ),
	LU( "Luxembourg", "LU", "LUX", 442, "lu", "EUR", "nnnn" , lb, de, fr ),
	MO( "Macao", "MO", "MAC", 446, "mo", "MOP", "999078"  ),
	MK( "Macedonia", "MK", "MKD", 807, "mk", "MKD", "nnnn" , mk, en, it ),
	MG( "Madagascar", "MG", "MDG", 450, "mg", "MGA", "nnn"  ),
	MW( "Malawi", "MW", "MWI", 454, "mw", "MWK", null  ),
	MY( "Malaysia", "MY", "MYS", 458, "my", "MYR", "nnnnn"  ),
	MV( "Maldives", "MV", "MDV", 462, "mv", "MVR", "nn-nn"  ),
	ML( "Mali", "ML", "MLI", 466, "ml", "XOF", null  ),
	MT( "Malta", "MT", "MDV", 470, "mv", "MVR", "aaa nnnn" , mt, en,  it ),
	MH( "Marshall Islands", "MH", "MHL", 584, "mh", "USD", "us"  ),
	MQ( "Martinique", "MQ", "MTQ", 474, "mq", "EUR", "fr"  ),
	MR( "Mauritania", "MR", "MRT", 478, "mr", "MRO", null  ),
	MU( "Mauritius", "MU", "MUS", 480, "mu", "MUR", null  ),
	YT( "Mayotte", "YT", "MYT", 175, "yt", "EUR", "fr"  ),
	MX( "Mexico", "MX", "MEX", 484, "mx", "MXN", "nnnnn" , en ),
	FM( "Micronesia", "FM", "FSM", 583, "fm", "USD", "us"  ),
	MD( "Moldova", "MD", "MDA", 498, "md", "MDL", "nnnn"  ),
	MC( "Monaco", "MC", "MCO", 492, "mc", "EUR", "fr{MC}" , fr, it, en ),
	MN( "Mongolia", "MN", "MNG", 496, "mn", "MNT", "nnnnnn"  ),
	ME( "Montenegro", "ME", "MNE", 499, "me", "EUR", "nnnnn"  ),
	MS( "Montserrat", "MS", "MSR", 500, "ms", "XCD", null  ),
	MA( "Morocco", "MA", "MAR", 504, "ma", "MAD", "nnnnn"  ),
	MZ( "Mozambique", "MZ", "MOZ", 508, "mz", "MZM", "nnnnn"  ),
	MM( "Myanmar", "MM", "MMR", 104, "mm", "MMK", "nnnnn"  ),
	NA( "Namibia", "NA", "NAM", 516, "na", "ZAR", null  ),
	NR( "Nauru", "NR", "NRU", 520, "nr", "AUD", null  ),
	NP( "Nepal", "NP", "NPL", 524, "np", "NPR", "nnnnn"  ),
	NL( "Netherlands", "NL", "NLD", 528, "nl", "EUR", "nnnn aa" , nl, de, en ),
	AN( "Netherlands Antilles", "AN", "ANT", 530, "an", "ANG", null  ),
	NC( "New Caledonia", "NC", "NCL", 540, "nc", "XPF", "fr"  ),
	NZ( "New Zealand", "NZ", "NZL", 554, "nz", "NZD", "nnnn" , en ),
	NI( "Nicaragua", "NI", "NIC", 558, "ni", "NIO", "nnn-nnn-n"  ),
	NE( "Niger", "NE", "NER", 562, "ne", "XOF", "nnnn"  ),
	NG( "Nigeria", "NG", "NGA", 566, "ng", "NGN", "nnnnnn"  ),
	NU( "Niue", "NU", "NIU", 570, "nu", "NZD", null  ),
	NF( "Norfolk Island", "NF", "NFK", 574, "nf", "AUD", "nnnn"  ),
	MP( "Northern Mariana Islands", "MP", "MNP", 580, "mp", "USD", "us"  ),
	NO( "Norway", "NO", "NOR", 578, "no", "NOK", "nnnn" , no ),
	OM( "Oman", "OM", "OMN", 512, "om", "OMR", "nnn"  ),
	PK( "Pakistan", "PK", "PAK", 586, "pk", "PKR", "nnnnn"  ),
	PS( "Palastinian Territories", "PS", "PSE", 275, "ps", "ILS", null  ),
	PW( "Palau", "PW", "PLW", 585, "pw", "USD", "us"  ),
	PA( "Panama", "PA", "PAN", 591, "pa", "USD", null  ),
	PG( "Papua New Guinea", "PG", "PNG", 598, "pg", "PGK", "nnn"  ),
	PY( "Paraguay", "PY", "PRY", 600, "py", "PYG", "nnnn"  ),
	PE( "Peru", "PE", "PER", 604, "pe", "USD", "(nn)?" , en ),
	PH( "Philippines", "PH", "PHL", 608, "ph", "PHP", "nnnn" , en ),
	PN( "Pitcairn", "PN", "PCN", 612, "pn", "NZD", "PCRN 1ZZ"  ),
	PL( "Poland", "PL", "POL", 616, "pl", "PLN", "nn-nnn" , pl, ru, en, de ),
	PT( "Portugal", "PT", "PRT", 620, "pt", "EUR", "nnnn-nnn" , pt, es, en, fr ),
	PR( "Puerto Rico", "PR", "PRI", 630, "pr", "USD", "us"  ),
	QA( "Qatar", "QA", "QAT", 634, "qa", "QAR", null  ),
	RE( "Réunion", "RE", "REU", 638, "re", "EUR", "fr"  ),
	RO( "Romania", "RO", "ROU", 642, "ro", "RON", "nnnnnn" , ro, en, fr, de, it, es ),
	RU( "Russian Federation", "RU", "RUS", 643, "ru", "RUB", "nnnnnn" , en ),
	RW( "Rwanda", "RW", "RWA", 646, "rw", "RWF", null  ),
	SH( "Saint Helena", "SH", "SHN", 654, "sh", "SHP", "STHL 1ZZ"  ),
	KN( "Saint Kitts and Nevis", "KN", "KNA", 659, "kn", "XCD", null  ),
	LC( "Saint Lucia", "LC", "LCA", 662, "lc", "XCD", null  ),
	PM( "Saint Pierre and Miquelon", "PM", "SPM", 666, "pm", "EUR", "fr"  ),
	VC( "Saint Vincent and the Grenadines", "VC", "VCT", 670, "vc", "XCD", null  ),
	WS( "Samoa", "WS", "WSM", 882, "ws", "WST", "us"  ),
	SM( "San Marino", "SM", "SMR", 674, "sm", "EUR", "nnnnn" , it, en ),
	ST( "Sao Tome and Principe", "ST", "STP", 678, "st", "STD", null  ),
	SA( "Saudi Arabia", "SA", "SAU", 682, "sa", "SAR", "nnnnn"  ),
	SN( "Senegal", "SN", "SEN", 686, "sn", "XOF", "nnnnn"  ),
	RS( "Serbia", "RS", "SRB", 688, "rs", "EUR", "nnnnn" , en ),
	SC( "Seychelles", "SC", "SYC", 690, "sc", "SCR", null  ),
	SL( "Sierra Leone", "SL", "SLE", 694, "sl", "SLL", null  ),
	SG( "Singapore", "SG", "SGP", 702, "sg", "SGD", "nnnnnn"  ),
	SK( "Slovakia", "SK", "SVK", 703, "sk", "SKK", "nnn nn" , sk, en, de ),
	SI( "Slovenia", "SI", "SVN", 705, "si", "EUR", "nnnn" , sl, hr, en, de ),
	SB( "Solomon Islands", "SB", "SLB", 90, "sb", "SBD", null  ),
	SO( "Somalia", "SO", "SOM", 706, "so", "SOS", null  ),
	ZA( "South Africa", "ZA", "ZAF", 710, "za", "ZAR", "nnnn" , en ),
	GS( "South Georgia and Sandwich Islands", "GS", "SGS", 239, null, "GBP", "SIQQ 1ZZ"  ),
	ES( "Spain", "ES", "ESP", 724, "es", "EUR", "nnnnn" , es, en ),
	LK( "Sri Lanka", "LK", "LKA", 144, "lk", "LKR", "nnnnn"  ),
	SD( "Sudan", "SD", "SDN", 736, "sd", "SDD", "nnnn"  ),
	SR( "Suriname", "SR", "SUR", 740, "sr", "SRD", null  ),
	SJ( "Svalbard and Jan Mayen Islands", "SJ", "SJM", 744, "sj", "NOK", null  ),
	SZ( "Swaziland", "SZ", "SWZ", 748, "sz", "SZL", "annn"  ),
	SE( "Sweden", "SE", "SWE", 752, "se", "SEK", "nnn nn" , sv, en, de, fr ),
	CH( "Switzerland", "CH", "CHE", 756, "ch", "CHF", "nnnn" , de ),
	SY( "Syrian Arab Republic", "SY", "SYR", 760, "sy", "SYP", null  ),
	TW( "Taiwan", "TW", "TWN", 158, "tw", "TWD", "nnnnn"  ),
	TJ( "Tajikistan", "TJ", "TJK", 762, "tj", "RUB", "nnnnnn"  ),
	TZ( "Tanzania", "TZ", "TZA", 834, "tz", "TZS", null  ),
	TH( "Thailand", "TH", "THA", 764, "th", "THB", "nnnnn" , en ),
	TL( "Timor-Leste", "TL", "TLS", 626, "tl", "IDR", null  ),
	TG( "Togo", "TG", "TGO", 768, "tg", "XOF", null  ),
	TK( "Tokelau", "TK", "TKL", 772, "tk", "NZD", null  ),
	TO( "Tonga", "TO", "TON", 776, "to", "TOP", null  ),
	TT( "Trinidad and Tobago", "TT", "TTO", 780, "tt", "TTD", null  ),
	TN( "Tunisia", "TN", "TUN", 788, "tn", "TND", "nnnn"  ),
	TR( "Turkey", "TR", "TUR", 792, "tr", "TRY", "nnnnn" , en ),
	TM( "Turkmenistan", "TM", "TKM", 795, "tm", "TMM", "nnnnnn"  ),
	TC( "Turks and Caicos Islands", "TC", "TCA", 796, "tc", "USD", "TKCA 1ZZ"  ),
	TV( "Tuvalu", "TV", "TUV", 798, "tv", "USD", null  ),
	UG( "Uganda", "UG", "UGA", 800, "ug", "UGX", null  ),
	UA( "Ukraine", "UA", "UKR", 804, "ua", "UAH", "nnnnn" , en ),
	AE( "United Arab Emirates", "AE", "ARE", 784, "ae", "AED", null  ),
	GB( "United Kingdom", "GB", "GBR", 826, "gb", "GBP", "uk" , en ),
	US( "United States", "US", "USA", 840, "us", "USD", "us" , en ),
	UY( "Uruguay", "UY", "URY", 858, "uy", "UYU", "nnnnn"  ),
	UZ( "Uzbekistan", "UZ", "UZB", 860, "uz", "UZS", "nnnnnn"  ),
	VU( "Vanuatu", "VU", "VUT", 548, "vu", "VUV", null  ),
	VE( "Venezuela", "VE", "VEN", 862, "ve", "VEB", "nnnn( a)?"  ),
	VN( "Viet Nam", "VN", "VNM", 704, "vn", "VND", "nnnnnn"  ),
	VG( "Virgin Islands, British", "VG", "VGB", 92, "vg", "USD", "VGnnnn"  ),
	VI( "Virgin Islands, U.S.", "VI", "VIR", 850, "vi", "USD", "us"  ),
	WF( "Wallis and Futuna", "WF", "WLF", 876, "wf", "XPF", "fr"  ),
	EH( "Western Sahara", "EH", "ESH", 732, "eh", "MAD", null  ),
	YE( "Yemen", "YE", "YEM", 887, "ye", "YER", null  ),
	ZM( "Zambia", "ZM", "ZMB", 894, "zm", "ZMK", "nnnnn"  ),
	ZW( "Zimbabwe", "ZW", "ZWE", 716, "zw", "ZWD", null  ),
	// -- END GENERATED --
	;
	
	private final String commonName;
	private final String iso2;
	private final String iso3;
	private final int isoN;
	private final String ccTld;
	private final Currency currency;
	private final String postalcode;
	private final Locale [] locales;
	
	private static HashMap<String,StaticCountries>forIso2 = new HashMap<String,StaticCountries>();
	private static HashMap<Integer,StaticCountries>forIsoN = new HashMap<Integer,StaticCountries>();
	
	static {
		for( StaticCountries c : StaticCountries.values() ){
			
			forIso2.put( c.getIso2(), c );
			forIsoN.put( c.getIsoN(), c );
		}
	}
	
	StaticCountries( String commonName, String iso2, String iso3, int isoN,
			String ccTld, String currency, String postalcode, Language ... language ){
		
		this.commonName = commonName;
		this.iso2 = iso2;
		this.iso3 = iso3;
		this.isoN = isoN;
		this.ccTld = ccTld;
		Currency c;
		try {
			 c = currency != null ? Currency.getInstance( currency ) : null;
		} catch( IllegalArgumentException e ) {
			c = null;
			// No Logger available here, sorry.
			E.rr( "Unknown currency: '" + currency + "'", e );
		}
		this.currency = c;
		this.postalcode = postalcode;
		Locale [] l = new Locale[ language.length ];
		for( int i=0; i<language.length; i++ ){
			l[i] = new Locale( language[i].code() );
		}
		this.locales = l;
	}
	
	public Locale [] locales(){ return locales; }
	
	public static StaticCountries forIso2( String iso2 ){ return forIso2.get( iso2.toUpperCase() ); }
	public static StaticCountries forIsoN( int isoN ){ return forIsoN.get( isoN ); }

	@Override
	public String getCommonName() { return commonName; }

	@Override
	public String getIso2() { return iso2; }

	@Override
	public String getIso3() { return iso3; }

	@Override
	public int getIsoN() { return isoN; }

	@Override
	public String getCcTld() { return ccTld; }

	@Override
	public Currency getCurrency() {
		return currency;
	}

	public String getPostalcode() { return postalcode; }


	/*
	private static final class CountryNameImpl implements CountryName {
		
		private final StaticCountries country;
		
		private CountryNameImpl( StaticCountries country ) {
			this.country = country;
		}

		@Override
		public int getSort() {
			return 0;
		}

		@Override
		public String getName() {
			return country.commonName;
		}

		@Override
		public Locale getLocale() {
			return Locale.GERMAN;
		}
		
		
	}
	*/
}
