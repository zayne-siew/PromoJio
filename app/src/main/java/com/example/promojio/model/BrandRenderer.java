package com.example.promojio.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.promojio.R;

public abstract class BrandRenderer {

    public final static String BRAND_3M = "3M";
    public final static String BRAND_ADIDAS = "Adidas";
    public final static String BRAND_AIRBNB = "Airbnb";
    public final static String BRAND_AMAZON = "Amazon";
    public final static String BRAND_AMERICAN_AIRLINES = "American Airlines";
    public final static String BRAND_AMERICAN_EXPRESS = "American Express";
    public final static String BRAND_APPLE = "Apple";
    public final static String BRAND_AUDI = "Audi";
    public final static String BRAND_BMW = "BMW";
    public final static String BRAND_BOEING = "Boeing";
    public final static String BRAND_BURBERRY = "Burberry";
    public final static String BRAND_CALVIN_KLEIN = "Calvin Klein";
    public final static String BRAND_CARTIER = "Cartier";
    public final static String BRAND_CHANEL = "Chanel";
    public final static String BRAND_CHEVROLET = "Chevrolet";
    public final static String BRAND_CISCO = "Cisco";
    public final static String BRAND_COCA_COLA = "Coca-Cola";
    public final static String BRAND_COLGATE = "Colgate";
    public final static String BRAND_COLGATE_PALMOLIVE = "Colgate-Palmolive";
    public final static String BRAND_DELL = "Dell";
    public final static String BRAND_DELTA_AIR = "Delta Air Lines";
    public final static String BRAND_DISNEY = "Disney";
    public final static String BRAND_EBAY = "eBay";
    public final static String BRAND_EMIRATES = "Emirates";
    public final static String BRAND_EXXONMOBIL = "ExxonMobil";
    public final static String BRAND_FACEBOOK = "Facebook";
    public final static String BRAND_FEDEX = "FedEx";
    public final static String BRAND_FERRARI = "Ferrari";
    public final static String BRAND_FORD = "Ford";
    public final static String BRAND_GAP = "Gap";
    public final static String BRAND_GENERAL_ELECTRIC = "General Electric";
    public final static String BRAND_GENERAL_MILLS = "General Mills";
    public final static String BRAND_GENERAL_MOTORS = "General Motors";
    public final static String BRAND_GOLDMAN_SACHS = "Goldman Sachs";
    public final static String BRAND_GOOGLE = "Google";
    public final static String BRAND_GUCCI = "Gucci";
    public final static String BRAND_HM = "H&M";
    public final static String BRAND_HEWLETT_PACKARD = "Hewlett Packard";
    public final static String BRAND_HONDA = "Honda";
    public final static String BRAND_HP = "HP";
    public final static String BRAND_HUAWEI = "Huawei";
    public final static String BRAND_HYUNDAI = "Hyundai";
    public final static String BRAND_IBM = "IBM";
    public final static String BRAND_IKEA = "IKEA";
    public final static String BRAND_INTEL = "Intel";
    public final static String BRAND_JOHNSON_JOHNSON = "Johnson & Johnson";
    public final static String BRAND_KELLOGGS = "Kellogg's";
    public final static String BRAND_KFC = "KFC";
    public final static String BRAND_KIA = "Kia";
    public final static String BRAND_LOREAL = "L'Oréal";
    public final static String BRAND_LENOVO = "Lenovo";
    public final static String BRAND_LG = "LG";
    public final static String BRAND_LOUIS_VUITTON = "Louis Vuitton";
    public final static String BRAND_MASTERCARD = "Mastercard";
    public final static String BRAND_MCDONALDS = "McDonald's";
    public final static String BRAND_MERCEDES_BENZ = "Mercedes-Benz";
    public final static String BRAND_MICROSOFT = "Microsoft";
    public final static String BRAND_NESTLE = "Nestlé";
    public final static String BRAND_NETFLIX = "Netflix";
    public final static String BRAND_NIKE = "Nike";
    public final static String BRAND_NISSAN = "Nissan";
    public final static String BRAND_ORACLE = "Oracle";
    public final static String BRAND_PANASONIC = "Panasonic";
    public final static String BRAND_PEPSI = "Pepsi";
    public final static String BRAND_PEPSICO = "PepsiCo";
    public final static String BRAND_PFIZER = "Pfizer";
    public final static String BRAND_PORSCHE = "Porsche";
    public final static String BRAND_PRADA = "Prada";
    public final static String BRAND_PROCTER_GAMBLE = "Procter & Gamble";
    public final static String BRAND_QUALCOMM = "Qualcomm";
    public final static String BRAND_RALPH_LAUREN = "Ralph Lauren";
    public final static String BRAND_ROLEX = "Rolex";
    public final static String BRAND_SAMSUNG = "Samsung";
    public final static String BRAND_SHELL = "Shell";
    public final static String BRAND_SIEMENS = "Siemens";
    public final static String BRAND_SONY = "Sony";
    public final static String BRAND_SOUTHWEST_AIRLINES = "Southwest Airlines";
    public final static String BRAND_STARBUCKS = "Starbucks";
    public final static String BRAND_SUBARU = "Subaru";
    public final static String BRAND_SUBWAY = "Subway";
    public final static String BRAND_TARGET = "Target";
    public final static String BRAND_TIFFANY_CO = "Tiffany & Co.";
    public final static String BRAND_TOMMY_HILFIGER = "Tommy Hilfiger";
    public final static String BRAND_TOYOTA = "Toyota";
    public final static String BRAND_UPS = "UPS";
    public final static String BRAND_VERIZON = "Verizon";
    public final static String BRAND_VISA = "Visa";
    public final static String BRAND_WALMART = "Walmart";
    public final static String BRAND_ZARA = "Zara";

