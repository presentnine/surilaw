package ga.surilaw.domain.dto;

import ga.surilaw.domain.entity.Precedent;

import java.util.List;

public class PrecedentSearchResponseDto {
    int totalCount;
    List<Precedent> precedentList;

    public PrecedentSearchResponseDto(int totalCount, List<Precedent> precedentList) {
        this.totalCount = totalCount;
        this.precedentList = precedentList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Precedent> getPrecedentList() {
        return precedentList;
    }

    public void setPrecedentList(List<Precedent> precedentList) {
        this.precedentList = precedentList;
    }
}
