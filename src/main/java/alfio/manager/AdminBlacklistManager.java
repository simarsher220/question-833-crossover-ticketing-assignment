package alfio.manager;

import alfio.exception.CustomException;
import alfio.model.Blacklist;
import alfio.model.BlacklistEmailRequest;
import alfio.model.SuccessResponse;
import alfio.model.result.ErrorCode;
import alfio.model.result.Result;
import alfio.repository.AdminBlacklistServiceRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class AdminBlacklistManager {

    private final AdminBlacklistServiceRepository blacklistRepo;

    public Result<Blacklist> insertBlacklistEmail(BlacklistEmailRequest request) throws CustomException {
        Blacklist blacklist = new Blacklist(UUID.randomUUID().toString(), request.getEmail());
        Blacklist.validateForUpsertion(blacklist);
        List<Blacklist> blacklists = null;
        blacklists = blacklistRepo.findByEmail(request.getEmail());
        if (!CollectionUtils.isEmpty(blacklists)) {
            ErrorCode errorCode = ErrorCode.custom("exists", "Blacklisted email already exists!");
            throw new CustomException(errorCode, HttpStatus.BAD_REQUEST);
        }
        blacklistRepo.insert(blacklist.getId(), blacklist.getEmail());
        return Result.success(blacklist);
    }

    public Result<SuccessResponse> deleteBlacklistedEmail(String email) throws CustomException {
        Validate.isTrue(!StringUtils.isEmpty(email));
        List<Blacklist> blacklists = null;
        blacklists = blacklistRepo.findByEmail(email);
        if (CollectionUtils.isEmpty(blacklists)) {
            ErrorCode errorCode = ErrorCode.custom("not_found", "Blacklisted email not found!");
            throw new CustomException(errorCode, HttpStatus.NOT_FOUND);
        }
        int response = blacklistRepo.deleteByEmail(email);
        return Result.success(new SuccessResponse());
    }

    public Result<List<Blacklist>> getAllBlacklistedEmails() {
        List<Blacklist> blacklists = getBlacklists();
        return Result.success(blacklists);
    }

    public List<Blacklist> getBlacklists() {
        List<Blacklist> blacklists = new ArrayList<>();
        try {
            blacklists = blacklistRepo.findAll();
        }
        catch (Exception e) {
            log.warn("No existing blacklisted guest found!");
        }
        return blacklists;
    }

    public Blacklist getBlacklistedGuestById(String id) throws CustomException {
        List<Blacklist> blacklists = new ArrayList<>();
        blacklists = blacklistRepo.findById(id);
        if (CollectionUtils.isEmpty(blacklists)) {
            ErrorCode errorCode = ErrorCode.custom("not_found", "Blacklisted email not found!");
            throw new CustomException(errorCode, HttpStatus.NOT_FOUND);
        }
        return blacklists.get(0);
    }

    public Result<Blacklist> getById(String id) throws CustomException {
        return Result.success(getBlacklistedGuestById(id));
    }

    public Result<SuccessResponse> updateBlacklistedEmail(String id, BlacklistEmailRequest request) throws CustomException {
        Blacklist blacklist = getBlacklistedGuestById(id);
        blacklist = new Blacklist(id, request.getEmail());
        Blacklist.validateForUpsertion(blacklist);
        int response = blacklistRepo.updateById(blacklist.getId(), blacklist.getEmail());
        return Result.success(new SuccessResponse());
    }
}
