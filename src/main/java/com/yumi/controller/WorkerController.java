package com.yumi.controller;

import com.yumi.auth.Authorized;
import com.yumi.entity.Worker;
import com.yumi.enums.Role;
import com.yumi.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @Authorized(roles = {Role.GM, Role.HR})
    @GetMapping()
    public Response queryAll(){
        List<Worker> workerList = workerService.list();
        return Response.buildSuccess(workerList);
    }

    @Authorized(roles = {Role.HR})
    @PostMapping()
    public Response addWorker(@RequestBody Worker worker){
        workerService.save(worker);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.HR})
    @PostMapping("/delete")
    public Response delete(@RequestBody Worker worker){
        workerService.removeById(worker.getId());
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.HR})
    @PostMapping("/update")
    public Response update(@RequestBody Worker worker){
        workerService.updateById(worker);
        return Response.buildSuccess();
    }
}
