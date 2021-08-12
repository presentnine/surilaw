package ga.surilaw.entity.enumType;

import lombok.Getter;

@Getter
public enum Category {
    ASK("ASK"), INFO("INFO"), FREE("FREE"), ALL("ALL");

    private String value;

    Category(String value){
        this.value = value;
    }
}
