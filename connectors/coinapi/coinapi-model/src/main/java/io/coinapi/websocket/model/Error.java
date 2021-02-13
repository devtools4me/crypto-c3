package io.coinapi.websocket.model;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.ToString;

@ToString(callSuper = true)
@CompiledJson
public class Error extends MessageBase {
    private String message;

    public String getMessage() { return message; }

    @JsonAttribute(name = "message")
    public void setMessage(String message) { this.message = message; }
}
