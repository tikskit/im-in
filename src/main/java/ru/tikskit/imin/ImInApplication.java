package ru.tikskit.imin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.dto.GeoPointDto;
import ru.tikskit.imin.services.geocode.AddressResolverService;
import ru.tikskit.imin.services.geocode.LatLng;
import ru.tikskit.imin.services.geocode.RequestResult;

import java.util.Optional;

@SpringBootApplication
@EnableCaching
public class ImInApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImInApplication.class, args);
/*
        AddressResolverService resolver = context.getBean(AddressResolverService.class);
        AddressDto addressDto = new AddressDto("Россия", "Новосибирская область", "Новосибирск",
                "Лазурная", "10");
        Optional<LatLng> latLng = resolver.resolve(addressDto);
        System.out.println(latLng.orElse(null));
        latLng = resolver.resolve(addressDto);
        System.out.println(latLng.orElse(null));

        latLng = resolver.resolve(new AddressDto("xxx", "sdsd", "dfdf",
                "dfdf", "rrt"));
        System.out.println(latLng.orElse(null));
*/
    }

}
