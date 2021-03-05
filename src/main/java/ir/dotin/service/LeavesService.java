package ir.dotin.service;

import ir.dotin.entity.Leaves;
import ir.dotin.repository.LeavesDao;


public class LeavesService {
    public  void addLeave(Leaves leaveEmployee) {
        LeavesDao leavesDao = new LeavesDao();
        LeavesDao.addLeave(leaveEmployee);
    }

    public  void LeaveConfirmation(Long leaveId) {
        LeavesDao LeavesDao = new LeavesDao();
        LeavesDao.LeaveConfirmation(leaveId);
    }

    public  void rejectionLeave(Long leaveId) {
        LeavesDao LeavesDao = new LeavesDao();
        LeavesDao.rejectionLeave(leaveId);
    }
}


