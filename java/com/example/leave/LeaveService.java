package com.example.leave;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class LeaveService {

    private final ArrayList<LeaveApplication> leaves =
            new ArrayList<>();

    private long nextId = 1;

    // GET: Get all leave applications
    public ArrayList<LeaveApplication> getAllLeaves() {
        return leaves;
    }

    // GET: Get leave application by ID
    public Optional<LeaveApplication> getLeaveById(Long id) {
        return leaves.stream()
                .filter(l -> l.getEmployeeId().equals(id))
                .findFirst();
    }

    // POST: Apply for leave
    public LeaveApplication addLeave(LeaveApplication leave) {
        leave.setEmployeeId(nextId++);
        leaves.add(leave);
        return leave;
    }

    // PUT: Update complete leave application
    public Optional<LeaveApplication> updateLeave(
            Long id, LeaveApplication updatedLeave) {

        Optional<LeaveApplication> existing =
                getLeaveById(id);

        if (existing.isPresent()) {
            updatedLeave.setEmployeeId(id);

            int index = leaves.indexOf(existing.get());
            leaves.set(index, updatedLeave);

            return Optional.of(updatedLeave);
        }

        return Optional.empty();
    }

    // DELETE: Delete leave application
    public boolean deleteLeave(Long id) {
        return leaves.removeIf(
                l -> l.getEmployeeId().equals(id));
    }

    // PATCH: Update selected fields
    public Optional<LeaveApplication> patchLeave(
            Long id, LeaveApplication updates) {

        Optional<LeaveApplication> existing =
                getLeaveById(id);

        if (existing.isEmpty()) {
            return Optional.empty();
        }

        LeaveApplication leave = existing.get();

        if (updates.getEmployeeName() != null) {
            leave.setEmployeeName(updates.getEmployeeName());
        }

        if (updates.getLeaveType() != null) {
            leave.setLeaveType(updates.getLeaveType());
        }

        if (updates.getNumberOfDays() != 0) {
            leave.setNumberOfDays(updates.getNumberOfDays());
        }

        if (updates.getReason() != null) {
            leave.setReason(updates.getReason());
        }

        return Optional.of(leave);
    }
}
