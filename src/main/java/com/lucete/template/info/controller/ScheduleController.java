package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.ScheduleDTO;
import com.lucete.template.info.model.Schedule;
import com.lucete.template.info.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@Tag(name = "schedules", description = "일정 API")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    @Operation(summary = "새로운 일정 생성", description = "새로운 일정 정보를 생성합니다.")

    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.createSchedule(scheduleDTO);
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "특정 일정 정보 조회", description = "일정 ID를 이용하여 출결 정보를 조회합니다.")
    public ScheduleDTO getSchedule(@PathVariable Long scheduleId) {
        return scheduleService.getSchedule(scheduleId);
    }

    @GetMapping("/userID/{userId}")
    @Operation(summary = "특정 사용자의 모든 일정 조회", description = "사용자 ID를 이용하여 해당 사용자의 모든 일정을 조회합니다.")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByUserId(@Parameter(description = "일정을 조회할 사용자의 ID") @PathVariable Long userId) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByUserId(userId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/teamCode/{teamCode}")
    @Operation(summary = "특정 팀의 모든 팀 일정 조회", description = "팀 코드를 이용하여 해당 팀의 모든 팀 일정을 조회합니다.")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByTeamCode(@Parameter(description = "일정을 조회할 팀 코드") @PathVariable Integer teamCode) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByTeamCode(teamCode);
        return ResponseEntity.ok(schedules);
    }
    @PutMapping("/{scheduleId}")
    @Operation(summary = "일정 정보 수정", description = "일정 ID를 이용하여 기존 일정 정보를 수정합니다.")

    public ScheduleDTO updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.updateSchedule(scheduleId, scheduleDTO);
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "일정 삭제", description = "일정 ID를 이용하여 일정 정보를 삭제합니다.")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }

    @GetMapping
    @Operation(summary = "모든 일정 조회")
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }
}
