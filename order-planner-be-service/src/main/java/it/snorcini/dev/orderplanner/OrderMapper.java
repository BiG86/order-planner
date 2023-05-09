package it.snorcini.dev.orderplanner;

import it.snorcini.dev.orderplanner.dto.OrderDTO;
import it.snorcini.dev.orderplanner.dto.UpdateOrderDTO;
import it.snorcini.dev.orderplanner.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * MapStruct mapper interface for the Book entity.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Named("mapPackages")
    List<it.snorcini.dev.orderplanner.entity.Package> mapPackages(
            List<it.snorcini.dev.orderplanner.entity.Package> packages);

    /**
     * From entity to dto.
     *
     * @param order the order entity
     * @return the DetailOrderDTO
     */
    @Mapping(target = "packages", source = "packages", qualifiedByName = "mapPackages")
    UpdateOrderDTO orderToDetailOrderDTO(Order order);

    /**
     * From dto to entity.
     *
     * @param orderDTO the order data transfer object
     * @return the order entity
     */
    Order orderDtoToOrder(OrderDTO orderDTO);

    /**
     * From update dto to entity.
     *
     * @param bookDTO    the application dto
     * @param uid         entity id to be updated
     * @return the book entity with activities
     */
    Order updateOrderDTOToOrderEntity(UpdateOrderDTO bookDTO,
                                      String uid);


}
