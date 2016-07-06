package de.axone.i18n;

import java.util.HashMap;
import java.util.Map;

public enum Language {
	
	ab( "Abkhaz", "аҧсуа бызшәа, аҧсшәа", "ab", "abk", "abk", "abk", "abks" ),
	aa( "Afar", "Afaraf", "aa", "aar", "aar", "aar", "aars" ),
	af( "Afrikaans", "Afrikaans", "af", "afr", "afr", "afr", "afrs" ),
	ak( "Akan", "Akan", "ak", "aka", "aka", "aka + 2", "" ),
	sq( "Albanian", "Shqip", "sq", "sqi", "alb", "sqi + 4", "", "en" ),
	am( "Amharic", "አማርኛ ", "am", "amh", "amh", "amh", "" ),
	ar( "Arabic", "العربية", "ar", "ara", "ara", "ara + 30", "", "fr" ),
	an( "Aragonese", "aragonés", "an", "arg", "arg", "arg", "" ),
	hy( "Armenian", "Հայերեն", "hy", "hye", "arm", "hye", "" ),
	as( "Assamese", "অসমীয়া", "as", "asm", "asm", "asm", "" ),
	av( "Avaric", "авар мацӀ, магӀарул мацӀ", "av", "ava", "ava", "ava", "" ),
	ae( "Avestan", "avesta", "ae", "ave", "ave", "ave", "" ),
	ay( "Aymara", "aymar aru", "ay", "aym", "aym", "aym + 2", "" ),
	az( "Azerbaijani", "azərbaycan dili", "az", "aze", "aze", "aze + 2", "" ),
	bm( "Bambara", "bamanankan", "bm", "bam", "bam", "bam", "" ),
	ba( "Bashkir", "башҡорт теле", "ba", "bak", "bak", "bak", "" ),
	eu( "Basque", "euskara, euskera", "eu", "eus", "baq", "eus", "" ),
	be( "Belarusian", "беларуская мова", "be", "bel", "bel", "bel", "", "ru", "en" ),
	bn( "Bengali, Bangla", "বাংলা", "bn", "ben", "ben", "ben", "" ),
	bh( "Bihari", "भोजपुरी", "bh", "bih", "bih", "", "" ),
	bi( "Bislama", "Bislama", "bi", "bis", "bis", "bis", "" ),
	bs( "Bosnian", "bosanski jezik", "bs", "bos", "bos", "bos", "boss", "hr", "sr", "de", "en" ),
	br( "Breton", "brezhoneg", "br", "bre", "bre", "bre", "" ),
	bg( "Bulgarian", "български език", "bg", "bul", "bul", "bul", "buls", "de", "en" ),
	my( "Burmese", "ဗမာစာ", "my", "mya", "bur", "mya", "" ),
	ca( "Catalan", "català", "ca", "cat", "cat", "cat", "", "es", "fr" ), //Catalan, not Canadian!
	ch( "Chamorro", "Chamoru", "ch", "cha", "cha", "cha", "" ),
	ce( "Chechen", "нохчийн мотт", "ce", "che", "che", "che", "" ),
	ny( "Chichewa, Chewa, Nyanja", "chiCheŵa, chinyanja", "ny", "nya", "nya", "nya", "" ),
	zh( "Chinese", "中文 (Zhōngwén), 汉语, 漢語", "zh", "zho", "chi", "zho + 13", "" ),
	cv( "Chuvash", "чӑваш чӗлхи", "cv", "chv", "chv", "chv", "" ),
	kw( "Cornish", "Kernewek", "kw", "cor", "cor", "cor", "" ),
	co( "Corsican", "corsu, lingua corsa", "co", "cos", "cos", "cos", "" ),
	cr( "Cree", "ᓀᐦᐃᔭᐍᐏᐣ", "cr", "cre", "cre", "cre + 6", "" ),
	hr( "Croatian", "hrvatski jezik", "hr", "hrv", "hrv", "hrv", "" ),
	cs( "Czech", "čeština, český jazyk", "cs", "ces", "cze", "ces", "", "en" ),
	da( "Danish", "dansk", "da", "dan", "dan", "dan", "", "en" ),
	dv( "Divehi, Dhivehi, Maldivian", "ދިވެހި", "dv", "div", "div", "div", "" ),
	nl( "Dutch", "Nederlands, Vlaams", "nl", "nld", "dut", "nld", "", "de", "en" ),
	dz( "Dzongkha", "རྫོང་ཁ", "dz", "dzo", "dzo", "dzo", "" ),
	en( "English", "English", "en", "eng", "eng", "eng", "engs" ),
	eo( "Esperanto", "Esperanto", "eo", "epo", "epo", "epo", "" ),
	et( "Estonian", "eesti, eesti keel", "et", "est", "est", "est + 2", "", "en", "de" ),
	ee( "Ewe", "Eʋegbe", "ee", "ewe", "ewe", "ewe", "" ),
	fo( "Faroese", "føroyskt", "fo", "fao", "fao", "fao", "", "da", "en" ),
	fj( "Fijian", "vosa Vakaviti", "fj", "fij", "fij", "fij", "" ),
	fi( "Finnish", "suomi, suomen kieli", "fi", "fin", "fin", "fin", "", "sv", "en" ),
	fr( "French", "français, langue française", "fr", "fra", "fre", "fra", "fras", "it", "en" ),
	ff( "Fula, Fulah, Pulaar, Pular", "Fulfulde, Pulaar, Pular", "ff", "ful", "ful", "ful + 9", "" ),
	gl( "Galician", "galego", "gl", "glg", "glg", "glg", "" ),
	ka( "Georgian", "ქართული", "ka", "kat", "geo", "kat", "" ),
	de( "German", "Deutsch", "de", "deu", "ger", "deu", "deus" ),
	el( "Greek (modern)", "ελληνικά", "el", "ell", "gre", "ell", "ells", "tr", "en" ),
	gn( "Guaraní", "Avañe'ẽ", "gn", "grn", "grn", "grn + 5", "" ),
	gu( "Gujarati", "ગુજરાતી", "gu", "guj", "guj", "guj", "" ),
	ht( "Haitian, Haitian Creole", "Kreyòl ayisyen", "ht", "hat", "hat", "hat", "" ),
	ha( "Hausa", "(Hausa) هَوُسَ", "ha", "hau", "hau", "hau", "" ),
	he( "Hebrew (modern)", "עברית", "he", "heb", "heb", "heb", "" ),
	hz( "Herero", "Otjiherero", "hz", "her", "her", "her", "" ),
	hi( "Hindi", "हिन्दी, हिंदी", "hi", "hin", "hin", "hin", "hins" ),
	ho( "Hiri Motu", "Hiri Motu", "ho", "hmo", "hmo", "hmo", "" ),
	hu( "Hungarian", "magyar", "hu", "hun", "hun", "hun", "", "de", "en" ),
	ia( "Interlingua", "Interlingua", "ia", "ina", "ina", "ina", "" ),
	id( "Indonesian", "Bahasa Indonesia", "id", "ind", "ind", "ind", "" ),
	ie( "//Interlingue", "Originally called Occidental; then Interlingue after WWII", "ie", "ile", "ile", "ile", "" ),
	ga( "Irish", "Gaeilge", "ga", "gle", "gle", "gle", "" ),
	ig( "Igbo", "Asụsụ Igbo", "ig", "ibo", "ibo", "ibo", "" ),
	ik( "Inupiaq", "Iñupiaq, Iñupiatun", "ik", "ipk", "ipk", "ipk + 2", "" ),
	io( "Ido", "Ido", "io", "ido", "ido", "ido", "idos" ),
	is( "Icelandic", "Íslenska", "is", "isl", "ice", "isl", "", "en", "da" ),
	it( "Italian", "italiano", "it", "ita", "ita", "ita", "itas", "en", "fr" ),
	iu( "Inuktitut", "ᐃᓄᒃᑎᑐᑦ", "iu", "iku", "iku", "iku + 2", "" ),
	ja( "Japanese", "日本語 (にほんご)", "ja", "jpn", "jpn", "jpn", "" ),
	jv( "Javanese", "ꦧꦱꦗꦮ", "jv", "jav", "jav", "jav", "" ),
	kl( "Kalaallisut, Greenlandic", "kalaallisut, kalaallit oqaasii", "kl", "kal", "kal", "kal", "" ),
	kn( "Kannada", "ಕನ್ನಡ", "kn", "kan", "kan", "kan", "" ),
	kr( "Kanuri", "Kanuri", "kr", "kau", "kau", "kau + 3", "" ),
	ks( "Kashmiri", "कश्मीरी, كشميري‎", "ks", "kas", "kas", "kas", "" ),
	kk( "Kazakh", "қазақ тілі", "kk", "kaz", "kaz", "kaz", "" ),
	km( "Khmer", "ខ្មែរ, ខេមរភាសា, ភាសាខ្មែរ", "km", "khm", "khm", "khm", "" ),
	ki( "Kikuyu, Gikuyu", "Gĩkũyũ", "ki", "kik", "kik", "kik", "" ),
	rw( "Kinyarwanda", "Ikinyarwanda", "rw", "kin", "kin", "kin", "" ),
	ky( "Kyrgyz", "Кыргызча, Кыргыз тили", "ky", "kir", "kir", "kir", "" ),
	kv( "Komi", "коми кыв", "kv", "kom", "kom", "kom + 2", "" ),
	kg( "Kongo", "Kikongo", "kg", "kon", "kon", "kon + 3", "" ),
	ko( "Korean", "한국어, 조선어", "ko", "kor", "kor", "kor", "" ),
	ku( "Kurdish", "Kurdî, كوردی‎", "ku", "kur", "kur", "kur + 3", "" ),
	kj( "Kwanyama, Kuanyama", "Kuanyama", "kj", "kua", "kua", "kua", "" ),
	la( "Latin", "latine, lingua latina", "la", "lat", "lat", "lat", "" ),
	lb( "Luxembourgish, Letzeburgesch", "Lëtzebuergesch", "lb", "ltz", "ltz", "ltz", "", "de", "fr" ),
	lg( "Ganda", "Luganda", "lg", "lug", "lug", "lug", "" ),
	li( "Limburgish, Limburgan, Limburger", "Limburgs", "li", "lim", "lim", "lim", "" ),
	ln( "Lingala", "Lingála", "ln", "lin", "lin", "lin", "" ),
	lo( "Lao", "ພາສາລາວ", "lo", "lao", "lao", "lao", "" ),
	lt( "Lithuanian", "lietuvių kalba", "lt", "lit", "lit", "lit", "", "ru", "en" ),
	lu( "Luba-Katanga", "Tshiluba", "lu", "lub", "lub", "lub", "" ),
	lv( "Latvian", "latviešu valoda", "lv", "lav", "lav", "lav + 2", "", "de", "en" ),
	gv( "Manx", "Gaelg, Gailck", "gv", "glv", "glv", "glv", "" ),
	mk( "Macedonian", "македонски јазик", "mk", "mkd", "mac", "mkd", "", "en", "it" ),
	mg( "Malagasy", "fiteny malagasy", "mg", "mlg", "mlg", "mlg + 10", "" ),
	ms( "Malay", "bahasa Melayu, بهاس ملايو‎", "ms", "msa", "may", "msa + 13", "" ),
	ml( "Malayalam", "മലയാളം", "ml", "mal", "mal", "mal", "" ),
	mt( "Maltese", "Malti", "mt", "mlt", "mlt", "mlt", "", "en" ),
	mi( "Māori", "te reo Māori", "mi", "mri", "mao", "mri", "" ),
	mr( "Marathi (Marāṭhī)", "मराठी", "mr", "mar", "mar", "mar", "" ),
	mh( "Marshallese", "Kajin M̧ajeļ", "mh", "mah", "mah", "mah", "" ),
	mn( "Mongolian", "Монгол хэл", "mn", "mon", "mon", "mon + 2", "" ),
	na( "Nauruan", "Dorerin Naoero", "na", "nau", "nau", "nau", "" ),
	nv( "Navajo, Navaho", "Diné bizaad", "nv", "nav", "nav", "nav", "" ),
	nd( "Northern Ndebele", "isiNdebele", "nd", "nde", "nde", "nde", "" ),
	ne( "Nepali", "नेपाली", "ne", "nep", "nep", "nep", "" ),
	ng( "Ndonga", "Owambo", "ng", "ndo", "ndo", "ndo", "" ),
	nb( "Norwegian Bokmål", "Norsk bokmål", "nb", "nob", "nob", "nob", "" ),
	nn( "Norwegian Nynorsk", "Norsk nynorsk", "nn", "nno", "nno", "nno", "" ),
	no( "Norwegian", "Norsk", "no", "nor", "nor", "nor + 2", "", "de", "en" ),
	ii( "Nuosu", "ꆈꌠ꒿ Nuosuhxop", "ii", "iii", "iii", "iii", "" ),
	nr( "Southern Ndebele", "isiNdebele", "nr", "nbl", "nbl", "nbl", "" ),
	oc( "Occitan", "occitan, lenga d'òc", "oc", "oci", "oci", "oci", "" ),
	oj( "Ojibwe, Ojibwa", "ᐊᓂᔑᓈᐯᒧᐎᓐ", "oj", "oji", "oji", "oji + 7", "" ),
	cu( "Old Church Slavonic, Church Slavonic, Old Bulgarian", "ѩзыкъ словѣньскъ", "cu", "chu", "chu", "chu", "" ),
	om( "Oromo", "Afaan Oromoo", "om", "orm", "orm", "orm + 4", "" ),
	or( "Oriya", "ଓଡ଼ିଆ", "or", "ori", "ori", "ori", "" ),
	os( "Ossetian, Ossetic", "ирон æвзаг", "os", "oss", "oss", "oss", "" ),
	pa( "Panjabi, Punjabi", "ਪੰਜਾਬੀ, پنجابی‎", "pa", "pan", "pan", "pan", "" ),
	pi( "Pāli", "पाऴि", "pi", "pli", "pli", "pli", "" ),
	fa( "Persian (Farsi)", "فارسی", "fa", "fas", "per", "fas + 2", "", "en" ),
	pl( "Polish", "język polski, polszczyzna", "pl", "pol", "pol", "pol", "pols", "ru", "en", "de" ),
	ps( "Pashto, Pushto", "پښتو", "ps", "pus", "pus", "pus + 3", "" ),
	pt( "Portuguese", "português", "pt", "por", "por", "por", "", "sp", "en", "fr" ),
	qu( "Quechua", "Runa Simi, Kichwa", "qu", "que", "que", "que + 44", "" ),
	rm( "Romansh", "rumantsch grischun", "rm", "roh", "roh", "roh", "" ),
	rn( "Kirundi", "Ikirundi", "rn", "run", "run", "run", "" ),
	rc( "Reunionese, Reunion Creole", "Kréol Rénioné", "rc", "rcf", "rcf", "rcf", "" ),
	ro( "Romanian", "limba română", "ro", "ron", "rum", "ron", "", "en", "fr", "de", "it", "es" ),
	ru( "Russian", "Русский", "ru", "rus", "rus", "rus", "", "en" ),
	sa( "Sanskrit (Saṁskṛta)", "संस्कृतम्", "sa", "san", "san", "san", "" ),
	sc( "Sardinian", "sardu", "sc", "srd", "srd", "srd + 4", "" ),
	sd( "Sindhi", "सिन्धी, سنڌي، سندھی‎", "sd", "snd", "snd", "snd", "" ),
	se( "Northern Sami", "Davvisámegiella", "se", "sme", "sme", "sme", "" ),
	sm( "Samoan", "gagana fa'a Samoa", "sm", "smo", "smo", "smo", "", "en" ),
	sg( "Sango", "yângâ tî sängö", "sg", "sag", "sag", "sag", "" ),
	sr( "Serbian", "српски језик", "sr", "srp", "srp", "srp", "", "de", "en" ),
	gd( "Scottish Gaelic, Gaelic", "Gàidhlig", "gd", "gla", "gla", "gla", "" ),
	sn( "Shona", "chiShona", "sn", "sna", "sna", "sna", "" ),
	si( "Sinhalese, Sinhala", "සිංහල", "si", "sin", "sin", "sin", "" ),
	sk( "Slovak", "slovenčina, slovenský jazyk", "sk", "slk", "slo", "slk", "", "en", "de" ),
	sl( "Slovene", "slovenski jezik, slovenščina", "sl", "slv", "slv", "slv", "", "hr", "en", "de" ),
	so( "Somali", "Soomaaliga, af Soomaali", "so", "som", "som", "som", "" ),
	st( "Southern Sotho", "Sesotho", "st", "sot", "sot", "sot", "" ),
	es( "Spanish", "español", "es", "spa", "spa", "spa", "", "en" ),
	su( "Sundanese", "Basa Sunda", "su", "sun", "sun", "sun", "" ),
	sw( "Swahili", "Kiswahili", "sw", "swa", "swa", "swa + 2", "" ),
	ss( "Swati", "SiSwati", "ss", "ssw", "ssw", "ssw", "" ),
	sv( "Swedish", "svenska", "sv", "swe", "swe", "swe", "", "en", "de", "fr" ),
	ta( "Tamil", "தமிழ்", "ta", "tam", "tam", "tam", "" ),
	te( "Telugu", "తెలుగు", "te", "tel", "tel", "tel", "" ),
	tg( "Tajik", "тоҷикӣ, toçikī, تاجیکی‎", "tg", "tgk", "tgk", "tgk", "" ),
	th( "Thai", "ไทย", "th", "tha", "tha", "tha", "" ),
	ti( "Tigrinya", "ትግርኛ", "ti", "tir", "tir", "tir", "" ),
	bo( "Tibetan Standard, Tibetan, Central", "བོད་ཡིག", "bo", "bod", "tib", "bod", "" ),
	tk( "Turkmen", "Türkmen, Түркмен", "tk", "tuk", "tuk", "tuk", "" ),
	tl( "Tagalog", "Wikang Tagalog", "tl", "tgl", "tgl", "tgl", "" ),
	tn( "Tswana", "Setswana", "tn", "tsn", "tsn", "tsn", "" ),
	to( "Tonga (Tonga Islands)", "faka Tonga", "to", "ton", "ton", "ton", "" ),
	tr( "Turkish", "Türkçe", "tr", "tur", "tur", "tur", "" ),
	ts( "Tsonga", "Xitsonga", "ts", "tso", "tso", "tso", "" ),
	tt( "Tatar", "татар теле, tatar tele", "tt", "tat", "tat", "tat", "" ),
	tw( "Twi", "Twi", "tw", "twi", "twi", "twi", "" ),
	ty( "Tahitian", "Reo Tahiti", "ty", "tah", "tah", "tah", "" ),
	ug( "Uyghur", "ئۇيغۇرچە‎, Uyghurche", "ug", "uig", "uig", "uig", "" ),
	uk( "Ukrainian", "Українська", "uk", "ukr", "ukr", "ukr", "" ),
	ur( "Urdu", "اردو", "ur", "urd", "urd", "urd", "" ),
	uz( "Uzbek", "Oʻzbek, Ўзбек, أۇزبېك‎", "uz", "uzb", "uzb", "uzb + 2", "" ),
	ve( "Venda", "Tshivenḓa", "ve", "ven", "ven", "ven", "" ),
	vi( "Vietnamese", "Tiếng Việt", "vi", "vie", "vie", "vie", "" ),
	vo( "Volapük", "Volapük", "vo", "vol", "vol", "vol", "" ),
	wa( "Walloon", "walon", "wa", "wln", "wln", "wln", "" ),
	cy( "Welsh", "Cymraeg", "cy", "cym", "wel", "cym", "" ),
	wo( "Wolof", "Wollof", "wo", "wol", "wol", "wol", "" ),
	fy( "Western Frisian", "Frysk", "fy", "fry", "fry", "fry", "" ),
	xh( "Xhosa", "isiXhosa", "xh", "xho", "xho", "xho", "" ),
	yi( "Yiddish", "ייִדיש", "yi", "yid", "yid", "yid + 2", "" ),
	yo( "Yoruba", "Yorùbá", "yo", "yor", "yor", "yor", "" ),
	za( "Zhuang, Chuang", "Saɯ cueŋƅ, Saw cuengh", "za", "zha", "zha", "zha + 16", "" ),
	zu( "Zulu", "isiZulu", "zu", "zul", "zul", "zul", "" ),
	;
		
