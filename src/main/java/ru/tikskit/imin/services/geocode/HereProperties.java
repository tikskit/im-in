package ru.tikskit.imin.services.geocode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HereProperties {
    @Value("${geocoding.here.apikey}")
    public String apiKey;
}
