package org.teamplus.mvc.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AutoUpdate {

    @PersistenceContext
    private EntityManager entity;

    @Scheduled(fixedRate = 6000)       //1초마다 실행
    @Transactional
    public void updateDatabase() {
        /* 현재 시간 기준 개인 할일 insert 후 한시간 이후에 status 가 진행전으로 update  */
        LocalDateTime one_hour = LocalDateTime.now().minusHours(1);    // 현재 시간에서 1시간 전

        List<Object[]> list = entity.createNativeQuery(
                        "SELECT * FROM PrivateTodo WHERE status = 3 AND todoDate <= :one_hour")
                .setParameter("one_hour", one_hour)
                .getResultList();

        for (Object[] result : list) {
            String todoNo = (String) result[0];

            String updateSql = "UPDATE PrivateTodo SET status = 0 WHERE todoNo = :todoNo";
            entity.createNativeQuery(updateSql)
                    .setParameter("todoNo", todoNo)
                    .executeUpdate();
        }
    }

    @Scheduled(fixedRate = 6000)    //1초마다 실행
    @Transactional
    public void endDateUpdate(){
        /* status 가 4이고 endDate 가 null 인 todoNo 만 endDate 를 sysdate로 update  */
        List<Object[]> list = entity.createNativeQuery("SELECT * FROM PrivateTodo WHERE status = 4 AND endDate IS NULL")
                .getResultList();

        for(Object[] result : list){
            String todoNo = (String) result[0];

            String updateSql = "UPDATE PrivateTodo SET endDate = sysdate WHERE todoNo = :todoNo";
            entity.createNativeQuery(updateSql)
                    .setParameter("todoNo", todoNo)
                    .executeUpdate();
        }
    }

    @Scheduled(fixedRate = 6000)       //1초마다 실행
    @Transactional
    public void LastupdateDatabase() {
        /* 현재 시간 기준 개인 할일 완료인경우 하루뒤에 status 가 1로 update  */
        LocalDateTime one_day = LocalDateTime.now().minusDays(1);    // 현재 시간에서 1일 전

        List<Object[]> list = entity.createNativeQuery(
                        "SELECT * FROM PrivateTodo WHERE status = 4 AND endDate <= :one_day")
                .setParameter("one_day", one_day)
                .getResultList();

        for (Object[] result : list) {
            String todoNo = (String) result[0];

            String updateSql = "UPDATE PrivateTodo SET status = 1 WHERE todoNo = :todoNo";
            entity.createNativeQuery(updateSql)
                    .setParameter("todoNo", todoNo)
                    .executeUpdate();
        }
    }
}