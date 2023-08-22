package com.lucete.template.info.service;

import com.lucete.template.info.DTO.AttendanceDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Attendance;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.AttendanceRepository;
import com.lucete.template.info.repository.UserRepository;
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
    private final UserRepository userRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, ModelMapper modelMapper, UserRepository userRepository){
        this.attendanceRepository = attendanceRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public AttendanceDTO createAttendance(AttendanceDTO attendanceDTO) {
        User user = userRepository.findById(attendanceDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user_id: " + attendanceDTO.getUserId()));
        Attendance attendance = modelMapper.map(attendanceDTO, Attendance.class);
        attendance.setUser(user);
        attendance = attendanceRepository.save(attendance);
        return convertToDTO(attendance);
    }

    public AttendanceDTO getAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance", "id", id));
        return convertToDTO(attendance);
    }

    public AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance", "id", id));
        modelMapper.map(attendanceDTO, attendance);
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return convertToDTO(updatedAttendance);
    }

    public void deleteAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance", "id", id));
        attendanceRepository.delete(attendance);
    }

    public List<AttendanceDTO> getAllAttendances(){
        List<Attendance> attendances = attendanceRepository.findAll();
        return attendances.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendancesByUserId(Long userId) {
        List<Attendance> attendances = attendanceRepository.findByUserId(userId);
        return attendances.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO attendanceDTO = modelMapper.map(attendance, AttendanceDTO.class);
        attendanceDTO.setUserId(attendance.getUser().getId());
        return attendanceDTO;
    }
}
