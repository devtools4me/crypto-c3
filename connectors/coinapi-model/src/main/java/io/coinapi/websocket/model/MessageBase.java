package io.coinapi.websocket.model;

import com.dslplatform.json.CompiledJson;
import lombok.ToString;

@ToString
@CompiledJson
public class MessageBase {

    protected String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
