package ru.luzhnykh.socialnet.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.luzhnykh.socialnet.dto.DialogRequestDto;

@FeignClient(name = "dialogs", url = "${dialogs.address}")
public interface DialogsHttpClient {
    String LOG_ID_HEADER = "log_id";

    @PostMapping("/dialog/{user_id}/send")
    ResponseEntity<String> send(@PathVariable String user_id, @RequestBody DialogRequestDto text,
                                @RequestHeader(LOG_ID_HEADER) String logId,
                                @RequestHeader String token);
}
