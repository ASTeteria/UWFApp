//package wushu.mapper;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.MappingTarget;
//import org.mapstruct.factory.Mappers;
//import wushu.dto.CompetitionApplicationDTO;
//import wushu.entity.CompetitionApplication;
//@Mapper(componentModel = "spring")
//public interface CompetitionApplicationMapper {
////    CompetitionApplicationMapper INSTANCE = Mappers.getMapper(CompetitionApplicationMapper.class);
//
////    @Mapping(target = "id", source = "id")
////    @Mapping(target = "competitionName", source = "competitionName")
////    @Mapping(target = "athleteFirstName", source = "athleteFirstName")
////    @Mapping(target = "athleteLastName", source = "athleteLastName")
////    @Mapping(target = "birthDate", source = "birthDate")
////    @Mapping(target = "gender", source = "gender")
////    @Mapping(target = "weaponlessProgram", source = "weaponlessProgram")
////    @Mapping(target = "shortWeaponProgram", source = "shortWeaponProgram")
////    @Mapping(target = "longWeaponProgram", source = "longWeaponProgram")
////    @Mapping(target = "duilian", source = "duilian")
//    CompetitionApplicationDTO toDto(CompetitionApplication competitionApplication);
//
////    @Mapping(target = "id", source = "id")
////    @Mapping(target = "competitionName", source = "competitionName")
////    @Mapping(target = "athleteFirstName", source = "athleteFirstName")
////    @Mapping(target = "athleteLastName", source = "athleteLastName")
////    @Mapping(target = "birthDate", source = "birthDate")
////    @Mapping(target = "gender", source = "gender")
////    @Mapping(target = "weaponlessProgram", source = "weaponlessProgram")
////    @Mapping(target = "shortWeaponProgram", source = "shortWeaponProgram")
////    @Mapping(target = "longWeaponProgram", source = "longWeaponProgram")
////    @Mapping(target = "duilian", source = "duilian")
//    CompetitionApplication toEntity(CompetitionApplicationDTO competitionApplicationDTO);
//
////    void updateEntity(@MappingTarget CompetitionApplication competitionApplication, CompetitionApplicationDTO competitionApplicationDTO);
//}
package wushu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import wushu.dto.CompetitionApplicationDTO;
import wushu.entity.CompetitionApplication;

@Mapper(componentModel = "spring")
public interface CompetitionApplicationMapper {
    CompetitionApplicationDTO toDto(CompetitionApplication competitionApplication);
    CompetitionApplication toEntity(CompetitionApplicationDTO competitionApplicationDTO);
}