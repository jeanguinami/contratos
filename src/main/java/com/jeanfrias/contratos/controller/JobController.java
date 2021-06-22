package com.jeanfrias.contratos.controller;

import com.jeanfrias.contratos.bean.request.JobRequest;
import com.jeanfrias.contratos.bean.response.JobResponse;
import com.jeanfrias.contratos.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    JobService jobService;

    @GetMapping("/filter/{n}")
    public ArrayList<JobResponse> getJobsByName(
            @PathVariable("n") String jobName,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        return jobService.getJobsByName(jobName, offset, limit);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createJob(@RequestBody @Valid JobRequest request){
        jobService.createJob(request);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody @Valid JobRequest request){
        jobService.updateJob(request);
    }

    @DeleteMapping("/remove/{j}")
    public void removeJob (@PathVariable("j") String jobId){
        jobService.deleteJob(jobId);
    }
}
