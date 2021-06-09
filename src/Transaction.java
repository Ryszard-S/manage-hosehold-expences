public class Transaction {
    private  Integer tranzakcja_Id;
    private  String data;
    private  Double kwota;
    private  String kategoria;

    public Transaction(Integer tranzakcja_Id, String data, Double kwota, String kategoria){
        this.tranzakcja_Id=tranzakcja_Id;
        this.data=data;
        this.kwota=kwota;
        this.kategoria=kategoria;
    }

    public Integer getTranzakcja_Id() {
        return tranzakcja_Id;
    }

    public void setTranzakcja_Id(Integer tranzakcja_Id) {
        this.tranzakcja_Id = tranzakcja_Id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getKwota() {
        return kwota;
    }

    public void setKwota(Double kwota) {
        this.kwota = kwota;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }
}
