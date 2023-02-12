package eu.jitpay.testtask.mapper;

import eu.jitpay.testtask.domain.Location;
import eu.jitpay.testtask.dto.LocationDto;
import eu.jitpay.testtask.dto.LocationInfo;
import eu.jitpay.testtask.dto.UserLocationDataRequest;
import eu.jitpay.testtask.dto.UserLocationHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "latitude", source = "request.location.latitude"),
            @Mapping(target = "longitude", source = "request.location.longitude")
    })
    Location convertFromUserLocationDataRequest(UserLocationDataRequest request);

    default Optional<UserLocationHistoryDto> convertToUserLocationHistoryDto(List<Location> locations){
        if(Objects.isNull(locations) || locations.isEmpty()) {
            return Optional.empty();
        }

        UserLocationHistoryDto userLocationHistoryDto = new UserLocationHistoryDto();
        userLocationHistoryDto.setUserId(locations.get(0).getUserId());
        userLocationHistoryDto.setLocations(convertToLocationInfo(locations));
        return Optional.of(userLocationHistoryDto);
    }

    List<LocationInfo> convertToLocationInfo(List<Location> locations);

    default LocationInfo convertToLocationInfo(Location location) {
        if(location == null) {
            return null;
        }
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setCreatedOn(location.getCreatedOn());

        LocationDto locationDto = new LocationDto();
        locationDto.setLongitude(location.getLongitude());
        locationDto.setLatitude(location.getLatitude());
        locationInfo.setLocation(locationDto);
        return locationInfo;
    }
}
