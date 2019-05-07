/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.prs.game.GameMoveEnum;

import java.io.IOException;

public class GameMoveEnumSerializer extends JsonSerializer<GameMoveEnum> {

    @Override
    public void serialize(GameMoveEnum value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(GameMoveEnum.VALUE_NAME, value.getValue());
        gen.writeObjectField(GameMoveEnum.VIEW_NAME, value.getView());
        gen.writeEndObject();
    }

}
