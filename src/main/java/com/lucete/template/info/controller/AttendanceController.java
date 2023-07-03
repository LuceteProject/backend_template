package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.AttendanceDTO;
import com.lucete.template.info.model.Attendance;
import com.lucete.template.info.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendances")
@Tag(name = "attendances", description = "출결 API")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @Operation(summary = "새로운 출결 생성", description = "새로운 출결 정보를 생성합니다.")

    public AttendanceDTO createAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        return attendanceService.createAttendance(attendanceDTO);
    }

    @GetMapping("/{attendanceId}")
    @Operation(summary = "특정 출결 정보 조회", description = "출결 ID를 이용하여 출결 정보를 조회합니다.")
    public AttendanceDTO getAttendance(@PathVariable Long attendanceId) {
        return attendanceService.getAttendance(attendanceId);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "특정 사용자의 모든 출결 조회", description = "사용자 ID를 이용하여 해당 사용자의 모든 출결을 조회합니다.")
    public ResponseEntity<List<AttendanceDTO>> getAttendancesByUserId(@Parameter(description = "출결을 조회할 사용자의 ID") @PathVariable Long userId) {
        List<AttendanceDTO> attendances = attendanceService.getAttendancesByUserId(userId);
        return ResponseEntity.ok(attendances);
    }
    @PutMapping("/{attendanceId}")
    @Operation(summary = "출결 정보 수정", description = "출결 ID를 이용하여 기존 출결 정보를 수정합니다.")

    public AttendanceDTO updateAttendance(@PathVariable Long attendanceId, @RequestBody AttendanceDTO attendanceDTO) {
        return attendanceService.updateAttendance(attendanceId, attendanceDTO);
    }

    @DeleteMapping("/{attendanceId}")
    @Operation(summary = "출결 삭제", description = "출결 ID를 이용하여 출결 정보를 삭제합니다.")
    public void deleteAttendance(@PathVariable Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
    }

    @GetMapping
    @Operation(summary = "모든 출결 조회")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }
}
