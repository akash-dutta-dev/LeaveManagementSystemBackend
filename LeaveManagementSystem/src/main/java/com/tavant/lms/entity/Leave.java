package com.tavant.lms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_request")

@NamedQuery(name = "Leave.findLeaveByDate",
        query = "SELECT l FROM Leave l WHERE ((l.fromDate BETWEEN ?1 AND ?2) OR (l.toDate BETWEEN ?1 AND ?2)) " +
                "AND l.status='APPROVED' ")
public class Leave {

    @Id
    @Column(name = "leave_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "leave_type", columnDefinition = "TEXT", nullable = false)
    private String leaveType;

    @Column(name = "leave_reason", columnDefinition = "TEXT", nullable = false)
    private String leaveReason;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Column(name = "denied_reason", columnDefinition = "TEXT")
    private String deniedReason;

    @Column(name = "status", columnDefinition = "TEXT", nullable = false)
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "to_manager")
    private Employee toManager;

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Employee getToManager() {
        return toManager;
    }

    public void setToManager(Employee toManager) {
        this.toManager = toManager;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Leave leave = (Leave) o;
//        return
//                Objects.equals(leaveId, leave.leaveId) &&
//                Objects.equals(employee, leave.employee) &&
//                Objects.equals(leaveType, leave.leaveType) &&
//                Objects.equals(leaveReason, leave.leaveReason) &&
//                Objects.equals(fromDate, leave.fromDate) &&
//                Objects.equals(toDate, leave.toDate) &&
//                Objects.equals(deniedReason, leave.deniedReason) &&
//                status == leave.status &&
//                Objects.equals(createdAt, leave.createdAt) &&
//                Objects.equals(toManager, leave.toManager);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(leaveId, employee, leaveType, leaveReason, fromDate, toDate, deniedReason, status, createdAt, toManager);
//    }
}
