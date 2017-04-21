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
	AD( "Andorra", "AD", "AND", 20, "ad", "EUR", "ADnnn", null , ca, es, fr ),
	AE( "United Arab Emirates", "AE", "ARE", 784, "ae", "AED", null, null  ),
	AF( "Afghanistan", "AF", "AFG", 4, "af", "AFN", null, null , fa, en ),
	AG( "Antigua and Barbuda", "AG", "ATG", 28, "ag", "XCD", null, null  ),
	AI( "Anguilla", "AI", "AIA", 660, "ai", "XCD", "AI-2640", null  ),
	AL( "Albania", "AL", "ALB", 8, "al", "ALL", "nnnn", null , sq, en ),
	AM( "Armenia", "AM", "ARM", 51, "am", "AMD", "nnnn", null  ),
	AN( "Netherlands Antilles", "AN", "ANT", 530, "an", "ANG", null, null  ),
	AO( "Angola", "AO", "AGO", 24, "ao", "AOA", null, null  ),
	AR( "Argentina", "AR", "ARG", 32, "ar", "ARS", "annnnaaa", null , en ),
	AS( "American Samoa", "AS", "ASM", 16, "as", "USD", "us", null , sm, en ),
	AT( "Austria", "AT", "AUT", 40, "at", "EUR", "nnnn", "ATUnnnnnnnn" , de ),
	AU( "Australia", "AU", "AUS", 36, "au", "AUD", "nnnn", null , en ),
	AW( "Aruba", "AW", "ABW", 533, "aw", "ANG", null, null  ),
	AX( "Åland Islands", "AX", "ALA", 248, "ax", "EUR", "nnnnn", null , sv, en ),
	AZ( "Azerbaijan", "AZ", "AZE", 31, "az", "AZN", "AZnnnn", null  ),
	BA( "Bosnia and Herzegovina", "BA", "BIH", 70, "ba", "EUR", "nnnnn", null , bs, hr, sr, de, en ),
	BB( "Barbados", "BB", "BRB", 52, "bb", "BBD", "BBnnnnn", null  ),
	BD( "Bangladesh", "BD", "BGD", 50, "bd", "BDT", "nnnn", null  ),
	BE( "Belgium", "BE", "BEL", 56, "be", "EUR", "nnnn", "BEnnnnnnnnnn" , nl, fr, de ),
	BF( "Burkina Faso", "BF", "BFA", 854, "bf", "XOF", null, null  ),
	BG( "Bulgaria", "BG", "BGR", 100, "bg", "BGN", "nnnn", "BGnnnnnnnnn|BGnnnnnnnnnn" , bg, de, en ),
	BH( "Bahrain", "BH", "BHR", 48, "bh", "BHD", "999(9)?", null  ),
	BI( "Burundi", "BI", "BDI", 108, "bi", "BIF", null, null  ),
	BJ( "Benin", "BJ", "BEN", 204, "bj", "XOF", null, null  ),
	BM( "Bermuda", "BM", "BMU", 60, "bm", "BMD", "aa(nn|aa)", null  ),
	BN( "Brunei Darussalam", "BN", "BRN", 96, "bn", "BND", "aannnn", null  ),
	BO( "Bolivia", "BO", "BOL", 68, "bo", "BOB", "nnnn", null  ),
	BR( "Brazil", "BR", "BRA", 76, "br", "BRL", "nnnnn(-nnn)?", null , en ),
	BS( "Bahamas", "BS", "BHS", 44, "bs", "BSD", null, null  ),
	BT( "Bhutan", "BT", "BTN", 64, "bt", "BTN", null, null  ),
	BV( "Bouvet Island", "BV", "BVT", 74, "bv", "NOK", null, null  ),
	BW( "Botswana", "BW", "BWA", 72, "bw", "BWP", null, null  ),
	BY( "Belarus", "BY", "BLR", 112, "by", "BYR", "nnnnnn", null , be, ru, en ),
	BZ( "Belize", "BZ", "BLZ", 84, "bz", "BZD", null, null  ),
	CA( "Canada", "CA", "CAN", 124, "ca", "CAD", "ana nan", null , en, fr ),
	CC( "Cocos (Keeling) Islands", "CC", "CCK", 166, "cc", "AUD", "nnnn", null  ),
	CD( "Congo, Democratic Republic of the", "CD", "COD", 180, "cd", "CDF", null, null  ),
	CF( "Central African Republic", "CF", "CAF", 140, "cf", "XAF", null, null  ),
	CG( "Congo", "CG", "COG", 178, "cg", "XAF", null, null  ),
	CH( "Switzerland", "CH", "CHE", 756, "ch", "CHF", "nnnn", null , de ),
	CI( "Îvory Coast", "CI", "CIV", 384, "ci", "XOF", null, null  ),
	CK( "Cook Islands", "CK", "COK", 184, "ck", "NZD", null, null  ),
	CL( "Chile", "CL", "CHL", 152, "cl", "CLP", "nnnnnnn", null , en ),
	CM( "Cameroon", "CM", "CMR", 120, "cm", "XAF", null, null  ),
	CN( "China", "CN", "CHN", 156, "cn", "CNY", "nnnnnn", null  ),
	CO( "Colombia", "CO", "COL", 170, "co", "COP", "nnnnnn", null , en ),
	CR( "Costa Rica", "CR", "CRI", 188, "cr", "CRC", "nnnnn", null  ),
	CU( "Cuba", "CU", "CUB", 192, "cu", "CUP", "nnnnn", null  ),
	CV( "Cape Verde", "CV", "CPV", 132, "cv", "CVE", "nnnn", null  ),
	CX( "Christmas Island", "CX", "CXR", 162, "cx", "AUD", "nnnn", null  ),
	CY( "Cyprus", "CY", "CYP", 196, "cy", "EUR", "nnnn", "CYnnnnnnnna" , el, tr, en ),
	CZ( "Czech Republic", "CZ", "CZE", 203, "cz", "CZK", "nnn nn", "CZnnnnnnnn|CZnnnnnnnnn|CZnnnnnnnnnn" , cs ),
	DE( "Germany", "DE", "DEU", 276, "de", "EUR", "nnnnn", "DEnnnnnnnnn" , de ),
	DJ( "Djibouti", "DJ", "DJI", 262, "dj", "DJF", null, null  ),
	DK( "Denmark", "DK", "DNK", 208, "dk", "DKK", "nnnn", "DKnnnnnnnn" , da, en ),
	DM( "Dominica", "DM", "DMA", 212, "dm", "XCD", null, null  ),
	DO( "Dominican Republic", "DO", "DOM", 214, "do", "DOP", "nnnnn", null  ),
	DZ( "Algeria", "DZ", "DZA", 12, "dz", "DZD", "nnnnn", null , ar, fr ),
	EC( "Ecuador", "EC", "ECU", 218, "ec", "USD", "ECnnnnnn", null , en ),
	EE( "Estonia", "EE", "EST", 233, "ee", "EUR", "nnnnn", "EEnnnnnnnnn" , et, en, de ),
	EG( "Egypt", "EG", "EGY", 818, "eg", "EGP", "nnnnn", null , en ),
	EH( "Western Sahara", "EH", "ESH", 732, "eh", "MAD", null, null  ),
	ER( "Eritrea", "ER", "ERI", 232, "er", "ERN", null, null  ),
	ES( "Spain", "ES", "ESP", 724, "es", "EUR", "nnnnn", "ESxnnnnnnnx" , es, en ),
	ET( "Ethiopia", "ET", "ETH", 231, "et", "ETB", "nnnn", null  ),
	FI( "Finland", "FI", "FIN", 246, "fi", "EUR", "nnnnn", "FInnnnnnnn" , fi, sv, en ),
	FJ( "Fiji", "FJ", "FJI", 242, "fj", "FJD", null, null  ),
	FK( "Falkland Islands (Malvinas)", "FK", "FLK", 238, "fk", "FKP", "FIQQ 1ZZ", null  ),
	FM( "Micronesia", "FM", "FSM", 583, "fm", "USD", "us", null  ),
	FO( "Faroe Islands", "FO", "FRO", 234, "fo", "DKK", "nnn", null , fo, da, en ),
	FR( "France", "FR", "FRA", 250, "fr", "EUR", "fr", "FRxxnnnnnnnnn" , fr ),
	GA( "Gabon", "GA", "GAB", 266, "ga", "XAF", "nn nn", null  ),
	GB( "United Kingdom", "GB", "GBR", 826, "gb", "GBP", "uk", "GBnnnnnnnnn|GBnnnnnnnnnnnn|GBGDnnn|GBHAnnn" , en ),
	GD( "Grenada", "GD", "GRD", 308, "gd", "XCD", null, null  ),
	GE( "Georgia", "GE", "GEO", 268, "ge", "GEL", "nnnn", null  ),
	GF( "French Guiana", "GF", "GUF", 254, "gf", "EUR", "fr", null  ),
	GG( "Guernsey, Bailiwick of", "GG", "GGY", 831, "gg", "GBP", "uk{GY}", null , en ),
	GH( "Ghana", "GH", "GHA", 288, "gh", "GHC", null, null  ),
	GI( "Gibraltar", "GI", "GIB", 292, "gi", "GIP", null, null , en, es ),
	GL( "Greenland", "GL", "GRL", 304, "gl", "DKK", "nnnn", null , en ),
	GM( "Gambia", "GM", "GMB", 270, "gm", "GMD", null, null  ),
	GN( "Guinea", "GN", "GIN", 324, "gn", "GNF", null, null  ),
	GP( "Guadeloupe", "GP", "GLP", 312, "gp", "EUR", "fr", null  ),
	GQ( "Equatorial Guinea", "GQ", "GNQ", 226, "gq", "XAF", null, null  ),
	GR( "Greece", "GR", "GRC", 300, "gr", "EUR", "nnnnn", "ELnnnnnnnnn" , el, en ),
	GS( "South Georgia and Sandwich Islands", "GS", "SGS", 239, null, "GBP", "SIQQ 1ZZ", null  ),
	GT( "Guatemala", "GT", "GTM", 320, "gt", "GTQ", "nnnnn", null  ),
	GU( "Guam", "GU", "GUM", 316, "gu", "USD", "us", null  ),
	GW( "Guinea-Bissau", "GW", "GNB", 624, "gw", "XOF", "nnnn", null  ),
	GY( "Guyana", "GY", "GUY", 328, "gy", "GYD", null, null  ),
	HK( "Hong Kong", "HK", "HKG", 344, "hk", "HNL", "999077", null  ),
	HM( "Heard and Mc Donald Islands", "HM", "HMD", 334, "hm", "AUD", "nnnn", null  ),
	HN( "Honduras", "HN", "HND", 340, "hn", "HNL", "nnnnn", null  ),
	HR( "Croatia", "HR", "HRV", 191, "hr", "HRK", "nnnnn", "HRnnnnnnnnnnn" , sr, de, en ),
	HT( "Haiti", "HT", "HTI", 332, "ht", "USD", "nnnn", null  ),
	HU( "Hungary", "HU", null, 348, "hu", "HUF", "nnnn", "HUnnnnnnnn" , hu, de, en ),
	ID( "Indonesia", "ID", "IDN", 360, "id", "IDR", "nnnnn", null  ),
	IE( "Ireland", "IE", "IRL", 372, "ie", "EUR", "xnx xxxx", "IEnxnnnnna|IEnnnnnnnaa" , en ),
	IL( "Israel", "IL", "ISR", 376, "il", "ILS", "nnnnn", null , en ),
	IM( "Isle of Man", "IM", "IMN", 833, "im", "GBP", "uk{IM}", null , en ),
	IN( "India", "IN", "IND", 356, "in", "INR", "nnn nnn", null , en ),
	IO( "British Indian Ocean Territory", "IO", "IOT", 86, "io", "USD", "BBND 1ZZ", null  ),
	IQ( "Iraq", "IQ", "IRQ", 368, "iq", "IQD", "nnnnn", null  ),
	IR( "Iran", "IR", "IRN", 364, "ir", "IRR", "nnnnn nnnnn", null  ),
	IS( "Iceland", "IS", "ISL", 352, "is", "ISK", "nnn", null , is, en, da ),
	IT( "Italy", "IT", "ITA", 380, "it", "EUR", "nnnnn", "ITnnnnnnnnnnn" , it ),
	JE( "Jersey", "JE", "JEY", 832, "je", "GBP", "uk{JE}", null , en ),
	JM( "Jamaica", "JM", "JAM", 388, "jm", "JMD", "n?n?", null  ),
	JO( "Jordan", "JO", "JOR", 400, "jo", "JOD", "nnnnn", null  ),
	JP( "Japan", "JP", "JPN", 392, "jp", "JPY", "nnn-nnnn", null , en ),
	KE( "Kenya", "KE", "KEN", 404, "ke", "KES", "nnnnn", null  ),
	KG( "Kyrgyzstan", "KG", "KGZ", 417, "kg", "KGS", "nnnnnn", null  ),
	KH( "Cambodia", "KH", "KHM", 116, "kh", "KHR", "nnnnn", null  ),
	KI( "Kiribati", "KI", "KIR", 296, "ki", "AUD", null, null  ),
	KM( "Comoros", "KM", null, 174, "km", "KMF", null, null  ),
	KN( "Saint Kitts and Nevis", "KN", "KNA", 659, "kn", "XCD", null, null  ),
	KP( "Korea North", "KP", "PRK", 408, "kp", "KPW", null, null  ),
	KR( "Korea South", "KR", "KOR", 410, "kr", "KRW", "nnn-nnn", null , en ),
	KW( "Kuwait", "KW", "KWT", 414, "kw", "KWD", "nnnnn", null  ),
	KY( "Cayman Islands", "KY", "CYM", 136, "ky", "KYD", "KYn-nnnn", null  ),
	KZ( "Kazakhstan", "KZ", "KAZ", 398, "kz", "KZT", "nnnnnn", null  ),
	LA( "Lao People's Democratic Republic", "LA", "LAO", 418, "la", "LAK", "nnnnn", null  ),
	LB( "Lebanon", "LB", "LBN", 422, "lb", "LBP", "nnnn( nnnn)?", null  ),
	LC( "Saint Lucia", "LC", "LCA", 662, "lc", "XCD", null, null  ),
	LI( "Liechtenstein", "LI", "LIE", 438, "li", "CHF", "nnnn", null , de ),
	LK( "Sri Lanka", "LK", "LKA", 144, "lk", "LKR", "nnnnn", null  ),
	LR( "Liberia", "LR", "LBR", 430, "lr", "LRD", "nnnn", null  ),
	LS( "Lesotho", "LS", "LSO", 426, "ls", "LSL", "nnn", null  ),
	LT( "Lithuania", "LT", "LTU", 440, "lt", "LTL", "nnnnn", "LTnnnnnnnnn|LTnnnnnnnnnnnn" , lt, ru, en ),
	LU( "Luxembourg", "LU", "LUX", 442, "lu", "EUR", "nnnn", "LUnnnnnnnn" , lb, de, fr ),
	LV( "Latvia", "LV", "LVA", 428, "lv", "LVL", "nnnn", "LVnnnnnnnnnnn" , lv, de, en ),
	LY( "Libyan Arab Jamahiriya", "LY", "LBY", 434, "ly", "LYD", "nnnnn", null  ),
	MA( "Morocco", "MA", "MAR", 504, "ma", "MAD", "nnnnn", null  ),
	MC( "Monaco", "MC", "MCO", 492, "mc", "EUR", "fr{MC}", null , fr, it, en ),
	MD( "Moldova", "MD", "MDA", 498, "md", "MDL", "nnnn", null  ),
	ME( "Montenegro", "ME", "MNE", 499, "me", "EUR", "nnnnn", null  ),
	MG( "Madagascar", "MG", "MDG", 450, "mg", "MGA", "nnn", null  ),
	MH( "Marshall Islands", "MH", "MHL", 584, "mh", "USD", "us", null  ),
	MK( "Macedonia", "MK", "MKD", 807, "mk", "MKD", "nnnn", null , mk, en, it ),
	ML( "Mali", "ML", "MLI", 466, "ml", "XOF", null, null  ),
	MM( "Myanmar", "MM", "MMR", 104, "mm", "MMK", "nnnnn", null  ),
	MN( "Mongolia", "MN", "MNG", 496, "mn", "MNT", "nnnnnn", null  ),
	MO( "Macao", "MO", "MAC", 446, "mo", "MOP", "999078", null  ),
	MP( "Northern Mariana Islands", "MP", "MNP", 580, "mp", "USD", "us", null  ),
	MQ( "Martinique", "MQ", "MTQ", 474, "mq", "EUR", "fr", null  ),
	MR( "Mauritania", "MR", "MRT", 478, "mr", "MRO", null, null  ),
	MS( "Montserrat", "MS", "MSR", 500, "ms", "XCD", null, null  ),
	MT( "Malta", "MT", "MDV", 470, "mv", "MVR", "aaa nnnn", "MTnnnnnnnn" , mt, en,  it ),
	MU( "Mauritius", "MU", "MUS", 480, "mu", "MUR", null, null  ),
	MV( "Maldives", "MV", "MDV", 462, "mv", "MVR", "nn-nn", null  ),
	MW( "Malawi", "MW", "MWI", 454, "mw", "MWK", null, null  ),
	MX( "Mexico", "MX", "MEX", 484, "mx", "MXN", "nnnnn", null , en ),
	MY( "Malaysia", "MY", "MYS", 458, "my", "MYR", "nnnnn", null  ),
	MZ( "Mozambique", "MZ", "MOZ", 508, "mz", "MZM", "nnnnn", null  ),
	NA( "Namibia", "NA", "NAM", 516, "na", "ZAR", null, null  ),
	NC( "New Caledonia", "NC", "NCL", 540, "nc", "XPF", "fr", null  ),
	NE( "Niger", "NE", "NER", 562, "ne", "XOF", "nnnn", null  ),
	NF( "Norfolk Island", "NF", "NFK", 574, "nf", "AUD", "nnnn", null  ),
	NG( "Nigeria", "NG", "NGA", 566, "ng", "NGN", "nnnnnn", null  ),
	NI( "Nicaragua", "NI", "NIC", 558, "ni", "NIO", "nnn-nnn-n", null  ),
	NL( "Netherlands", "NL", "NLD", 528, "nl", "EUR", "nnnn aa", "NLnnnnnnnnnBnn" , nl, de, en ),
	NO( "Norway", "NO", "NOR", 578, "no", "NOK", "nnnn", null , no ),
	NP( "Nepal", "NP", "NPL", 524, "np", "NPR", "nnnnn", null  ),
	NR( "Nauru", "NR", "NRU", 520, "nr", "AUD", null, null  ),
	NU( "Niue", "NU", "NIU", 570, "nu", "NZD", null, null  ),
	NZ( "New Zealand", "NZ", "NZL", 554, "nz", "NZD", "nnnn", null , en ),
	OM( "Oman", "OM", "OMN", 512, "om", "OMR", "nnn", null  ),
	PA( "Panama", "PA", "PAN", 591, "pa", "USD", null, null  ),
	PE( "Peru", "PE", "PER", 604, "pe", "USD", "(nn)?", null , en ),
	PF( "French Polynesia", "PF", "PYF", 258, "pf", "XPF", "fr", null  ),
	PG( "Papua New Guinea", "PG", "PNG", 598, "pg", "PGK", "nnn", null  ),
	PH( "Philippines", "PH", "PHL", 608, "ph", "PHP", "nnnn", null , en ),
	PK( "Pakistan", "PK", "PAK", 586, "pk", "PKR", "nnnnn", null  ),
	PL( "Poland", "PL", "POL", 616, "pl", "PLN", "nn-nnn", "PLnnnnnnnnnn" , pl, ru, en, de ),
	PM( "Saint Pierre and Miquelon", "PM", "SPM", 666, "pm", "EUR", "fr", null  ),
	PN( "Pitcairn", "PN", "PCN", 612, "pn", "NZD", "PCRN 1ZZ", null  ),
	PR( "Puerto Rico", "PR", "PRI", 630, "pr", "USD", "us", null  ),
	PS( "Palastinian Territories", "PS", "PSE", 275, "ps", "ILS", null, null  ),
	PT( "Portugal", "PT", "PRT", 620, "pt", "EUR", "nnnn-nnn", "PTnnnnnnnnn" , pt, es, en, fr ),
	PW( "Palau", "PW", "PLW", 585, "pw", "USD", "us", null  ),
	PY( "Paraguay", "PY", "PRY", 600, "py", "PYG", "nnnn", null  ),
	QA( "Qatar", "QA", "QAT", 634, "qa", "QAR", null, null  ),
	RE( "Réunion", "RE", "REU", 638, "re", "EUR", "fr", null  ),
	RO( "Romania", "RO", "ROU", 642, "ro", "RON", "nnnnnn", "ROn{2,10}" , ro, en, fr, de, it, es ),
	RS( "Serbia", "RS", "SRB", 688, "rs", "EUR", "nnnnn", null , en ),
	RU( "Russian Federation", "RU", "RUS", 643, "ru", "RUB", "nnnnnn", null , en ),
	RW( "Rwanda", "RW", "RWA", 646, "rw", "RWF", null, null  ),
	SA( "Saudi Arabia", "SA", "SAU", 682, "sa", "SAR", "nnnnn", null  ),
	SB( "Solomon Islands", "SB", "SLB", 90, "sb", "SBD", null, null  ),
	SC( "Seychelles", "SC", "SYC", 690, "sc", "SCR", null, null  ),
	SD( "Sudan", "SD", "SDN", 736, "sd", "SDD", "nnnn", null  ),
	SE( "Sweden", "SE", "SWE", 752, "se", "SEK", "nnn nn", "SEnnnnnnnnnn01" , sv, en, de, fr ),
	SG( "Singapore", "SG", "SGP", 702, "sg", "SGD", "nnnnnn", null  ),
	SH( "Saint Helena", "SH", "SHN", 654, "sh", "SHP", "STHL 1ZZ", null  ),
	SI( "Slovenia", "SI", "SVN", 705, "si", "EUR", "nnnn", "SInnnnnnnn" , sl, hr, en, de ),
	SJ( "Svalbard and Jan Mayen Islands", "SJ", "SJM", 744, "sj", "NOK", null, null  ),
	SK( "Slovakia", "SK", "SVK", 703, "sk", "SKK", "nnn nn", "SKnnnnnnnnnn" , sk, en, de ),
	SL( "Sierra Leone", "SL", "SLE", 694, "sl", "SLL", null, null  ),
	SM( "San Marino", "SM", "SMR", 674, "sm", "EUR", "nnnnn", null , it, en ),
	SN( "Senegal", "SN", "SEN", 686, "sn", "XOF", "nnnnn", null  ),
	SO( "Somalia", "SO", "SOM", 706, "so", "SOS", null, null  ),
	SR( "Suriname", "SR", "SUR", 740, "sr", "SRD", null, null  ),
	ST( "Sao Tome and Principe", "ST", "STP", 678, "st", "STD", null, null  ),
	SV( "El Salvador", "SV", "SLV", 222, "sv", "SVC", "nnnn", null  ),
	SY( "Syrian Arab Republic", "SY", "SYR", 760, "sy", "SYP", null, null  ),
	SZ( "Swaziland", "SZ", "SWZ", 748, "sz", "SZL", "annn", null  ),
	TC( "Turks and Caicos Islands", "TC", "TCA", 796, "tc", "USD", "TKCA 1ZZ", null  ),
	TD( "Chad", "TD", "TCD", 148, "td", "XAF", "nnnnn", null  ),
	TF( "French Southern Territories", "TF", "ATF", 260, "tf", "EUR", null, null  ),
	TG( "Togo", "TG", "TGO", 768, "tg", "XOF", null, null  ),
	TH( "Thailand", "TH", "THA", 764, "th", "THB", "nnnnn", null , en ),
	TJ( "Tajikistan", "TJ", "TJK", 762, "tj", "RUB", "nnnnnn", null  ),
	TK( "Tokelau", "TK", "TKL", 772, "tk", "NZD", null, null  ),
	TL( "Timor-Leste", "TL", "TLS", 626, "tl", "IDR", null, null  ),
	TM( "Turkmenistan", "TM", "TKM", 795, "tm", "TMM", "nnnnnn", null  ),
	TN( "Tunisia", "TN", "TUN", 788, "tn", "TND", "nnnn", null  ),
	TO( "Tonga", "TO", "TON", 776, "to", "TOP", null, null  ),
	TR( "Turkey", "TR", "TUR", 792, "tr", "TRY", "nnnnn", null , en ),
	TT( "Trinidad and Tobago", "TT", "TTO", 780, "tt", "TTD", null, null  ),
	TV( "Tuvalu", "TV", "TUV", 798, "tv", "USD", null, null  ),
	TW( "Taiwan", "TW", "TWN", 158, "tw", "TWD", "nnnnn", null  ),
	TZ( "Tanzania", "TZ", "TZA", 834, "tz", "TZS", null, null  ),
	UA( "Ukraine", "UA", "UKR", 804, "ua", "UAH", "nnnnn", null , en ),
	UG( "Uganda", "UG", "UGA", 800, "ug", "UGX", null, null  ),
	US( "United States", "US", "USA", 840, "us", "USD", "us", null , en ),
	UY( "Uruguay", "UY", "URY", 858, "uy", "UYU", "nnnnn", null  ),
	UZ( "Uzbekistan", "UZ", "UZB", 860, "uz", "UZS", "nnnnnn", null  ),
	VA( "Vatican City State", "VA", "VAT", 336, "va", "EUR", "120", null , en ),
	VC( "Saint Vincent and the Grenadines", "VC", "VCT", 670, "vc", "XCD", null, null  ),
	VE( "Venezuela", "VE", "VEN", 862, "ve", "VEB", "nnnn( a)?", null  ),
	VG( "Virgin Islands, British", "VG", "VGB", 92, "vg", "USD", "VGnnnn", null  ),
	VI( "Virgin Islands, U.S.", "VI", "VIR", 850, "vi", "USD", "us", null  ),
	VN( "Viet Nam", "VN", "VNM", 704, "vn", "VND", "nnnnnn", null  ),
	VU( "Vanuatu", "VU", "VUT", 548, "vu", "VUV", null, null  ),
	WF( "Wallis and Futuna", "WF", "WLF", 876, "wf", "XPF", "fr", null  ),
	WS( "Samoa", "WS", "WSM", 882, "ws", "WST", "us", null  ),
	YE( "Yemen", "YE", "YEM", 887, "ye", "YER", null, null  ),
	YT( "Mayotte", "YT", "MYT", 175, "yt", "EUR", "fr", null  ),
	ZA( "South Africa", "ZA", "ZAF", 710, "za", "ZAR", "nnnn", null , en ),
	ZM( "Zambia", "ZM", "ZMB", 894, "zm", "ZMK", "nnnnn", null  ),
	ZW( "Zimbabwe", "ZW", "ZWE", 716, "zw", "ZWD", null, null  ),
	// -- END GENERATED --
	;
	
	private final String commonName;
	private final String iso2;
	private final String iso3;
	private final int isoN;
	private final String ccTld;
	private final Currency currency;
	private final String postalcode;
	private final String tin;
	private final Locale [] locales;
	
	private static HashMap<String,StaticCountries>forIso2 = new HashMap<String,StaticCountries>();
	
	static {
		for( StaticCountries c : StaticCountries.values() ){
			
			forIso2.put( c.getIso2(), c );
		}
	}
	
	StaticCountries( String commonName, String iso2, String iso3, int isoN,
			String ccTld, String currency, String postalcode, String tin, Language ... language ){
		
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
		this.tin = tin;
		Locale [] l = new Locale[ language.length ];
		for( int i=0; i<language.length; i++ ){
			l[i] = new Locale( language[i].iso2() );
		}
		this.locales = l;
	}
	
	public Locale [] locales(){ return locales; }
	
	public static StaticCountries forIso2( String iso2 ){ return forIso2.get( iso2.toUpperCase() ); }

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
	
	public String getTin() { return tin; }


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
