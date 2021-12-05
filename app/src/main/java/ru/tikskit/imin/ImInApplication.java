package ru.tikskit.imin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class ImInApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImInApplication.class, args);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        System.out.println("Supeuser's pass: " + passwordEncoder.encode("QWEasd"));

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
