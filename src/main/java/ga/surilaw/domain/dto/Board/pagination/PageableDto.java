package ga.surilaw.domain.dto.board.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableDto {

    int page = 0;
    int size = 20;

    //나중에 sort Setting 추가해야함.
    String sort = "postId";

    //ASC or DESC
    String order;

    //filter String
    String filter;

    FilterDto filters;

    public void setFilters(){
        String[] parse = filter.split(",");

        HashMap<String, String> parseMap =  new HashMap<>();

        for(String s : parse){
            String[] token = s.split(":");
            parseMap.put(token[0],token[1]);
        }

        filters = new FilterDto(parseMap.get("category"),
                parseMap.get("name"), parseMap.get("title"),parseMap.get("content"));
    }
}
