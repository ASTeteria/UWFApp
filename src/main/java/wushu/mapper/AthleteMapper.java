package wushu.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import wushu.dto.AthleteDTO;
import wushu.entity.Athlete;

@Mapper(componentModel = "spring")
public interface AthleteMapper {
//    AthleteMapper INSTANCE = Mappers.getMapper(AthleteMapper.class);

//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "firstName", source = "firstName")
//    @Mapping(target = "lastName", source = "lastName")
//    @Mapping(target = "birthDate", source = "birthDate")
//    @Mapping(target = "programType", source = "programType")
    AthleteDTO toDto(Athlete athlete);

//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "firstName", source = "firstName")
//    @Mapping(target = "lastName", source = "lastName")
//    @Mapping(target = "birthDate", source = "birthDate")
//    @Mapping(target = "programType", source = "programType")
    Athlete toEntity(AthleteDTO athleteDTO);

    void updateEntity(@MappingTarget Athlete athlete, AthleteDTO athleteDTO);


//    AthleteDTO toEntity(Athlete athlete);
}
