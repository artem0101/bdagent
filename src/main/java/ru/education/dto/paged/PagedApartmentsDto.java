package ru.education.dto.paged;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.education.dto.ApartmentDto;

import java.util.Collection;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class PagedApartmentsDto {

    private Collection<ApartmentDto> apartments;
    private int page;
    private int size;

}
