//package wushu.mapper;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.MappingTarget;
//import org.mapstruct.factory.Mappers;
//import wushu.dto.ContactCompetitionApplicationDTO;
//import wushu.entity.ContactCompetitionApplication;
//@Mapper(componentModel = "spring")
//public interface ContactCompetitionApplicationMapper {
////    ContactCompetitionApplicationMapper INSTANCE = Mappers.getMapper(ContactCompetitionApplicationMapper.class);
//
////    @Mapping(target = "id", source = "id")
////    @Mapping(target = "competitionName", source = "competitionName")
////    @Mapping(target = "athleteFirstName", source = "athleteFirstName")
////    @Mapping(target = "athleteLastName", source = "athleteLastName")
////    @Mapping(target = "birthDate", source = "birthDate")
////    @Mapping(target = "gender", source = "gender")
////    @Mapping(target = "contactProgram", source = "contactProgram")
////    @Mapping(target = "weightCategory", source = "weightCategory")
//    ContactCompetitionApplicationDTO toDto(ContactCompetitionApplication contactCompetitionApplication);
//
////    @Mapping(target = "id", source = "id")
////    @Mapping(target = "competitionName", source = "competitionName")
////    @Mapping(target = "athleteFirstName", source = "athleteFirstName")
////    @Mapping(target = "athleteLastName", source = "athleteLastName")
////    @Mapping(target = "birthDate", source = "birthDate")
////    @Mapping(target = "gender", source = "gender")
////    @Mapping(target = "contactProgram", source = "contactProgram")
////    @Mapping(target = "weightCategory", source = "weightCategory")
//    ContactCompetitionApplication toEntity(ContactCompetitionApplicationDTO contactCompetitionApplicationDTO);
//
////    void updateEntity(@MappingTarget ContactCompetitionApplication contactCompetitionApplication, ContactCompetitionApplicationDTO contactCompetitionApplicationDTO);
//}

package wushu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import wushu.dto.ContactCompetitionApplicationDTO;
import wushu.entity.ContactCompetitionApplication;

@Mapper(componentModel = "spring")
public interface ContactCompetitionApplicationMapper {
    ContactCompetitionApplicationDTO toDto(ContactCompetitionApplication contactCompetitionApplication);
    ContactCompetitionApplication toEntity(ContactCompetitionApplicationDTO contactCompetitionApplicationDTO);
}
