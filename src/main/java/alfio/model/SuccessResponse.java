package alfio.model;

import lombok.Getter;

@Getter
public class SuccessResponse {

    private String status;

    public SuccessResponse() {
        this.status = "Success";
    }
}
