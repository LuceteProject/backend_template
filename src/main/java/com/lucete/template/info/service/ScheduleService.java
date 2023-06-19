package com.lucete.template.info.service;

import com.lucete.template.info.DTO.AttendanceDTO;
import com.lucete.template.info.DTO.ScheduleDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Attendance;
import com.lucete.template.info.model.Schedule;
import com.lucete.template.info.repository.ScheduleRepository;
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

    public ScheduleService(ScheduleRepository scheduleRepository, ModelMapper modelMapper){
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToDTO(savedSchedule);
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
        List<ScheduleDTO> scheduleDTOS = schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return scheduleDTOS;
    }

    public List<ScheduleDTO> getSchedulesByTeamCode(Integer teamCode) {
        List<Schedule> schedules = scheduleRepository.findByTeamCode(teamCode);
        List<ScheduleDTO> scheduleDTOS = schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return scheduleDTOS;
    }

    public List<ScheduleDTO> getAllSchedules(){
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
        scheduleDTO.setUser_id(schedule.getUser().getId());
        return scheduleDTO;
    }
}
