package ir.dotin.service;

import ir.dotin.entity.Leaves;
import ir.dotin.repository.LeavesDao;
import org.hibernate.Session;


public class LeavesService {
    public void addLeave(Leaves leaveEmployee, Session session) {
        LeavesDao leavesDao = new LeavesDao();
        leavesDao.addLeave(leaveEmployee, session);
    }

    public void LeaveConfirmation(Long leaveId, Session session) {
        LeavesDao LeavesDao = new LeavesDao();
        LeavesDao.LeaveConfirmation(leaveId, session);
    }

    public void rejectionLeave(Long leaveId, Session session) {
        LeavesDao LeavesDao = new LeavesDao();
        LeavesDao.rejectionLeave(leaveId, session);
    }

    public void cancelLeave(Long leaveId, Session session) {
        LeavesDao LeavesDao = new LeavesDao();
        LeavesDao.cancelLeave(leaveId, session);
    }
}


