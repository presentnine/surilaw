package ga.surilaw.common.mapper;

import ga.surilaw.domain.dto.InsertPostInfoDto;
import ga.surilaw.domain.entity.PostInformation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PostInfoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "title", target = "postTitle")
    @Mapping(source = "content", target = "postContent")
    PostInformation insertDtoToEntity(InsertPostInfoDto insertPostInfoDto);
}


