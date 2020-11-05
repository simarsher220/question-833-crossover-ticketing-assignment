package alfio.controller.api.admin;

import alfio.exception.CustomException;
import alfio.manager.AdminBlacklistManager;
import alfio.model.Blacklist;
import alfio.model.BlacklistEmailRequest;
import alfio.model.SuccessResponse;
import alfio.model.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/api/blacklist")
@RestController
@AllArgsConstructor
public class AdminBlacklistController {

    private final AdminBlacklistManager blacklistManager;

    @PostMapping("/new")
    public ResponseEntity<Result<Blacklist>> createNew(@RequestBody BlacklistEmailRequest request) throws CustomException {
        return new ResponseEntity<>(blacklistManager.insertBlacklistEmail(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Result<SuccessResponse>> delete(@RequestParam String email) throws CustomException {
        return new ResponseEntity<>(blacklistManager.deleteBlacklistedEmail(email), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result<SuccessResponse>> update(@PathVariable String id, @RequestBody BlacklistEmailRequest request) throws CustomException {
        return new ResponseEntity<>(blacklistManager.updateBlacklistedEmail(id, request), HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<Result<List<Blacklist>>> getAll() {
        return new ResponseEntity<>(blacklistManager.getAllBlacklistedEmails(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Result<Blacklist>> getById(@PathVariable String id) throws CustomException {
        return new ResponseEntity<>(blacklistManager.getById(id), HttpStatus.OK);
    }
}
