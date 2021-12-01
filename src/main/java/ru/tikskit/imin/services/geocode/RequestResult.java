package ru.tikskit.imin.services.geocode.here;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.tikskit.imin.services.geocode.here.dto.Position;

@AllArgsConstructor
@Getter
public class RequestResult {
    private final ResultStatus status;
    private final Position position;
    private final Exception exception;

    public static RequestResult success(Position position) {
        return new RequestResult(ResultStatus.RECEIVED, position, null);
    }

    public static RequestResult limitExceeded() {
        return new RequestResult(ResultStatus.LIMIT_EXCEEDED, null, null);
    }

    public static RequestResult exception(Exception exception) {
        return new RequestResult(ResultStatus.EXCEPTION, null, exception);
    }
}
