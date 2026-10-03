package ru.education.utils;

import org.springframework.stereotype.Component;
import ru.education.dto.ApartmentDto;
import ru.education.dto.ClientDto;
import ru.education.dto.EmployeeDto;
import ru.education.dto.GroundDto;
import ru.education.dto.HouseDto;
import ru.education.dto.PlacementDto;
import ru.education.dto.SubjectDto;
import ru.education.dto.TransactionDto;
import ru.education.entity.ApartmentEntity;
import ru.education.entity.ClientEntity;
import ru.education.entity.EmployeeEntity;
import ru.education.entity.GroundEntity;
import ru.education.entity.HouseEntity;
import ru.education.entity.PlacementEntity;
import ru.education.entity.SubjectEntity;
import ru.education.entity.TransactionEntity;
import ru.education.enums.PostType;
import ru.education.enums.SubjectType;

@Component
public final class Mapper {

    private Mapper() {
    }

    public EmployeeDto toEmployeeDto(EmployeeEntity entity) {
        return EmployeeDto.builder()
                .id(entity.getId())
                .surname(entity.getSurname())
                .name(entity.getName())
                .patronymic(entity.getPatronymic())
                .post(entity.getPost().name())
                .build();
    }

    public EmployeeEntity toEmployeeEntity(EmployeeDto dto) {
        return EmployeeEntity.builder()
                .surname(dto.getSurname())
                .name(dto.getName())
                .patronymic(dto.getPatronymic())
                .post(PostType.valueOf(dto.getPost()))
                .build();
    }

    public ApartmentDto toApartmentDto(ApartmentEntity entity) {
        var subjectEntity = entity.getSubject();

        return ApartmentDto.builder()
                .id(entity.getId()).floor(entity.getFloor())
                .rooms(entity.getRooms())
                .subjectId(subjectEntity.getId())
                .country(subjectEntity.getCountry())
                .city(subjectEntity.getCity())
                .postalCode(subjectEntity.getPostalCode())
                .street(subjectEntity.getStreet())
                .number(subjectEntity.getNumber())
                .price(subjectEntity.getPrice())
                .isActive(subjectEntity.isActive())
                .build();
    }

    public ApartmentEntity toApartmentEntity(ApartmentDto dto) {
        var subjectEntity = SubjectEntity.builder()
                .id(dto.getSubjectId())
                .country(dto.getCountry())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .street(dto.getStreet())
                .number(dto.getNumber())
                .price(dto.getPrice())
                .isActive(dto.isActive())
                .type(SubjectType.valueOf(dto.getType()))
                .build();

        return ApartmentEntity.builder()
                .id(dto.getId())
                .floor(dto.getFloor())
                .rooms(dto.getRooms())
                .subject(subjectEntity)
                .build();
    }

    public GroundDto toGroundDto(GroundEntity entity) {
        var subjectEntity = entity.getSubject();

        return GroundDto.builder()
                .id(entity.getId())
                .area(entity.getArea())
                .subjectId(subjectEntity.getId())
                .country(subjectEntity.getCountry())
                .city(subjectEntity.getCity())
                .postalCode(subjectEntity.getPostalCode())
                .street(subjectEntity.getStreet())
                .number(subjectEntity.getNumber())
                .price(subjectEntity.getPrice())
                .isActive(subjectEntity.isActive())
                .build();
    }

    public GroundEntity toGroundEntity(GroundDto dto) {
        var subjectEntity = SubjectEntity
                .builder()
                .id(dto.getSubjectId())
                .country(dto.getCountry())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .street(dto.getStreet())
                .number(dto.getNumber())
                .price(dto.getPrice())
                .isActive(dto.isActive())
                .type(SubjectType.valueOf(dto.getType()))
                .build();

        return GroundEntity.builder()
                .id(dto.getId())
                .area(dto.getArea())
                .subject(subjectEntity)
                .build();
    }

