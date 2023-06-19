package com.lucete.template.info.service;

import com.lucete.template.info.DTO.AttendanceDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Attendance;
import com.lucete.template.info.repository.AttendanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;

    public AttendanceService(AttendanceRepository attendanceRepository, ModelMapper modelMapper){
        this.attendanceRepository = attendanceRepository;
        this.modelMapper = modelMapper;
    }

    public AttendanceDTO createAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = modelMapper.map(attendanceDTO, Attendance.class);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return convertToDto(savedAttendance);
    }

    public AttendanceDTO getAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance", "id", id));
        return convertToDto(attendance);
    }

    public AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance", "id", id));
        modelMapper.map(attendanceDTO, attendance);
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return convertToDto(updatedAttendance);
    }

    public void deleteAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance", "id", id));
        attendanceRepository.delete(attendance);
    }

    public List<AttendanceDTO> getAllAttendances(){
        List<Attendance> attendances = attendanceRepository.findAll();
        return attendances.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendancesByUserId(Long userId) {
        List<Attendance> attendances = attendanceRepository.findByUserId(userId);
        return attendances.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AttendanceDTO convertToDto(Attendance attendance) {
        AttendanceDTO attendanceDTO = modelMapper.map(attendance, AttendanceDTO.class);
        attendanceDTO.setUser_id(attendance.getUser().getId());
        return attendanceDTO;
    }
}