	private final String name;
	private final String nameLocal;
	private final String iso2;
	private final String iso3;
	private final String[] fallback;
	
	private static Map<String,Language> forIso2 = new HashMap<>();
	private static Map<String,Language> forIso3 = new HashMap<>();
	static {
		for( Language lang : Language.values() ) {
			forIso2.put( lang.iso2(), lang );
			forIso3.put( lang.iso3(), lang );
		}
	}
	
	Language( String nameEn, String nameLocal, 
			String iso639_1, String iso639_2T, String iso639_2B, String iso639_3, String iso639_6, 
			String ... fallback_iso2 ) {
		
		this.name = nameEn;
		this.nameLocal = nameLocal;
		this.iso2 = iso639_1;
		this.iso3 = iso639_2B;
		this.fallback = fallback_iso2;
	}
	
	public static Language forIso2( String iso2 ) {
		Language lang = forIso2.get( iso2 );
		if( lang == null ) throw new IllegalArgumentException( "Unknown: " + iso2 );
		return lang;
	}
	public static Language forIso3( String iso3 ) {
		Language lang = forIso3.get( iso3 );
		if( lang == null ) throw new IllegalArgumentException( "Unknown: " + iso3 );
		return lang;
	}
	
	
	public String iso2(){ return iso2; }
	public String iso3(){ return iso3; }
	public String [] fallback(){ return fallback; }
	public String nameLocal(){ return nameLocal; }
	public String nameEn(){ return name; }
	@Override public String toString(){ return name; }
}