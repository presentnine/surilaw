package ga.surilaw.domain.entity;

public class Precedent {
    int idNum;
    String caseName;
    String caseNum;
    String date;
    String court;

    public Precedent(int idNum, String caseName, String caseNum, String date, String court) {
        this.idNum = idNum;
        this.caseName = caseName;
        this.caseNum = caseNum;
        this.date = date;
        this.court = court;
    }

    public int getIdNum() {
        return idNum;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }
}
