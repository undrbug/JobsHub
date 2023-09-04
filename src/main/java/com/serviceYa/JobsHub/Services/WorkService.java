package com.serviceYa.JobsHub.Services;

import com.serviceYa.JobsHub.Entities.Work;
import com.serviceYa.JobsHub.Enums.WorkStatus;
import com.serviceYa.JobsHub.Exceptions.MiException;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.serviceYa.JobsHub.Repositories.WorkRepository;

@Service
public class WorkService {
    @Autowired
    WorkRepository workRepository;

    @Autowired
    UserService userService;

    @Transactional
    public void createWork(Work work) throws MiException {

        try {

            work.setWorkStatus(WorkStatus.REQUIRED);
            workRepository.save(work);

        } catch (Exception e) {
            throw new MiException("ERROR al generar solicitud de trabajo");
        }
    }

    @Transactional
    public void delete(String id) {
        workRepository.delete(getById(id));
    }

    public Work getById(String id) {
        return workRepository.findById(id).get();
    }

    @Transactional
    public void changeWorkStatus(String id, String wStat) throws MiException{
        
        try {
            
            Optional<Work> work = workRepository.findById(id);
            if (work.isPresent() && wStat.equals("REVERT") || wStat.equals("ACCEPTED") || wStat.equals("DONE")) {
                Work newWorkStatus = work.get();
                newWorkStatus.setWorkStatus(WorkStatus.valueOf(wStat)); 
            }

        } catch (Exception e) {
            throw new MiException("ERROR AL MODIFICAR WORK STATUS");
        }

    }

}