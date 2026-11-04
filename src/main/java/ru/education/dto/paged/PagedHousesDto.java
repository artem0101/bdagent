package ru.education.dto.paged;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.education.dto.HouseDto;

import java.util.Collection;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class PagedHousesDto {

    private Collection<HouseDto> houses;
    private int page;
    private int size;

}
