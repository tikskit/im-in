package ru.tikskit.imin.services.geocode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RequestResult {
    private final ResultStatus status;
    private final LatLng latLng;
    private final Exception exception;

    public static RequestResult success(LatLng latLng) {
        return new RequestResult(ResultStatus.RECEIVED, latLng, null);
    }

    public static RequestResult empty() {
        return new RequestResult(ResultStatus.EMPTY, null, null);
    }

    public static RequestResult limitExceeded() {
        return new RequestResult(ResultStatus.LIMIT_EXCEEDED, null, null);
    }

    public static RequestResult exception(Exception exception) {
        return new RequestResult(ResultStatus.EXCEPTION, null, exception);
    }
}
