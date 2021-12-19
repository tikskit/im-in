package ru.tikskit.imin.kladrimport.model.src;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class State {
    private final byte id;
    private final String name;
}
