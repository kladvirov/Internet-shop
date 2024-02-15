package org.example.mapper;

import org.example.dto.OrderCreationDto;
import org.example.dto.OrderDto;
import org.example.model.Good;
import org.example.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper
public interface OrderMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "goods", source = "orderDto.goodIds", qualifiedByName = "mapGoodIdsToGoods")
    Order toEntity(OrderCreationDto orderDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "goodIds", source = "order.goods", qualifiedByName = "mapGoodsToGoodIds")
    OrderDto toDto(Order order);

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
