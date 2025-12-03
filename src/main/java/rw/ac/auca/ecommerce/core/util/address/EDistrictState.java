package rw.ac.auca.ecommerce.core.util.address;

public enum EDistrictState {
    // Kigali City
    GASABO(EProvinceState.KIGALI),
    KICUKIRO(EProvinceState.KIGALI),
    NYARUGENGE(EProvinceState.KIGALI),

    // Northern Province
    GICUMBI(EProvinceState.NORTHERN),
    MUSANZE(EProvinceState.NORTHERN),
    GAKENKE(EProvinceState.NORTHERN),
    RULINDO(EProvinceState.NORTHERN),
    BURERA(EProvinceState.NORTHERN),

    // Southern Province
    HUYE(EProvinceState.SOUTHERN),
    GISAGARA(EProvinceState.SOUTHERN),
    NYANZA(EProvinceState.SOUTHERN),
    NYAMAGABE(EProvinceState.SOUTHERN),
    MUHANGA(EProvinceState.SOUTHERN),
    NYARUGURU(EProvinceState.SOUTHERN),
    KAMONYI(EProvinceState.SOUTHERN),
    RUHANGO(EProvinceState.SOUTHERN),

    // Eastern Province
    RWAMAGANA(EProvinceState.EASTERN),
    KAYONZA(EProvinceState.EASTERN),
    BUGESERA(EProvinceState.EASTERN),
    GATSIBO(EProvinceState.EASTERN),
    NGOMA(EProvinceState.EASTERN),
    KIREHE(EProvinceState.EASTERN),
    NYAGATARE(EProvinceState.EASTERN),

    // Western Province
    RUBAVU(EProvinceState.WESTERN),
    RUSIZI(EProvinceState.WESTERN),
    KARONGI(EProvinceState.WESTERN),
    RUTSIRO(EProvinceState.WESTERN),
    NGORORERO(EProvinceState.WESTERN),
    NYAMASHEKE(EProvinceState.WESTERN),
    NYABIHU(EProvinceState.WESTERN);



    private final EProvinceState province;

    EDistrictState(EProvinceState province) {
        this.province = province;
    }

    public EProvinceState getProvince() {
        return province;
    }

}
