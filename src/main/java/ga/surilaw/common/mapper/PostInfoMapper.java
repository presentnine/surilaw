package ga.surilaw.common.mapper;

import ga.surilaw.domain.dto.board.InsertPostInfoDto;
import ga.surilaw.domain.dto.board.ReadPostInfoDto;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.domain.entity.enumType.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PostInfoMapper {


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "insertPostInfoDto.title", target = "postTitle")
    @Mapping(source = "insertPostInfoDto.content", target = "postContent")
    PostInformation insertDtoToEntity(InsertPostInfoDto insertPostInfoDto);


    default PostInformation updateDtoToEntity(InsertPostInfoDto insertPostInfoDto, PostInformation postInformation){
        if(insertPostInfoDto.getCategory() != null){
            postInformation.updateCategory(Category.valueOf(insertPostInfoDto.getCategory()));
        }
        if(insertPostInfoDto.getTitle() != null){
            postInformation.updateTitle(insertPostInfoDto.getTitle());
        }
        if(insertPostInfoDto.getContent() != null){
            postInformation.updateContent(insertPostInfoDto.getContent());
        }
        return postInformation;
    }


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "member.memberName", target = "memberName")
    @Mapping(source = "postTitle", target = "title")
    @Mapping(source = "postContent", target = "content")
    ReadPostInfoDto entityToReadDto(PostInformation postInformation);
}


