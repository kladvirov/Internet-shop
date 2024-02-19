package by.kladvirov.mapper;

import by.kladvirov.dto.OrderCreationDto;
import by.kladvirov.dto.OrderDto;
import by.kladvirov.model.Good;
import by.kladvirov.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "goods", source = "orderDto.goodIds", qualifiedByName = "mapGoodIdsToGoods")
    Order toEntity(OrderCreationDto orderDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "goodIds", source = "order.goods", qualifiedByName = "mapGoodsToGoodIds")
    OrderDto toDto(Order order);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "goodIds", source = "order.goods", qualifiedByName = "mapGoodsToGoodIds")
    List<OrderDto> toDto(List<Order> orders);

    @Named("mapGoodIdsToGoods")
    default Set<Good> mapGoodIdsToGoods(List<Long> goodIds) {
        return goodIds.stream()
                .map(goodId -> {
                    Good good = new Good();
                    good.setId(goodId);
                    return good;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapGoodsToGoodIds")
    default List<Long> mapGoodsToGoodIds(Set<Good> goods) {
        return goods.stream()
                .map(Good::getId)
                .collect(Collectors.toList());
    }

}