    public final static int ERROR_LOGO = R.drawable.unknown;
    public final static String ERROR_URL = "<ERROR>";
    public final static String LOG_TAG = "LOGCAT_BrandRenderer";

    public static int getBrandLogo(@NonNull String brandName) {
        switch (brandName) {
            case BRAND_3M:
                return R.drawable.three_m;
            case BRAND_ADIDAS:
                return R.drawable.adidas;
            case BRAND_AIRBNB:
                return R.drawable.airbnb;
            case BRAND_AMAZON:
                return R.drawable.amazon;
            case BRAND_AMERICAN_AIRLINES:
                return R.drawable.american_airlines;
            case BRAND_AMERICAN_EXPRESS:
                return R.drawable.american_express;
            case BRAND_APPLE:
                return R.drawable.apple;
            case BRAND_AUDI:
                return R.drawable.audi;
            case BRAND_BMW:
                return R.drawable.bmw;
            case BRAND_BOEING:
                return R.drawable.boeing;
            case BRAND_BURBERRY:
                return R.drawable.burberry;
            case BRAND_CALVIN_KLEIN:
                return R.drawable.calvin_klein;
            case BRAND_CARTIER:
                return R.drawable.cartier;
            case BRAND_CHANEL:
                return R.drawable.chanel;
            case BRAND_CHEVROLET:
                return R.drawable.chevrolet;
            case BRAND_CISCO:
                return R.drawable.cisco;
            case BRAND_COCA_COLA:
                return R.drawable.coca_cola;
            case BRAND_COLGATE:
                return R.drawable.colgate;
            case BRAND_COLGATE_PALMOLIVE:
                return R.drawable.colgate_palmolive;
            case BRAND_DELL:
                return R.drawable.dell;
            case BRAND_DELTA_AIR:
                return R.drawable.delta_air;
            case BRAND_DISNEY:
                return R.drawable.disney;
            case BRAND_EBAY:
                return R.drawable.ebay;
            case BRAND_EMIRATES:
                return R.drawable.emirates;
            case BRAND_EXXONMOBIL:
                return R.drawable.exxonmobil;
            case BRAND_FACEBOOK:
                return R.drawable.facebook;
            case BRAND_FEDEX:
                return R.drawable.fedex;
            case BRAND_FERRARI:
                return R.drawable.ferrari;
            case BRAND_FORD:
                return R.drawable.ford;
            case BRAND_GAP:
                return R.drawable.gap;
            case BRAND_GENERAL_ELECTRIC:
                return R.drawable.general_electric;
            case BRAND_GENERAL_MILLS:
                return R.drawable.general_mills;
            case BRAND_GENERAL_MOTORS:
                return R.drawable.general_motors;
            case BRAND_GOLDMAN_SACHS:
                return R.drawable.goldman_sachs;
            case BRAND_GOOGLE:
                return R.drawable.google;
            case BRAND_GUCCI:
                return R.drawable.gucci;
            case BRAND_HM:
                return R.drawable.hm;
            case BRAND_HEWLETT_PACKARD:
                return R.drawable.hpe;
            case BRAND_HONDA:
                return R.drawable.honda;
            case BRAND_HP:
                return R.drawable.hp;
            case BRAND_HUAWEI:
                return R.drawable.huawei;
            case BRAND_HYUNDAI:
                return R.drawable.hyundai;
            case BRAND_IBM:
                return R.drawable.ibm;
            case BRAND_IKEA:
                return R.drawable.ikea;
            case BRAND_INTEL:
                return R.drawable.intel;
            case BRAND_JOHNSON_JOHNSON:
                return R.drawable.jnj;
            case BRAND_KELLOGGS:
                return R.drawable.kelloggs;
            case BRAND_KFC:
                return R.drawable.kfc;
            case BRAND_KIA:
                return R.drawable.kia;
            case BRAND_LOREAL:
                return R.drawable.loreal;
            case BRAND_LENOVO:
                return R.drawable.lenovo;
            case BRAND_LG:
                return R.drawable.lg;
            case BRAND_LOUIS_VUITTON:
                return R.drawable.louis_vuitton;
            case BRAND_MASTERCARD:
                return R.drawable.mastercard;
            case BRAND_MCDONALDS:
                return R.drawable.mcdonald;
            case BRAND_MERCEDES_BENZ:
                return R.drawable.mercedez_benz;
            case BRAND_MICROSOFT:
                return R.drawable.microsoft;
            case BRAND_NESTLE:
                return R.drawable.nestle;
            case BRAND_NETFLIX:
                return R.drawable.netflix;
            case BRAND_NIKE:
                return R.drawable.nike;
            case BRAND_NISSAN:
                return R.drawable.nissan;
            case BRAND_ORACLE:
                return R.drawable.oracle;
            case BRAND_PANASONIC:
                return R.drawable.panasonic;
            case BRAND_PEPSI:
                return R.drawable.pepsi;
            case BRAND_PEPSICO:
                return R.drawable.pepsico;
            case BRAND_PFIZER:
                return R.drawable.pfizer;
            case BRAND_PORSCHE:
                return R.drawable.porsche;
            case BRAND_PRADA:
                return R.drawable.prada;
            case BRAND_PROCTER_GAMBLE:
                return R.drawable.proctor_gamble;
            case BRAND_QUALCOMM:
                return R.drawable.qualcomm;
            case BRAND_RALPH_LAUREN:
                return R.drawable.ralph_lauren;
            case BRAND_ROLEX:
                return R.drawable.rolex;
            case BRAND_SAMSUNG:
                return R.drawable.samsung;
            case BRAND_SHELL:
                return R.drawable.shell;
            case BRAND_SIEMENS:
                return R.drawable.siemens;
            case BRAND_SONY:
                return R.drawable.sony;
            case BRAND_SOUTHWEST_AIRLINES:
                return R.drawable.southwest_airlines;
            case BRAND_STARBUCKS:
                return R.drawable.starbucks;
            case BRAND_SUBARU:
                return R.drawable.subaru;
            case BRAND_SUBWAY:
                return R.drawable.subway;
            case BRAND_TARGET:
                return R.drawable.target;
            case BRAND_TIFFANY_CO:
                return R.drawable.tiffany_co;
            case BRAND_TOMMY_HILFIGER:
                return R.drawable.tommy_hilfiger;
            case BRAND_TOYOTA:
                return R.drawable.toyota;
            case BRAND_UPS:
                return R.drawable.ups;
            case BRAND_VERIZON:
                return R.drawable.verizon;
            case BRAND_VISA:
                return R.drawable.visa;
            case BRAND_WALMART:
                return R.drawable.walmart;
            case BRAND_ZARA:
                return R.drawable.zara;
            default:
                Log.e(LOG_TAG, "Unrecognised brand name: " + brandName);
                return ERROR_LOGO;
        }
    }

