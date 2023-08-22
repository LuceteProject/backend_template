package com.lucete.template.info.service;

import com.lucete.template.info.DTO.AttendanceDTO;
import com.lucete.template.info.DTO.ScheduleDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Attendance;
import com.lucete.template.info.model.Schedule;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.ScheduleRepository;
import com.lucete.template.info.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ModelMapper modelMapper, UserRepository userRepository){
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        User user = userRepository.findById(scheduleDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user_id: " + scheduleDTO.getUserId()));
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        schedule.setUser(user);
        schedule = scheduleRepository.save(schedule);
        return convertToDTO(schedule);
    }

    public ScheduleDTO getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", id));
        return convertToDTO(schedule);
    }

    public ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", id));
        modelMapper.map(scheduleDTO, schedule);
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return convertToDTO(updatedSchedule);
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", id));
        scheduleRepository.delete(schedule);
    }

    public List<ScheduleDTO> getSchedulesByUserId(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);
        return schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedulesByTeamCode(Integer teamCode) {
        List<Schedule> schedules = scheduleRepository.findByTeamCode(teamCode);
        return schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAllSchedules(){
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
        scheduleDTO.setUserId(schedule.getUser().getId());
        scheduleDTO.setTeamCode(schedule.getTeamCode());
        return scheduleDTO;
    }
}
