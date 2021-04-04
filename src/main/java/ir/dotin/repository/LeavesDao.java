package ir.dotin.repository;

import ir.dotin.entity.CategoryElement;
import ir.dotin.entity.Leaves;
import org.hibernate.Session;

import javax.persistence.Query;

public class LeavesDao {
    public void addLeave(Leaves leaveEmployee, Session session) {
        session.save(leaveEmployee);

    }


    public void LeaveConfirmation(long leaveId, Session session) {
        Query categoryElementQuery = session.createQuery
                ("select ce from CategoryElement ce where ce.code =:code");
        categoryElementQuery.setParameter("code", "accept");
        CategoryElement accept = (CategoryElement) categoryElementQuery.getSingleResult();
        Query query = session.createQuery
                ("update Leaves l set l.leaveStatus =:accept " +
                        "  where l.id =:id");
        query.setParameter("accept", accept);
        query.setParameter("id", leaveId);
        query.executeUpdate();

    }


    public void rejectionLeave(long leaveId, Session session) {
        Query categoryElementQuery = session.createQuery
                (" select ce from CategoryElement ce where ce.code =:code");
        categoryElementQuery.setParameter("code", "reject");
        CategoryElement reject = (CategoryElement) categoryElementQuery.getSingleResult();
        Query query = session.createQuery
                ("update Leaves l set l.leaveStatus =:reject " +
                        " where l.id =:id");
        query.setParameter("reject", reject);
        query.setParameter("id", leaveId);
        query.executeUpdate();
    }

    public void cancelLeave(long leaveId, Session session) {
        Query categoryElementQuery = session.createQuery
                (" select ce from CategoryElement ce where ce.code =:code");
        categoryElementQuery.setParameter("code", "cancel");
        CategoryElement cancel = (CategoryElement) categoryElementQuery.getSingleResult();
        Query query = session.createQuery
                ("update Leaves l set l.leaveStatus =:cancel " +
                        " where l.id =:id");
        query.setParameter("cancel", cancel);
        query.setParameter("id", leaveId);
        query.executeUpdate();
    }
}