    public HouseDto toHouseDto(HouseEntity entity) {
        var subjectEntity = entity.getSubject();

        return HouseDto.builder()
                .id(entity.getId())
                .floors(entity.getFloors())
                .rooms(entity.getRooms())
                .areaGround(entity.getAreaGround())
                .areaHouse(entity.getAreaHouse())
                .subjectId(subjectEntity.getId())
                .country(subjectEntity.getCountry())
                .city(subjectEntity.getCity())
                .postalCode(subjectEntity.getPostalCode())
                .street(subjectEntity.getStreet())
                .number(subjectEntity.getNumber())
                .price(subjectEntity.getPrice())
                .isActive(subjectEntity.isActive())
                .build();
    }

    public HouseEntity toHouseEntity(HouseDto dto) {
        var subjectEntity = SubjectEntity.builder()
                .id(dto.getSubjectId())
                .country(dto.getCountry())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .street(dto.getStreet())
                .number(dto.getNumber())
                .price(dto.getPrice())
                .isActive(dto.isActive())
                .type(SubjectType.valueOf(dto.getType()))
                .build();

        return HouseEntity.builder()
                .id(dto.getId())
                .floors(dto.getFloors())
                .areaGround(dto.getAreaGround())
                .areaHouse(dto.getAreaHouse())
                .rooms(dto.getRooms())
                .subject(subjectEntity)
                .build();
    }

    public PlacementDto toPlacementDto(PlacementEntity entity) {
        var subjectEntity = entity.getSubject();

        return PlacementDto.builder()
                .id(entity.getId())
                .floors(entity.getFloors())
                .rooms(entity.getRooms())
                .area(entity.getArea())
                .subjectId(subjectEntity.getId())
                .country(subjectEntity.getCountry())
                .city(subjectEntity.getCity())
                .postalCode(subjectEntity.getPostalCode())
                .street(subjectEntity.getStreet())
                .number(subjectEntity.getNumber())
                .price(subjectEntity.getPrice())
                .isActive(subjectEntity.isActive())
                .build();
    }

    public PlacementEntity toPlacementEntity(PlacementDto dto) {
        var subjectEntity = SubjectEntity.builder()
                .id(dto.getSubjectId())
                .country(dto.getCountry())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .street(dto.getStreet())
                .number(dto.getNumber())
                .price(dto.getPrice())
                .isActive(dto.isActive())
                .type(SubjectType.valueOf(dto.getType()))
                .build();

        return PlacementEntity.builder()
                .id(dto.getId())
                .floors(dto.getFloors())
                .area(dto.getArea())
                .rooms(dto.getRooms())
                .subject(subjectEntity)
                .build();
    }

    public SubjectDto toSubjectDto(SubjectEntity entity) {
        return SubjectDto.builder()
                .subjectId(entity.getId())
                .country(entity.getCountry())
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .street(entity.getStreet())
                .number(entity.getNumber())
                .price(entity.getPrice())
                .isActive(entity.isActive())
                .build();
    }

    public SubjectEntity toSubjectEntity(SubjectDto dto) {
        return SubjectEntity.builder()
                .id(dto.getSubjectId())
                .country(dto.getCountry())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .street(dto.getStreet())
                .number(dto.getNumber())
                .price(dto.getPrice())
                .isActive(dto.isActive())
                .type(SubjectType.valueOf(dto.getType()))
                .build();
    }

    public ClientDto toClientDto(ClientEntity entity) {
        return ClientDto.builder()
                .id(entity.getId())
                .surname(entity.getSurname())
                .name(entity.getName())
                .patronymic(entity.getPatronymic())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .birthday(entity.getBirthday())
                .build();
    }

    public ClientEntity toClientEntity(ClientDto dto) {
        return ClientEntity.builder()
                .id(dto.getId())
                .surname(dto.getSurname())
                .name(dto.getName())
                .patronymic(dto.getPatronymic())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .birthday(dto.getBirthday())
                .build();
    }

    public TransactionDto toTransactionDto(TransactionEntity entity) {
        return TransactionDto.builder()
                .id(entity.getId())
                .subject(this.toSubjectDto(entity.getSubject()))
                .buyer(this.toClientDto(entity.getBuyer()))
                .seller(this.toClientDto(entity.getSeller()))
                .employee(this.toEmployeeDto(entity.getEmployee()))
                .transactionDate(entity.getTransactionDate())
                .amount(entity.getAmount())
                .status(entity.getStatus().name())
                .build();
    }

}
