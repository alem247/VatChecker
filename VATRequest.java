public class VATRequest {

    private String countryCode;
    private String vatNumber;

    public VATRequest(String countryCode, String vatNumber) {
        this.countryCode = countryCode;
        this.vatNumber = vatNumber;
    }

    public String getCountryCode()  {
        return countryCode;
    }

    public String getVatNumber()  {
        return vatNumber;
    }

    public void setCountryCode(String countryCode)  {
        this.countryCode = countryCode;
    }

    public void setVatNumber(String vatNumber)  {
        this.vatNumber = vatNumber;
    }




}