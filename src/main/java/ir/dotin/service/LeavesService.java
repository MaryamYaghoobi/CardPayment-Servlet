package ir.dotin.service;

import ir.dotin.entity.Leaves;
import ir.dotin.repository.LeavesDao;


public class LeavesService {
    public static void addLeave(Leaves leaveEmployee) {
        LeavesDao leavesDao = new LeavesDao();
        LeavesDao.addLeave(leaveEmployee);
    }

    public static void LeaveConfirmation(Long leaveId) {
        LeavesDao LeavesDao = new LeavesDao();
        LeavesDao.LeaveConfirmation(leaveId);
    }

    public static void rejectionLeave(Long leaveId) {
        LeavesDao LeavesDao = new LeavesDao();
        LeavesDao.rejectionLeave(leaveId);
    }
}


