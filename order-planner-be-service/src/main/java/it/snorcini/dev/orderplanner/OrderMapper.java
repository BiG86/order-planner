package it.snorcini.dev.orderplanner;

import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.DetailOrderDTO;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;

/**
 * MapStruct mapper interface for the Book entity.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    /**
     * From entity to dto.
     *
     * @param order the book entity
     * @return the DetailBookDTO
     */
    @Mapping(target = "activities.dateInsert", source = "dateInsert")
    @Mapping(target = "activities.dateModify", source = "dateModify")
    @Mapping(target = "activities.lastUserModify", source = "lastUserModify")
    DetailOrderDTO bookToDetailBookDTO(Order order);

    /**
     * From dto to entity.
     *
     * @param orderDTO the book data transfer object
     * @return the Book entity
     */
    Order bookDtoToBook(OrderDTO orderDTO,
                        OffsetDateTime dateInsert,
                        OffsetDateTime dateModify,
                        String lastUserModify);

    /**
     * From update dto to entity.
     *
     * @param bookDTO   the application dto
     * @param id             entity id to be updated
     * @param dateInsert     entity creation date
     * @param dateModify     entity last modified date
     * @param lastUserModify entity last user modify
     * @return the book entity with activities
     */
    Order updateBookDTOToBookEntity(UpdateOrderDTO bookDTO,
                                    Long id,
                                    OffsetDateTime dateInsert,
                                    OffsetDateTime dateModify,
                                    String lastUserModify);


}