    public static String getBrandURL(@NonNull String brandName) {
        switch (brandName) {
            case BRAND_3M:
                return "https://www.3m.com/";
            case BRAND_ADIDAS:
                return "https://www.adidas.com/";
            case BRAND_AIRBNB:
                return "https://www.airbnb.com/";
            case BRAND_AMAZON:
                return "https://www.amazon.com/";
            case BRAND_AMERICAN_AIRLINES:
                return "https://www.aa.com/";
            case BRAND_AMERICAN_EXPRESS:
                return "https://www.americanexpress.com/";
            case BRAND_APPLE:
                return "https://www.apple.com/";
            case BRAND_AUDI:
                return "https://www.audi.com/";
            case BRAND_BMW:
                return "https://www.bmw.com/";
            case BRAND_BOEING:
                return "https://www.boeing.com/";
            case BRAND_BURBERRY:
                return "https://www.burberry.com/";
            case BRAND_CALVIN_KLEIN:
                return "https://www.calvinklein.us/";
            case BRAND_CARTIER:
                return "https://www.cartier.com/";
            case BRAND_CHANEL:
                return "https://www.chanel.com/";
            case BRAND_CHEVROLET:
                return "https://www.chevrolet.com/";
            case BRAND_CISCO:
                return "https://www.cisco.com/";
            case BRAND_COCA_COLA:
                return "https://www.coca-colacompany.com/";
            case BRAND_COLGATE:
                return "https://www.colgate.com/";
            case BRAND_COLGATE_PALMOLIVE:
                return "https://www.colgatepalmolive.com/";
            case BRAND_DELL:
                return "https://www.dell.com/";
            case BRAND_DELTA_AIR:
            return "https://www.delta.com/";
            case BRAND_DISNEY:
                return "https://www.disney.com/";
            case BRAND_EBAY:
                return "https://www.ebay.com/";
            case BRAND_EMIRATES:
                return "https://www.emirates.com/";
            case BRAND_EXXONMOBIL:
                return "https://www.exxonmobil.com/";
            case BRAND_FACEBOOK:
                return "https://www.facebook.com/";
            case BRAND_FEDEX:
                return "https://www.fedex.com/";
            case BRAND_FERRARI:
                return "https://www.ferrari.com/";
            case BRAND_FORD:
                return "https://www.ford.com/";
            case BRAND_GAP:
                return "https://www.gap.com/";
            case BRAND_GENERAL_ELECTRIC:
                return "https://www.ge.com/";
            case BRAND_GENERAL_MILLS:
                return "https://www.generalmills.com/";
            case BRAND_GENERAL_MOTORS:
                return "https://www.gm.com/";
            case BRAND_GOLDMAN_SACHS:
                return "https://www.gs.com/";
            case BRAND_GOOGLE:
                return "https://www.google.com/";
            case BRAND_GUCCI:
                return "https://www.gucci.com/";
            case BRAND_HM:
                return "https://www.hm.com/";
            case BRAND_HEWLETT_PACKARD:
                return "https://www.hpe.com/";
            case BRAND_HONDA:
                return "https://www.honda.com/";
            case BRAND_HP:
                return "https://www.hp.com/";
            case BRAND_HUAWEI:
                return "https://www.huawei.com/";
            case BRAND_HYUNDAI:
                return "https://www.hyundai.com/";
            case BRAND_IBM:
                return "https://www.ibm.com/";
            case BRAND_IKEA:
                return "https://www.ikea.com/";
            case BRAND_INTEL:
                return "https://www.intel.com/";
            case BRAND_JOHNSON_JOHNSON:
                return "https://www.jnj.com/";
            case BRAND_KELLOGGS:
                return "https://www.kelloggs.com/";
            case BRAND_KFC:
                return "https://www.kfc.com/";
            case BRAND_KIA:
                return "https://www.kia.com/";
            case BRAND_LOREAL:
                return "https://www.loreal.com/";
            case BRAND_LENOVO:
                return "https://www.lenovo.com/";
            case BRAND_LG:
                return "https://www.lg.com/";
            case BRAND_LOUIS_VUITTON:
                return "https://www.louisvuitton.com/";
            case BRAND_MASTERCARD:
                return "https://www.mastercard.us/";
            case BRAND_MCDONALDS:
                return "https://www.mcdonalds.com/";
            case BRAND_MERCEDES_BENZ:
                return "https://www.mercedes-benz.com/";
            case BRAND_MICROSOFT:
                return "https://www.microsoft.com/";
            case BRAND_NESTLE:
                return "https://www.nestle.com/";
            case BRAND_NETFLIX:
                return "https://www.netflix.com/";
            case BRAND_NIKE:
                return "https://www.nike.com/";
            case BRAND_NISSAN:
                return "https://www.nissanusa.com/";
            case BRAND_ORACLE:
                return "https://www.oracle.com/";
            case BRAND_PANASONIC:
                return "https://www.panasonic.com/";
            case BRAND_PEPSI:
                return "https://www.pepsi.com/";
            case BRAND_PEPSICO:
                return "https://www.pepsico.com/";
            case BRAND_PFIZER:
                return "https://www.pfizer.com/";
            case BRAND_PORSCHE:
                return "https://www.porsche.com/";
            case BRAND_PRADA:
                return "https://www.prada.com/";
            case BRAND_PROCTER_GAMBLE:
                return "https://www.pg.com/";
            case BRAND_QUALCOMM:
                return "https://www.qualcomm.com/";
            case BRAND_RALPH_LAUREN:
                return "https://www.ralphlauren.com/";
            case BRAND_ROLEX:
                return "https://www.rolex.com/";
            case BRAND_SAMSUNG:
                return "https://www.samsung.com/";
            case BRAND_SHELL:
                return "https://www.shell.com/";
            case BRAND_SIEMENS:
                return "https://www.siemens.com/";
            case BRAND_SONY:
                return "https://www.sony.com/";
            case BRAND_SOUTHWEST_AIRLINES:
                return "https://www.southwest.com/";
            case BRAND_STARBUCKS:
                return "https://www.starbucks.com/";
            case BRAND_SUBARU:
                return "https://www.subaru.com/";
            case BRAND_SUBWAY:
                return "https://www.subway.com/";
            case BRAND_TARGET:
                return "https://www.target.com/";
            case BRAND_TIFFANY_CO:
                return "https://www.tiffany.com/";
            case BRAND_TOMMY_HILFIGER:
                return "https://www.tommy.com/";
            case BRAND_TOYOTA:
                return "https://www.toyota.com/";
            case BRAND_UPS:
                return "https://www.ups.com/";
            case BRAND_VERIZON:
                return "https://www.verizon.com/";
            case BRAND_VISA:
                return "https://www.visa.com/";
            case BRAND_WALMART:
                return "https://www.walmart.com/";
            case BRAND_ZARA:
                return "https://www.zara.com/";
            default:
                Log.e(LOG_TAG, "Unrecognised brand name: " + brandName);
                return ERROR_URL;
        }
    }
}
