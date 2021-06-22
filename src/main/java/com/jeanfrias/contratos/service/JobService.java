package com.jeanfrias.contratos.service;

import com.jeanfrias.contratos.bean.ejb.Job;
import com.jeanfrias.contratos.bean.request.JobRequest;
import com.jeanfrias.contratos.bean.response.JobResponse;
import com.jeanfrias.contratos.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    public ArrayList<JobResponse> getJobsByName(String jobName, Integer offset, Integer limit) {
        Pageable pagination = PageRequest.of(offset, limit, Sort.unsorted());
        ArrayList<JobResponse> jobResponseList = new ArrayList<>();

        Page<Job> jobs = jobRepository.findByName(jobName, pagination);
        jobs.forEach((j) -> jobResponseList.add(buildJobResponse(j)));

        return jobResponseList;
    }

    public void createJob(JobRequest jobReq) {
        Job job = buildJob(jobReq);
        jobRepository.save(job);
    }

    public void updateJob(JobRequest jobReq) {
        Job job = jobRepository.findById(Long.valueOf(jobReq.getJobId()))
                .orElseThrow(() -> new RuntimeException("Invalid Job"));

        job.setName(jobReq.getJobName());

        jobRepository.save(job);
    }

    public void deleteJob(String jobId) {
        Job job = jobRepository.findById(Long.valueOf(jobId))
                .orElseThrow(() -> new RuntimeException("Job isn't on database"));
        jobRepository.delete(job);
    }

    private Job buildJob(JobRequest jobReq){
        Integer findCount = jobRepository.countName(jobReq.getJobName());
        if (findCount == 0) {

            if(!isBlank(jobReq.getJobName())){
                return Job.builder()
                        .name(jobReq.getJobName())
                        .build();
            } else {
                throw new RuntimeException("Invalid Job");
            }
        } else {
            throw new RuntimeException("Job already on database");
        }
    }

    private JobResponse buildJobResponse(Job job) {

        return JobResponse.builder()
                .jobId(String.valueOf(job.getId()))
                .jobName(job.getName())
                .build();
    }
}
