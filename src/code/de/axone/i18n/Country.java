package de.axone.i18n;

import static de.axone.i18n.Language.*;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public enum Country {
	
	// -- START GENERATED --
	AF( "Afghanistan", "AF", "AFG", 4, "af", Currency.getInstance( "AFN" ), null ,  fa,  en ),
	AX( "Åland Islands", "AX", "ALA", 248, "ax", Currency.getInstance( "EUR" ), "nnnnn" ,  sv,  en ),
	AL( "Albania", "AL", "ALB", 8, "al", Currency.getInstance( "ALL" ), "nnnn" ,  sq,  en ),
	DZ( "Algeria", "DZ", "DZA", 12, "dz", Currency.getInstance( "DZD" ), "nnnnn" ,  ar,  fr ),
	AS( "American Samoa", "AS", "ASM", 16, "as", Currency.getInstance( "USD" ), "us" ,  sm,  en ),
	AD( "Andorra", "AD", "AND", 20, "ad", Currency.getInstance( "EUR" ), "ADnnn" ,  ca,  es,  fr ),
	AO( "Angola", "AO", "AGO", 24, "ao", Currency.getInstance( "AOA" ), null  ),
	AI( "Anguilla", "AI", "AIA", 660, "ai", Currency.getInstance( "XCD" ), "AI-2640"  ),
	AG( "Antigua and Barbuda", "AG", "ATG", 28, "ag", Currency.getInstance( "XCD" ), null  ),
	AR( "Argentina", "AR", "ARG", 32, "ar", Currency.getInstance( "ARS" ), "annnnaaa"  ),
	AM( "Armenia", "AM", "ARM", 51, "am", Currency.getInstance( "AMD" ), "nnnn"  ),
	AW( "Aruba", "AW", "ABW", 533, "aw", Currency.getInstance( "ANG" ), null  ),
	AU( "Australia", "AU", "AUS", 36, "au", Currency.getInstance( "AUD" ), "nnnn"  ),
	AT( "Austria", "AT", "AUT", 40, "at", Currency.getInstance( "EUR" ), "nnnn" , de ),
	AZ( "Azerbaijan", "AZ", "AZE", 31, "az", Currency.getInstance( "AZN" ), "AZnnnn"  ),
	BS( "Bahamas", "BS", "BHS", 44, "bs", Currency.getInstance( "BSD" ), null  ),
	BH( "Bahrain", "BH", "BHR", 48, "bh", Currency.getInstance( "BHD" ), "999(9)?"  ),
	BD( "Bangladesh", "BD", "BGD", 50, "bd", Currency.getInstance( "BDT" ), "nnnn"  ),
	BB( "Barbados", "BB", "BRB", 52, "bb", Currency.getInstance( "BBD" ), "BBnnnnn"  ),
	BY( "Belarus", "BY", "BLR", 112, "by", Currency.getInstance( "BYR" ), "nnnnnn", be, ru, en ),
	BE( "Belgium", "BE", "BEL", 56, "be", Currency.getInstance( "EUR" ), "nnnn" , nl, fr, de ),
	BZ( "Belize", "BZ", "BLZ", 84, "bz", Currency.getInstance( "BZD" ), null  ),
	BJ( "Benin", "BJ", "BEN", 204, "bj", Currency.getInstance( "XOF" ), null  ),
	BM( "Bermuda", "BM", "BMU", 60, "bm", Currency.getInstance( "BMD" ), "aa(nn|aa)"  ),
	BT( "Bhutan", "BT", "BTN", 64, "bt", Currency.getInstance( "BTN" ), null  ),
	BO( "Bolivia", "BO", "BOL", 68, "bo", Currency.getInstance( "BOB" ), "nnnn"  ),
	BA( "Bosnia and Herzegovina", "BA", "BIH", 70, "ba", Currency.getInstance( "BAM" ), "nnnnn" ,  bs, hr, sr, de, en ),
	BW( "Botswana", "BW", "BWA", 72, "bw", Currency.getInstance( "BWP" ), null  ),
	BV( "Bouvet Island", "BV", "BVT", 74, "bv", Currency.getInstance( "NOK" ), null  ),
	BR( "Brazil", "BR", "BRA", 76, "br", Currency.getInstance( "BRL" ), "nnnnn(-nnn)?"  ),
	IO( "British Indian Ocean Territory", "IO", "IOT", 86, "io", Currency.getInstance( "USD" ), "BBND 1ZZ"  ),
	BN( "Brunei Darussalam", "BN", "BRN", 96, "bn", Currency.getInstance( "BND" ), "aannnn"  ),
	BG( "Bulgaria", "BG", "BGR", 100, "bg", Currency.getInstance( "BGN" ), "nnnn" , bg, de, en ),
	BF( "Burkina Faso", "BF", "BFA", 854, "bf", Currency.getInstance( "XOF" ), null  ),
	BI( "Burundi", "BI", "BDI", 108, "bi", Currency.getInstance( "BIF" ), null  ),
	KH( "Cambodia", "KH", "KHM", 116, "kh", Currency.getInstance( "KHR" ), "nnnnn"  ),
	CM( "Cameroon", "CM", "CMR", 120, "cm", Currency.getInstance( "XAF" ), null  ),
	CA( "Canada", "CA", "CAN", 124, "ca", Currency.getInstance( "CAD" ), "nan ana", en ),
	CV( "Cape Verde", "CV", "CPV", 132, "cv", Currency.getInstance( "CVE" ), "nnnn"  ),
	KY( "Cayman Islands", "KY", "CYM", 136, "ky", Currency.getInstance( "KYD" ), "KYn-nnnn"  ),
	CF( "Central African Republic", "CF", "CAF", 140, "cf", Currency.getInstance( "XAF" ), null  ),
	TD( "Chad", "TD", "TCD", 148, "td", Currency.getInstance( "XAF" ), "nnnnn"  ),
	CL( "Chile", "CL", "CHL", 152, "cl", Currency.getInstance( "CLP" ), "nnnnnnn"  ),
	CN( "China", "CN", "CHN", 156, "cn", Currency.getInstance( "CNY" ), "nnnnnn"  ),
	CX( "Christmas Island", "CX", "CXR", 162, "cx", Currency.getInstance( "AUD" ), "nnnn"  ),
	CC( "Cocos (Keeling) Islands", "CC", "CCK", 166, "cc", Currency.getInstance( "AUD" ), "nnnn"  ),
	CO( "Colombia", "CO", "COL", 170, "co", Currency.getInstance( "COP" ), "nnnnnn"  ),
	KM( "Comoros", "KM", null, 174, "km", Currency.getInstance( "KMF" ), null  ),
	CG( "Congo", "CG", "COG", 178, "cg", Currency.getInstance( "XAF" ), null  ),
	CD( "Congo, Democratic Republic of the", "CD", "COD", 180, "cd", Currency.getInstance( "CDF" ), null  ),
	CK( "Cook Islands", "CK", "COK", 184, "ck", Currency.getInstance( "NZD" ), null  ),
	CR( "Costa Rica", "CR", "CRI", 188, "cr", Currency.getInstance( "CRC" ), "nnnnn"  ),
	HR( "Croatia", "HR", "HRV", 191, "hr", Currency.getInstance( "HRK" ), "nnnnn" , sr, de, en ),
	CU( "Cuba", "CU", "CUB", 192, "cu", Currency.getInstance( "CUP" ), "nnnnn"  ),
	CY( "Cyprus", "CY", "CYP", 196, "cy", Currency.getInstance( "EUR" ), "nnnn" , el, tr, en ),
	CZ( "Czech Republic", "CZ", "CZE", 203, "cz", Currency.getInstance( "CZK" ), "nnn nn" , cs ),
	DK( "Denmark", "DK", "DNK", 208, "dk", Currency.getInstance( "DKK" ), "nnnn" , da, en ),
	DJ( "Djibouti", "DJ", "DJI", 262, "dj", Currency.getInstance( "DJF" ), null  ),
	DM( "Dominica", "DM", "DMA", 212, "dm", Currency.getInstance( "XCD" ), null  ),
	DO( "Dominican Republic", "DO", "DOM", 214, "do", Currency.getInstance( "DOP" ), "nnnnn"  ),
	EC( "Ecuador", "EC", "ECU", 218, "ec", Currency.getInstance( "USD" ), "ECnnnnnn"  ),
	EG( "Egypt", "EG", "EGY", 818, "eg", Currency.getInstance( "EGP" ), "nnnnn"  ),
	SV( "El Salvador", "SV", "SLV", 222, "sv", Currency.getInstance( "SVC" ), "nnnn"  ),
	GQ( "Equatorial Guinea", "GQ", "GNQ", 226, "gq", Currency.getInstance( "XAF" ), null  ),
	ER( "Eritrea", "ER", "ERI", 232, "er", Currency.getInstance( "ERN" ), null  ),
	EE( "Estonia", "EE", "EST", 233, "ee", Currency.getInstance( "EEK" ), "nnnnn" , et, en, de ),
	ET( "Ethiopia", "ET", "ETH", 231, "et", Currency.getInstance( "ETB" ), "nnnn"  ),
	FK( "Falkland Islands (Malvinas)", "FK", "FLK", 238, "fk", Currency.getInstance( "GBP" ), "FIQQ 1ZZ"  ),
	FO( "Faroe Islands", "FO", "FRO", 234, "fo", Currency.getInstance( "DKK" ), "nnn" , fo, da, en ),
	FJ( "Fiji", "FJ", "FJI", 242, "fj", Currency.getInstance( "FJD" ), null  ),
	FI( "Finland", "FI", "FIN", 246, "fi", Currency.getInstance( "EUR" ), "nnnnn" , fi, sv, en ),
	FR( "France", "FR", "FRA", 250, "fr", Currency.getInstance( "EUR" ), "fr" , fr ),
	GF( "French Guiana", "GF", "GUF", 254, "gf", Currency.getInstance( "EUR" ), "fr" ),
	PF( "French Polynesia", "PF", "PYF", 258, "pf", Currency.getInstance( "XPF" ), "fr" ),
	TF( "French Southern Territories", "TF", "ATF", 260, "tf", Currency.getInstance( "EUR" ), null  ),
	GA( "Gabon", "GA", "GAB", 266, "ga", Currency.getInstance( "XAF" ), "nn nn"  ),
	GM( "Gambia", "GM", "GMB", 270, "gm", Currency.getInstance( "GMD" ), null  ),
	GE( "Georgia", "GE", "GEO", 268, "ge", Currency.getInstance( "GEL" ), "nnnn"  ),
	DE( "Germany", "DE", "DEU", 276, "de", Currency.getInstance( "EUR" ), "nnnnn" , de ),
	GH( "Ghana", "GH", "GHA", 288, "gh", Currency.getInstance( "GHC" ), null  ),
	GI( "Gibraltar", "GI", "GIB", 292, "gi", Currency.getInstance( "GIP" ), null , en, es ),
	GR( "Greece", "GR", "GRC", 300, "gr", Currency.getInstance( "EUR" ), "nnnnn" , el, en ),
	GL( "Greenland", "GL", "GRL", 304, "gl", Currency.getInstance( "DKK" ), "nnnn" ),
	GD( "Grenada", "GD", "GRD", 308, "gd", Currency.getInstance( "XCD" ), null  ),
	GP( "Guadeloupe", "GP", "GLP", 312, "gp", Currency.getInstance( "EUR" ), "fr" ),
	GU( "Guam", "GU", "GUM", 316, "gu", Currency.getInstance( "USD" ), "us"  ),
	GT( "Guatemala", "GT", "GTM", 320, "gt", Currency.getInstance( "GTQ" ), "nnnnn"  ),
	GG( "Guernsey, Bailiwick of", "GG", "GGY", 831, "gg", Currency.getInstance( "GBP" ), "uk{GY}"  ),
	GN( "Guinea", "GN", "GIN", 324, "gn", Currency.getInstance( "GNF" ), null  ),
	GW( "Guinea-Bissau", "GW", "GNB", 624, "gw", Currency.getInstance( "XOF" ), "nnnn"  ),
	GY( "Guyana", "GY", "GUY", 328, "gy", Currency.getInstance( "GYD" ), null  ),
	HT( "Haiti", "HT", "HTI", 332, "ht", Currency.getInstance( "USD" ), "nnnn"  ),
	HM( "Heard and Mc Donald Islands", "HM", "HMD", 334, "hm", Currency.getInstance( "AUD" ), "nnnn"  ),
	VA( "Holy See", "VA", "VAT", 336, "va", Currency.getInstance( "EUR" ), "00120"  ),
	HN( "Honduras", "HN", "HND", 340, "hn", Currency.getInstance( "HNL" ), "nnnnn"  ),
	HK( "Hong Kong", "HK", "HKG", 344, "hk", Currency.getInstance( "HNL" ), "999077"  ),
	HU( "Hungary", "HU", null, 348, "hu", Currency.getInstance( "HUF" ), "nnnn" , hu, de, en ),
	IS( "Iceland", "IS", "ISL", 352, "is", Currency.getInstance( "HUF" ), "nnn" , is, en, da ),
	IN( "India", "IN", "IND", 356, "in", Currency.getInstance( "ISK" ), "nnn nnn"  ),
	ID( "Indonesia", "ID", "IDN", 360, "id", Currency.getInstance( "INR" ), "nnnnn"  ),
	IR( "Iran", "IR", "IRN", 364, "ir", Currency.getInstance( "IRR" ), "nnnnn nnnnn"  ),
	IQ( "Iraq", "IQ", "IRQ", 368, "iq", Currency.getInstance( "IDR" ), "nnnnn"  ),
	IE( "Ireland", "IE", "IRL", 372, "ie", Currency.getInstance( "EUR" ), "n?n?" , en ),
	IM( "Isle of Man", "IM", "IMN", 833, "im", Currency.getInstance( "GBP" ), "uk{IM}"  ),
	IL( "Israel", "IL", "ISR", 376, "il", Currency.getInstance( "ILS" ), "nnnnn"  ),
	IT( "Italy", "IT", "ITA", 380, "it", Currency.getInstance( "EUR" ), "nnnnn" , it ),
	CI( "Îvory Coast", "CI", "CIV", 384, "ci", Currency.getInstance( "XOF" ), null  ),
	JM( "Jamaica", "JM", "JAM", 388, "jm", Currency.getInstance( "JMD" ), "n?n?"  ),
	JP( "Japan", "JP", "JPN", 392, "jp", Currency.getInstance( "JPY" ), "nnn-nnnn"  ),
	JE( "Jersey", "JE", "JEY", 832, "je", Currency.getInstance( "GBP" ), "uk{JE}"  ),
	JO( "Jordan", "JO", "JOR", 400, "jo", Currency.getInstance( "JOD" ), "nnnnn"  ),
	KZ( "Kazakhstan", "KZ", "KAZ", 398, "kz", Currency.getInstance( "KZT" ), "nnnnnn"  ),
	KE( "Kenya", "KE", "KEN", 404, "ke", Currency.getInstance( "KES" ), "nnnnn"  ),
	KI( "Kiribati", "KI", "KIR", 296, "ki", Currency.getInstance( "AUD" ), null  ),
	KP( "Korea North", "KP", "PRK", 408, "kp", Currency.getInstance( "KPW" ), null  ),
	KR( "Korea South", "KR", "KOR", 410, "kr", Currency.getInstance( "KRW" ), "nnn-nnn"  ),
	KW( "Kuwait", "KW", "KWT", 414, "kw", Currency.getInstance( "KWD" ), "nnnnn"  ),
	KG( "Kyrgyzstan", "KG", "KGZ", 417, "kg", Currency.getInstance( "KGS" ), "nnnnnn"  ),
	LA( "Lao People's Democratic Republic", "LA", "LAO", 418, "la", Currency.getInstance( "LAK" ), "nnnnn"  ),
	LV( "Latvia", "LV", "LVA", 428, "lv", Currency.getInstance( "LVL" ), "nnnn" , lv, de, en ),
	LB( "Lebanon", "LB", "LBN", 422, "lb", Currency.getInstance( "LBP" ), "nnnn( nnnn)?"  ),
	LS( "Lesotho", "LS", "LSO", 426, "ls", Currency.getInstance( "LSL" ), "nnn"  ),
	LR( "Liberia", "LR", "LBR", 430, "lr", Currency.getInstance( "LRD" ), "nnnn"  ),
	LY( "Libyan Arab Jamahiriya", "LY", "LBY", 434, "ly", Currency.getInstance( "LYD" ), "nnnnn"  ),
	LI( "Liechtenstein", "LI", "LIE", 438, "li", Currency.getInstance( "CHF" ), null , de ),
	LT( "Lithuania", "LT", "LTU", 440, "lt", Currency.getInstance( "LTL" ), "nnnnn" , lt, ru, en ),
	LU( "Luxembourg", "LU", "LUX", 442, "lu", Currency.getInstance( "EUR" ), "nnnn" , lb, de, fr ),
	MO( "Macao", "MO", "MAC", 446, "mo", Currency.getInstance( "MOP" ), "999078"  ),
	MK( "Macedonia", "MK", "MKD", 807, "mk", Currency.getInstance( "MKD" ), "nnnn" , mk, en, it ),
	MG( "Madagascar", "MG", "MDG", 450, "mg", Currency.getInstance( "MGA" ), "nnn"  ),
	MW( "Malawi", "MW", "MWI", 454, "mw", Currency.getInstance( "MWK" ), null  ),
	MY( "Malaysia", "MY", "MYS", 458, "my", Currency.getInstance( "MYR" ), "nnnnn"  ),
	MV( "Maldives", "MV", "MDV", 462, "mv", Currency.getInstance( "MVR" ), "nn-nn"  ),
	ML( "Mali", "ML", "MLI", 466, "ml", Currency.getInstance( "XOF" ), null  ),
	MT( "Malta", "MT", "MDV", 470, "mv", Currency.getInstance( "MVR" ), "aaa nnnn", mt, en ),
	MH( "Marshall Islands", "MH", "MHL", 584, "mh", Currency.getInstance( "USD" ), "us"  ),
	MQ( "Martinique", "MQ", "MTQ", 474, "mq", Currency.getInstance( "EUR" ), "fr"  ),
	MR( "Mauritania", "MR", "MRT", 478, "mr", Currency.getInstance( "MRO" ), null  ),
	MU( "Mauritius", "MU", "MUS", 480, "mu", Currency.getInstance( "MUR" ), null  ),
	YT( "Mayotte", "YT", "MYT", 175, "yt", Currency.getInstance( "EUR" ), "fr"  ),
	MX( "Mexico", "MX", "MEX", 484, "mx", Currency.getInstance( "MXN" ), "nnnnn"  ),
	FM( "Micronesia", "FM", "FSM", 583, "fm", Currency.getInstance( "USD" ), "us"  ),
	MD( "Moldova", "MD", "MDA", 498, "md", Currency.getInstance( "MDL" ), "nnnn"  ),
	MC( "Monaco", "MC", "MCO", 492, "mc", Currency.getInstance( "EUR" ), "fr{MC}" , fr, it, en ),
	MN( "Mongolia", "MN", "MNG", 496, "mn", Currency.getInstance( "MNT" ), "nnnnnn"  ),
	ME( "Montenegro", "ME", "MNE", 499, "me", Currency.getInstance( "EUR" ), "nnnnn"  ),
	MS( "Montserrat", "MS", "MSR", 500, "ms", Currency.getInstance( "XCD" ), null  ),
	MA( "Morocco", "MA", "MAR", 504, "ma", Currency.getInstance( "MAD" ), "nnnnn"  ),
	MZ( "Mozambique", "MZ", "MOZ", 508, "mz", Currency.getInstance( "MZM" ), "nnnnn"  ),
	MM( "Myanmar", "MM", "MMR", 104, "mm", Currency.getInstance( "MMK" ), "nnnnn"  ),
	NA( "Namibia", "NA", "NAM", 516, "na", Currency.getInstance( "ZAR" ), null  ),
	NR( "Nauru", "NR", "NRU", 520, "nr", Currency.getInstance( "AUD" ), null  ),
	NP( "Nepal", "NP", "NPL", 524, "np", Currency.getInstance( "NPR" ), "nnnnn"  ),
	NL( "Netherlands", "NL", "NLD", 528, "nl", Currency.getInstance( "EUR" ), "nnn aa", nl, de, en ),
	AN( "Netherlands Antilles", "AN", "ANT", 530, "an", Currency.getInstance( "ANG" ), null  ),
	NC( "New Caledonia", "NC", "NCL", 540, "nc", Currency.getInstance( "XPF" ), "fr"  ),
	NZ( "New Zealand", "NZ", "NZL", 554, "nz", Currency.getInstance( "NZD" ), "nnnn"  ),
	NI( "Nicaragua", "NI", "NIC", 558, "ni", Currency.getInstance( "NIO" ), "nnn-nnn-n"  ),
	NE( "Niger", "NE", "NER", 562, "ne", Currency.getInstance( "XOF" ), "nnnn"  ),
	NG( "Nigeria", "NG", "NGA", 566, "ng", Currency.getInstance( "NGN" ), "nnnnnn"  ),
	NU( "Niue", "NU", "NIU", 570, "nu", Currency.getInstance( "NZD" ), null  ),
	NF( "Norfolk Island", "NF", "NFK", 574, "nf", Currency.getInstance( "AUD" ), "nnnn"  ),
	MP( "Northern Mariana Islands", "MP", "MNP", 580, "mp", Currency.getInstance( "USD" ), "us"  ),
	NO( "Norway", "NO", "NOR", 578, "no", Currency.getInstance( "NOK" ), "nnnn"  ),
	OM( "Oman", "OM", "OMN", 512, "om", Currency.getInstance( "OMR" ), "nnn"  ),
	PK( "Pakistan", "PK", "PAK", 586, "pk", Currency.getInstance( "PKR" ), "nnnnn"  ),
	PS( "Palastinian Territories", "PS", "PSE", 275, "ps", null, null  ),
	PW( "Palau", "PW", "PLW", 585, "pw", Currency.getInstance( "USD" ), "us"  ),
	PA( "Panama", "PA", "PAN", 591, "pa", Currency.getInstance( "USD" ), null  ),
	PG( "Papua New Guinea", "PG", "PNG", 598, "pg", Currency.getInstance( "PGK" ), "nnn"  ),
	PY( "Paraguay", "PY", "PRY", 600, "py", Currency.getInstance( "PYG" ), "nnnn"  ),
	PE( "Peru", "PE", "PER", 604, "pe", Currency.getInstance( "PEN" ), "(nn)?"  ),
	PH( "Philippines", "PH", "PHL", 608, "ph", Currency.getInstance( "PHP" ), "nnnn"  ),
	PN( "Pitcairn", "PN", "PCN", 612, "pn", Currency.getInstance( "NZD" ), "PCRN 1ZZ"  ),
	PL( "Poland", "PL", "POL", 616, "pl", Currency.getInstance( "PLN" ), "nn-nnn" , pl, ru, en, de ),
	PT( "Portugal", "PT", "PRT", 620, "pt", Currency.getInstance( "EUR" ), "nnnn-nnn" , pt, es, en, fr ),
	PR( "Puerto Rico", "PR", "PRI", 630, "pr", Currency.getInstance( "USD" ), "us"  ),
	QA( "Qatar", "QA", "QAT", 634, "qa", Currency.getInstance( "QAR" ), null  ),
	RE( "Réunion", "RE", "REU", 638, "re", Currency.getInstance( "EUR" ), "fr"  ),
	RO( "Romania", "RO", "ROU", 642, "ro", Currency.getInstance( "RON" ), "nnnnnn" , ro, en, fr, de, it, es ),
	RU( "Russian Federation", "RU", "RUS", 643, "ru", Currency.getInstance( "RUB" ), "nnnnnn"  ),
	RW( "Rwanda", "RW", "RWA", 646, "rw", Currency.getInstance( "RWF" ), null  ),
	SH( "Saint Helena", "SH", "SHN", 654, "sh", Currency.getInstance( "SHP" ), "STHL 1ZZ"  ),
	KN( "Saint Kitts and Nevis", "KN", "KNA", 659, "kn", Currency.getInstance( "XCD" ), null  ),
	LC( "Saint Lucia", "LC", "LCA", 662, "lc", Currency.getInstance( "XCD" ), null  ),
	PM( "Saint Pierre and Miquelon", "PM", "SPM", 666, "pm", Currency.getInstance( "EUR" ), "fr"  ),
	VC( "Saint Vincent and the Grenadines", "VC", "VCT", 670, "vc", Currency.getInstance( "XCD" ), null  ),
	WS( "Samoa", "WS", "WSM", 882, "ws", Currency.getInstance( "WST" ), "us"  ),
	SM( "San Marino", "SM", "SMR", 674, "sm", Currency.getInstance( "EUR" ), "nnnnn" , it, en ),
	ST( "Sao Tome and Principe", "ST", "STP", 678, "st", Currency.getInstance( "STD" ), null  ),
	SA( "Saudi Arabia", "SA", "SAU", 682, "sa", Currency.getInstance( "SAR" ), "nnnnn"  ),
	SN( "Senegal", "SN", "SEN", 686, "sn", Currency.getInstance( "XOF" ), "nnnnn"  ),
	RS( "Serbia", "RS", "SRB", 688, "rs", null, "nnnnn"  ),
	SC( "Seychelles", "SC", "SYC", 690, "sc", Currency.getInstance( "SCR" ), null  ),
	SL( "Sierra Leone", "SL", "SLE", 694, "sl", Currency.getInstance( "SLL" ), null  ),
	SG( "Singapore", "SG", "SGP", 702, "sg", Currency.getInstance( "SGD" ), "nnnnnn"  ),
	SK( "Slovakia", "SK", "SVK", 703, "sk", Currency.getInstance( "SKK" ), "nnn nn" , sk, en, de ),
	SI( "Slovenia", "SI", "SVN", 705, "si", Currency.getInstance( "SIT" ), "nnnn" , sl, hr, en, de ),
	SB( "Solomon Islands", "SB", "SLB", 90, "sb", Currency.getInstance( "SBD" ), null  ),
	SO( "Somalia", "SO", "SOM", 706, "so", Currency.getInstance( "SOS" ), null  ),
	ZA( "South Africa", "ZA", "ZAF", 710, "za", Currency.getInstance( "ZAR" ), "nnnn"  ),
	GS( "South Georgia and Sandwich Islands", "GS", "SGS", 239, null, Currency.getInstance( "GBP" ), "SIQQ 1ZZ"  ),
	ES( "Spain", "ES", "ESP", 724, "es", Currency.getInstance( "EUR" ), "nnnnn" , es, en ),
	LK( "Sri Lanka", "LK", "LKA", 144, "lk", Currency.getInstance( "LKR" ), "nnnnn"  ),
	SD( "Sudan", "SD", "SDN", 736, "sd", Currency.getInstance( "SDD" ), "nnnn"  ),
	SR( "Suriname", "SR", "SUR", 740, "sr", Currency.getInstance( "SRD" ), null  ),
	SJ( "Svalbard and Jan Mayen Islands", "SJ", "SJM", 744, "sj", Currency.getInstance( "NOK" ), null  ),
	SZ( "Swaziland", "SZ", "SWZ", 748, "sz", Currency.getInstance( "SZL" ), "annn"  ),
	SE( "Sweden", "SE", "SWE", 752, "se", Currency.getInstance( "SEK" ), "nnn nn" , sv, en, de, fr ),
	CH( "Switzerland", "CH", "CHE", 756, "ch", Currency.getInstance( "CHF" ), "nnnn" , de ),
	SY( "Syrian Arab Republic", "SY", "SYR", 760, "sy", Currency.getInstance( "SYP" ), null  ),
	TW( "Taiwan", "TW", "TWN", 158, "tw", Currency.getInstance( "TWD" ), "nnnnn"  ),
	TJ( "Tajikistan", "TJ", "TJK", 762, "tj", Currency.getInstance( "RUB" ), "nnnnnn"  ),
	TZ( "Tanzania", "TZ", "TZA", 834, "tz", Currency.getInstance( "TZS" ), null  ),
	TH( "Thailand", "TH", "THA", 764, "th", Currency.getInstance( "THB" ), "nnnnn"  ),
	TL( "Timor-Leste", "TL", "TLS", 626, "tl", Currency.getInstance( "IDR" ), null  ),
	TG( "Togo", "TG", "TGO", 768, "tg", Currency.getInstance( "XOF" ), null  ),
	TK( "Tokelau", "TK", "TKL", 772, "tk", Currency.getInstance( "NZD" ), null  ),
	TO( "Tonga", "TO", "TON", 776, "to", Currency.getInstance( "TOP" ), null  ),
	TT( "Trinidad and Tobago", "TT", "TTO", 780, "tt", Currency.getInstance( "TTD" ), null  ),
	TN( "Tunisia", "TN", "TUN", 788, "tn", Currency.getInstance( "TND" ), "nnnn"  ),
	TR( "Turkey", "TR", "TUR", 792, "tr", Currency.getInstance( "TRY" ), "nnnnn"  ),
	TM( "Turkmenistan", "TM", "TKM", 795, "tm", Currency.getInstance( "TMM" ), "nnnnnn"  ),
	TC( "Turks and Caicos Islands", "TC", "TCA", 796, "tc", Currency.getInstance( "USD" ), "TKCA 1ZZ"  ),
	TV( "Tuvalu", "TV", "TUV", 798, "tv", Currency.getInstance( "GBP" ), null  ),
	UG( "Uganda", "UG", "UGA", 800, "ug", Currency.getInstance( "UGX" ), null  ),
	UA( "Ukraine", "UA", "UKR", 804, "ua", Currency.getInstance( "UAH" ), "nnnnn"  ),
	AE( "United Arab Emirates", "AE", "ARE", 784, "ae", Currency.getInstance( "AED" ), null  ),
	GB( "United Kingdom", "GB", "GBR", 826, "gb", Currency.getInstance( "GBP" ), "uk" , en ),
	US( "United States", "US", "USA", 840, "us", Currency.getInstance( "USD" ), "us" , en ),
	UY( "Uruguay", "UY", "URY", 858, "uy", Currency.getInstance( "UYU" ), "nnnnn"  ),
	UZ( "Uzbekistan", "UZ", "UZB", 860, "uz", Currency.getInstance( "UZS" ), "nnnnnn"  ),
	VU( "Vanuatu", "VU", "VUT", 548, "vu", Currency.getInstance( "VUV" ), null  ),
	VE( "Venezuela", "VE", "VEN", 862, "ve", Currency.getInstance( "VEB" ), "nnnn( a)?"  ),
	VN( "Viet Nam", "VN", "VNM", 704, "vn", Currency.getInstance( "VND" ), "nnnnnn"  ),
	VG( "Virgin Islands, British", "VG", "VGB", 92, "vg", Currency.getInstance( "USD" ), "VGnnnn"  ),
	VI( "Virgin Islands, U.S.", "VI", "VIR", 850, "vi", Currency.getInstance( "USD" ), "us"  ),
	WF( "Wallis and Futuna", "WF", "WLF", 876, "wf", Currency.getInstance( "XPF" ), "fr"  ),
	EH( "Western Sahara", "EH", "ESH", 732, "eh", Currency.getInstance( "MAD" ), null  ),
	YE( "Yemen", "YE", "YEM", 887, "ye", Currency.getInstance( "YER" ), null  ),
	ZM( "Zambia", "ZM", "ZMB", 894, "zm", Currency.getInstance( "ZMK" ), "nnnnn"  ),
	ZW( "Zimbabwe", "ZW", "ZWE", 716, "zw", Currency.getInstance( "ZWD" ), null  ),
	// -- END GENERATED --
	;
	
	private String commonName;
	private String iso2;
	private String iso3;
	private int isoN;
	private String ccTld;
	private Currency currency;
	private String postalcode;
	private Locale [] locales;
	private static HashMap<String,Country>forIso2 = new HashMap<String,Country>();
	private static HashMap<Integer,Country>forIsoN = new HashMap<Integer,Country>();
	static {
		for( Country c : Country.values() ){
			
			forIso2.put( c.iso2(), c );
			forIsoN.put( c.isoN, c );
		}
	}
	
	Country( String commonName, String iso2, String iso3, int isoN,
			String ccTld, Currency currency, String postalcode, Language ... language ){
		
		this.commonName = commonName;
		this.iso2 = iso2;
		this.iso3 = iso3;
		this.isoN = isoN;
		this.ccTld = ccTld;
		this.currency = currency;
		this.postalcode = postalcode;
		Locale [] l = new Locale[ language.length ];
		for( int i=0; i<language.length; i++ ){
			l[i] = new Locale( language[i].code() );
		}
		this.locales = l;
	}
	
	public String commonName(){ return commonName; }
	public String iso2(){ return iso2; }
	public String iso3(){ return iso3; }
	public int isoN(){ return isoN; }
	public String ccTld(){ return ccTld; }
	public Currency currency(){ return currency; }
	public String postalcode(){ return postalcode; }
	public Locale [] locales(){ return locales; }
	
	public static Country forIso2( String iso2 ){ return forIso2.get( iso2.toUpperCase() ); }
	public static Country forIsoN( int isoN ){ return forIsoN.get( isoN ); }
}
