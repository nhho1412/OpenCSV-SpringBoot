package com.tiger.demoOpenCSV.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiger.demoOpenCSV.Repository.CountryRepository;
import com.tiger.demoOpenCSV.logic.OpenCsvLogic;
import com.tiger.demoOpenCSV.model.Country;

@Controller
@RequestMapping(value = "/main")
public class OpenCsvController {
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	OpenCsvLogic openCsvLogic;
	
	@ResponseBody
	@RequestMapping(value = "/demoCsvReader")
	public String demoCsvReader(@RequestParam("path") String path) throws IOException {
		List<Country> countries = openCsvLogic.ParseCsvToObjectUsingAnnotation(path);
		
		Integer idMax = this.countryRepository.getMaxId();
		String result = "";
		result += "<ul>";
		for (Country country: countries) {
			idMax++;
			country.setId(idMax);
			
			this.countryRepository.save(country);
			result += "<li> inserted " + country.getCode() + " - " + country.getName() + "</li>";
		}
		result += "</ul>";
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/")
	public String home() {
		String html = "";

		html += "<ul>";
		html += " <li><a href='/main/testInsert'>Test Insert</a></li>";
		html += " <li><a href='/main/showAllCountry'>Show All Country</a></li>";
		html += " <li><a href='/main/showCodeLikeVN'>Show All 'VN'</a></li>";
		html += " <li><a href='/main/deleteAllCountry'>Delete All Country</a></li>";
		html += "</ul>";

		return html;
	}

	private String getRandomCountry() {
		String country[] = { "AA-(Aruba)", "AC-Antigua và Barbuda", "AE-Các Tiểu Vương quốc Ả Rập Thống nhất",
				"AF-Afghanistan", "AG-Algérie", "AJ-Azerbaijan", "AL-Albania", "AM-Armenia", "AN-Andorra", "AO-Angola",
				"AQ-(Samoa thuộc Mỹ)", "AR-Argentina", "AS-Úc", "AT-(Quần đảo Ashmore và Cartier)", "AU-Áo",
				"AV-(Anguilla)", "AX-(Akrotiri)", "AY-(Nam Cực)", "BA-Bahrain", "BB-Barbados", "BC-Botswana",
				"BD-(Bermuda)", "BE-Bỉ", "BF-Bahamas", "BG-Bangladesh", "BH-Belize", "BK-Bosna và Hercegovina",
				"BL-Bolivia", "BM-Myanma", "BN-Bénin", "BO-Belarus", "BP-Quần đảo Solomon", "BQ-(Đảo Navassa)",
				"BR-Brasil", "BS-(Bassas da India)", "BT-Bhutan", "BU-Bulgaria", "BV-(Đảo Bouvet)", "BX-Brunei",
				"BY-Burundi", "CA-Canada", "CB-Campuchia", "CD-Tchad", "CE-Sri Lanka", "CF-Cộng hòa Congo",
				"CG-Cộng hòa Dân chủ Congo", "CH-Cộng hòa Nhân dân Trung Hoa", "CI-Chile", "CJ-(Quần đảo Cayman)",
				"CK-(Quần đảo Cocos (Keeling))", "CM-Cameroon", "CN-Comoros", "CO-Colombia",
				"CQ-(Quần đảo Bắc Mariana)", "CR-(Quần đảo Coral Sea)", "CS-Costa Rica", "CT-Cộng hòa Trung Phi",
				"CU-Cuba", "CV-Cabo Verde", "CW-(Quần đảo Cook)", "CY-Kypros", "DA-Đan Mạch", "DJ-Djibouti",
				"DO-Dominica", "DQ-(Đảo Jarvis)", "DR-Cộng hòa Dominican", "DX-(Dhekelia)", "DE-(Đức)", "EC-Ecuador",
				"EG-Ai Cập", "EI-Ireland", "EK-Guinea Xích đạo", "EN-Estonia", "ER-Eritrea", "ES-El Salvador",
				"ET-Ethiopia", "EU-(Đảo Europa)", "EZ-Cộng hòa Séc", "FG-(Guyane)", "FI-Phần Lan", "FJ-Fiji",
				"FK-(Quần đảo Falkland/Quần đảo Malvinas)", "FM-Liên bang Micronesia", "FO-(Quần đảo Faroe)",
				"FP-(Polynésie thuộc Pháp)", "FQ-(Đảo Baker)", "FR-Pháp", "FS-(Lãnh thổ phía Nam Pháp)", "GA-Gambia",
				"GB-Gabon", "GG-Gruzia", "GH-Ghana", "GI-(Gibraltar)", "GJ-Grenada", "GK-(Guernsey)", "GL-(Greenland)",
				"GM-Đức", "GO-(Quần đảo Glorioso)", "GP-(Guadeloupe)", "GQ-(Guam)", "GR-Hy Lạp", "GT-Guatemala",
				"GV-Guinée", "GY-Guyana", "GZ-(Dải Gaza)", "HA-Haiti", "HK-(Hồng Kông)",
				"HM-(Đảo Heard và quần đảo McDonald)", "HO-Honduras", "HQ-(Đảo Howland)", "HR-Croatia", "HU-Hungary",
				"IC-Iceland", "ID-Indonesia", "IM-(Đảo Man)", "IN-Ấn Độ", "IO-(Lãnh thổ Ấn Độ Dương thuộc Anh)",
				"IP-(Đảo Clipperton)", "IR-Iran", "IS-Israel", "IT-Ý", "IV-Côte d'Ivoire", "IZ-Iraq", "JA-Nhật Bản",
				"JE-(Jersey)", "JM-Jamaica", "JN-(Jan Mayen)", "JO-Jordan", "JQ-(Đảo Johnston)",
				"JU-(Đảo Juan de Nova)", "KE-Kenya", "KG-Kyrgyzstan", "KM-Bắc Triều Tiên", "KQ-(Đảo đá Kingman)",
				"KR-Kiribati", "KS-Hàn Quốc", "KT-(Đảo Giáng Sinh)", "KU-Kuwait", "KZ-Kazakhstan", "LA-Lào", "LE-Liban",
				"LG-Latvia", "LH-Litva", "LI-Liberia", "LO-Slovakia", "LS-Liechtenstein", "LT-Lesotho", "LU-Luxembourg",
				"LY-Libya", "MA-Madagascar", "MB-(Martinique)", "MC-(Ma Cao)", "MD-Moldova", "MF-(Mayotte)",
				"MG-Mông Cổ", "MH-(Montserrat)", "MI-Malawi", "MJ-Montenegro", "MK-Macedonia", "ML-Mali", "MN-Monaco",
				"MO-Maroc", "MP-Mauritius", "MQ-(Quần đảo Midway)", "MR-Mauritanie", "MT-Malta", "MU-Oman",
				"MV-Maldives", "MX-México", "MY-Malaysia", "MZ-Mozambique", "NC-(Nouvelle-Calédonie)", "NE-(Niue)",
				"NF-(Đảo Norfolk)", "NG-Niger", "NH-Vanuatu", "NI-Nigeria", "NL-Hà Lan", "NO-Na Uy", "NP-Nepal",
				"NR-Nauru", "NS-Suriname", "NT-(Antille thuộc Hà Lan)", "NU-Nicaragua", "NZ-New Zealand", "PA-Paraguay",
				"PC-(Quần đảo Pitcairn)", "PE-Peru", "PF-(Quần đảo Hoàng Sa)", "PG-(Quần đảo Trường Sa)", "PK-Pakistan",
				"PL-Ba Lan", "PM-Panama", "PO-Bồ Đào Nha", "PP-Papua New Guinea", "PS-Palau", "PU-Guiné-Bissau",
				"QA-Qatar", "RB-Serbia", "RE-(Réunion)", "RM-Quần đảo Marshall", "RO-România", "RP-Philippines",
				"RQ-(Puerto Rico)", "RS-Nga", "RW-Rwanda", "SA-Ả Rập Xê Út", "SB-(Saint-Pierre và Miquelon)",
				"SC-Saint Kitts và Nevis", "SE-Seychelles", "SF-Nam Phi", "SG-Sénégal", "SH-(Saint Helena)",
				"SI-Slovenia", "SL-Sierra Leone", "SM-San Marino", "SN-Singapore", "SO-Somalia", "SP-Tây Ban Nha",
				"ST-Saint Lucia", "SU-Sudan", "SV-(Svalbard)", "SW-Thụy Điển",
				"SX-(Nam Georgia và Quần đảo Nam Sandwich)", "SY-Syria", "SZ-Thụy Sĩ", "TD-Trinidad và Tobago",
				"TE-(Đảo Tromelin)", "TH-Thái Lan", "TI-Tajikistan", "TK-(Quần đảo Turks và Caicos)", "TL-(Tokelau)",
				"TN-Tonga", "TO-Togo", "TP-São Tomé và Príncipe", "TS-Tunisia", "TT-Đông Timor", "TU-Thổ Nhĩ Kỳ",
				"TV-Tuvalu", "TW-Trung Hoa Dân quốc (Đài Loan)", "TX-Turkmenistan", "TZ-Tanzania", "UG-Uganda",
				"UK-Vương quốc Liên hiệp Anh và Bắc Ireland", "UP-Ukraina", "US-Hoa Kỳ", "UV-Burkina Faso",
				"UY-Uruguay", "UZ-Uzbekistan", "VC-Saint Vincent và Grenadines", "VE-Venezuela",
				"VI-(Quần đảo Virgin thuộc Anh)", "VN-Việt Nam", "VQ-(Quần đảo Virgin thuộc Mỹ)", "VT-Vatican",
				"WA-Namibia", "WE-(Bờ Tây)", "WF-(Wallis và Futuna)", "WI-(Tây Sahara)", "WQ-(Đảo Wake)", "WS-Samoa",
				"WZ-Swaziland" };
		
		int randomNumber = new Random().nextInt(country.length);
		return country[randomNumber];
	}

	@ResponseBody
	@RequestMapping("/testInsert")
	public String testInsert() {
		Integer idMax = this.countryRepository.getMaxId();
		int id = idMax + 1;
		String codeAndName[] = this.getRandomCountry().split("-");
		
		Country country = new Country();
		country.setId(id);
		country.setCode(codeAndName[0]);
		country.setName(codeAndName[1]);
		
		this.countryRepository.save(country);
		
		String result = "insert successfuly /n";
		result += " <li><a href='/main/'>Return Main</a></li>";
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/showAllCountry")
	public String showAllCountry() {
		Iterable<Country> allCOuntry = this.countryRepository.findAll();

		String result = "list country:";
		int i = 1;
		result += "<ul>";
		for (Country country: allCOuntry) {
			result += "<li>" + i + "." + country.getCode() + " - " + country.getName() + "</li>";
			i++;
		}
		result += "</ul>";
		
		return result;
	}
}
