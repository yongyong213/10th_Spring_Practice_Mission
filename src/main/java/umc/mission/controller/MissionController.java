package umc.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.apiPayload.ApiResponse;
import umc.apiPayload.code.BaseSuccessCode;
import umc.mission.dto.MissionReqDTO;
import umc.mission.dto.MissionResDTO;
import umc.mission.exception.code.MissionSuccessCode;
import umc.mission.service.MissionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {
    private final MissionService missionService;

    @GetMapping("/v1/users/me/missions")
    public ApiResponse<MissionResDTO.MissionListDTO> getMyMissions(
            @RequestParam(name = "isComplete") Boolean isComplete,
            @RequestParam(name = "page") Integer page
    ) {
        return ApiResponse.onSuccess(MissionSuccessCode.USER_MISSION_OK, missionService.getMyMissions(isComplete, page));
    }

    @PatchMapping("/v1/users/user-missions/{userMissionId}")
    public ApiResponse<MissionResDTO.MissionCompleteResultDTO> completeMission(
            @PathVariable(name = "userMissionId") Long userMissionId
    ) {
        return ApiResponse.onSuccess(MissionSuccessCode.COMPLETE_OK, missionService.completeMissions(userMissionId));
    }

    @PostMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody MissionReqDTO.CreateMission dto
            ){
        BaseSuccessCode code = MissionSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
    }

    @GetMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<List<MissionResDTO.GetMission>> getMissions(
            @PathVariable Long storeId
    ){
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(storeId));
    }
}
